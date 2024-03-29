package Game.GameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

    private int count = 0;
    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        //Restart button
        uiManager.addObjects(new UIImageButton(600, 600, 128, 64, Images.playAgain, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().reStart();
            State.setState(handler.getGame().gameState);
        }));






    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().gameState);
        }


    }

    @Override
    public void render(Graphics g) {
    	g.fillRect(0,0,handler.getWidth(),handler.getHeight());
        g.drawImage(Images.gameOver,0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);

    }
}
