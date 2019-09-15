package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {
	//score declared for keeping track of points of eating apples
	public double score = 0;
	public float score1 = (int) score;
	//speed4Score is created to increase the snakes speed whenever she eats
	public int speed4Score = 1;
    public int lenght;
    public int time = 0;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;

    public int moveCounter;

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;

    }
//Move counter changes speed
    public void tick(){
    	int x = xCoord;
        int y = yCoord;
        moveCounter++;
        //counts before apple goes bad
        time++;
        System.out.println(time);
        //speedometer and speed4Score are variables created to increase the snakes speed
        double speedometer = 6;

        if(moveCounter>=speedometer-(speed4Score*0.09)) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        // !direction at the end of the statements to prevent backtracking
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction != "Down"){
            direction="Up";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && direction != "Up"){
            direction="Down";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction != "Right"){
            direction="Left";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && direction != "Left"){
            direction="Right";
        }
        //Pressing N adds 1 to the tail
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
        	lenght++;
        //	handler.getWorld().body.addFirst(new Tail(x, y,handler));
        	handler.getWorld().body.addLast(new Tail(xCoord, yCoord, handler));
        }
        //By pressing M you can get rid of one lenght in tail
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_M)){
        	lenght--;
        	handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]= false; 
        //	handler.getWorld().body.addFirst(new Tail(x, y,handler));
        	handler.getWorld().body.removeLast();
        }
        //Here I make the snake speed down if - is pressed
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS) && speed4Score != 1){
        	speed4Score--;
        }
        //Here I make the snake speed up if + or = is pressed. I putted = too since some laptops have the = sign as the same as the + and cant get the + unless you press shift + =.
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_PLUS) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){
        	speed4Score++;
        }
        //PAUSE state
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
        	State.setState(handler.getGame().pauseState);
        }
        
    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
            	

            	//changed the kill() to respective coordenates to make the snake teleport
                if(xCoord==0){
                	xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                    //kill();
                }else{
                    xCoord--;
                }
                break;
            case "Right":          
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                	xCoord = 0;
                    //kill();
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                	yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                    //kill();
                }else{
                    yCoord--;
                }
                break;
            case "Down":
            	
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                	yCoord = 0;
                    //kill();
                }else{
                    yCoord++;
                }
                break;
        }
        
        handler.getWorld().playerLocation[xCoord][yCoord]=true;
        
        for(int i = 0; i < handler.getWorld().body.size(); i++) {
      	  if(((this.xCoord == handler.getWorld().body.get(i).x) && (this.yCoord == handler.getWorld().body.get(i).y))) {
      		   kill();
      		   State.setState(handler.getGame().gameOverState);
      	   }
      }

        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
        }

       if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }

    }

    public void render(Graphics g,Boolean[][] playeLocation){
    	Random r = new Random();
    	//Score on screen, size and color
    	Font myFont = new Font ("Score: ", 1, 30);
    	g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + this.score1, 0, 40);
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(Color.green);
                
                //GIVES THE SHADE OF GREEN TO THE SNAKE
                if(playeLocation[i][j]){
                	g.setColor(Color.green);
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }
                //GIVES THE SHADE OF RED TO THE SNAKE
                if(handler.getWorld().appleLocation[i][j] && time < 400) {
                	g.setColor(Color.red);
                	   g.fillRect((i*handler.getWorld().GridPixelsize),
                               (j*handler.getWorld().GridPixelsize),
                               handler.getWorld().GridPixelsize,
                               handler.getWorld().GridPixelsize);
                }
                //When time variable reaches 400 the apple starts going bad
                else if(handler.getWorld().appleLocation[i][j] && time >= 400 && handler.getWorld().appleLocation[i][j] && time < 800) {
                	g.setColor(Color.orange);
    				g.fillRect((i*handler.getWorld().GridPixelsize),
                    (j*handler.getWorld().GridPixelsize),
                    handler.getWorld().GridPixelsize,
                    handler.getWorld().GridPixelsize);
    	}
                //when Time variable reaches 800 the apple goes bad
            else if(handler.getWorld().appleLocation[i][j] && time >= 800){
            			
            				g.setColor(Color.GRAY);
            				g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
            	}
            }
        }


    }

    public void Eat() {
    	
    	handler.getWorld().player.setJustAte(false);   	
    	//score keeps track of the points earned from eating apples 
    	//Here is the bad apple code. If he eats after time is over 800 he will loss a tail
    	if(time >= 800) {
    		handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]= false; 
            handler.getWorld().body.removeLast();
            
            //score is being taken off
            	score -= Math.sqrt(2*score+1); 
            	score1 = (float) score;
        	lenght --;
        	
        	handler.getWorld().appleLocation[xCoord][yCoord]=false;
            handler.getWorld().appleOnBoard=false;
        	
        }
    	
    	//Here is bussiness as usual if he eats a good apple
    	else if(time < 800){
        lenght++;
    	score = Math.sqrt(2*score+1); 
    	score1 = (float) score;
    	
    	
   
        Tail tail= null;

        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }

                else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
        	handler.getWorld().body.addLast(tail);}}
    	speed4Score++;
    	time = 0;
    	//if the score is less than 0 the user will lose.
    	if(score1 < 0) {
    		kill();
   		   State.setState(handler.getGame().gameOverState);
    	}
    }

    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;

            }
        }
    }

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }

}
