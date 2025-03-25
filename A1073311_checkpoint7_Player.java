import javax.swing.*;
import java.awt.*;


public class A1073311_checkpoint7_Player extends JLabel {
    //Description : the absolute distance the player moving each "milliseconds".
    private int perStep; 
    //Description : the grid location X of player.
    private int mapX;
    //Description : the grid location Y of player.
    private int mapY;
    //Description : identify the player is onclick or not.
    private boolean onClick;
    //Description : the normal image size.
    private int originalGridLen;
    //Description : the time that the player moving in per grid.
    private int stepTime;
    //Description : the Image the player is.
    private ImageIcon icon;
    //Description : identify the player is moving or not.
    private boolean moving;
    //Description: this is the player's constructor, we set icon of player here and calculate the distance every step.
    //Hint text : "player", horizontalAlignment : SwingConstants.CENTER
    public A1073311_checkpoint7_Player(String text ,int scaler,int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.perStep = 32; 
        this.mapX = 7;
        this.mapY = 8;
        this.onClick = false;
        this.originalGridLen = 256;
        this.stepTime = originalGridLen/perStep;
        this.icon = new ImageIcon("Resource/player.png");
        this.moving = false;
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen/scaler, originalGridLen/scaler, Image.SCALE_DEFAULT);
        icon.setImage(img);
        this.setIcon(icon);
        this.perStep = perStep/scaler;
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    public int getMapX(){
        return this.mapX;
    }
    public void setMapX(int mapX){
        this.mapX = mapX;
    }
    public int getMapY(){
        return this.mapY;
    }
    public void setMapY(int mapY){
        this.mapY = mapY;
    }
    public boolean getOnClick(){
        return this.onClick;
    }
    public void setOnClick(boolean onClick){
        this.onClick = onClick;
    }
    public int getPerStep(){
        return this.perStep;
    }
    public void setPerStep(int perStep){
        this.perStep = perStep;
    }
    public int getStepTime(){
        return this.stepTime;
    }
    public void setStepTime(int stepTime){
        this.stepTime = stepTime;
    }
    public boolean getMoving(){
        return this.moving;
    }
    public void setMoving(boolean moving){
        this.moving = moving;
    }

}

