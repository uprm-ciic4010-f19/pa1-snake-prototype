package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class PauseState extends State {

    private int count = 0;
    private UIManager uiManager;

    public PauseState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


//RESUME BUTTON
        uiManager.addObjects(new UIImageButton(600, 400, 128, 64, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
        }));
        
        //RESTART BUTTON
        uiManager.addObjects(new UIImageButton(600, 600, 128, 64, Images.replay, () -> {
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
        g.drawImage(Images.snakePause,0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);

    }
}
