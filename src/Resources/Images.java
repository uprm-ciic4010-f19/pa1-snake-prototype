package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static ImageIcon icon;
    public static BufferedImage snakePause;
    public static BufferedImage gameOver;
    public static BufferedImage[] Pause1;
//    public static BufferedImage[] replay;
//    public static BufferedImage[] playAgain;
    public static BufferedImage[] playAgain;
    public static BufferedImage[] replay;

    public Images() {

        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Pause1 = new BufferedImage[0];
//        replay = new BufferedImage[0];
//        playAgain = new BufferedImage[0];
        playAgain = new BufferedImage[2];
        replay = new BufferedImage[2];
        

        try {

            snakePause = ImageIO.read(getClass().getResourceAsStream("/Sheets/snakePause.png"));
            gameOver = ImageIO.read(getClass().getResourceAsStream("/Sheets/gameOver.png"));
            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Title.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            //Resume
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause1.png"));
            playAgain[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/playAgain.png"));
            replay[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/replay.png"));
            //ResumeP
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause1.png"));
            playAgain[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/playAgain.png"));
            replay[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/replay.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormBut.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverBut.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedBut.png"));//clickbut
           
            //Pause1[2] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause1.png"));
           // replay[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/replay.png"));
            //playAgain[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/playAgain"));

            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));


        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
