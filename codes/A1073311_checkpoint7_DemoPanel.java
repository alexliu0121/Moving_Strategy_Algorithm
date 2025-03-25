
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A1073311_checkpoint7_DemoPanel extends JPanel {
    //Description : the obstacle block set.
    private ArrayList<A1073311_checkpoint7_Block> obstacle;
    //Description : the sand block set.
    private ArrayList<A1073311_checkpoint7_Block> sands;
    //Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer,Image> obstacleImg;
    //Description : the image object of the map.
    private Image mapImg;
    //Description : the displaysize of the map
    private int scaler;
    //Description : the normal image size.
    //Hint : while the mapsize is not normal size, you have to think of the displaysize.
    private int originalGridLen;
    //Description : the image displaysize.
    private int gridLen;
    //Description : the map center point x-axis location.
    //Hint : While dragging the map, you may need to set the map location via this.
    private Integer centerX;
    //Description : the map center point y-axis location.
    //Hint : While dragging the map, you may need to set the map location via this.
    private Integer centerY; 
    //Description : the player object.
    private A1073311_checkpoint7_Player player;
    //Description : the path that the player had walked through.
    private ArrayList<A1073311_checkpoint7_Block> path;
    //Description : while moving, the target block;
    private A1073311_checkpoint7_Block target;
    public A1073311_checkpoint7_DemoPanel(A1073311_checkpoint7_Player player, ArrayList<A1073311_checkpoint7_Block> obstacle,int scaler,ArrayList<A1073311_checkpoint7_Block> sands){
        this.mapImg = new ImageIcon("Resource/map.png").getImage();
        this.originalGridLen = 256;
        this.obstacle = obstacle;
        this.scaler = scaler;   
        this.gridLen = originalGridLen/scaler;
        this.player = player;
        this.sands = sands;
        this.add(player);
        this.path = new ArrayList<A1073311_checkpoint7_Block>();
        this.target = null;
        /*********************************The Past TODO (Checkpoint3)********************************
         * 
         * TODO(Past): You need to set the center point location of the map into variable centerX, centerY.
         * 
         * Hint:  While setting the location, you have to consider about the scaler of the map. 
         * 
         **********************************The End of the TODO***************************************/

        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/
        centerX=4096/2/scaler;
        centerY=4096/2/scaler;

         /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
        
    }

    //Description : While painting this JPanel, we draw map on the given location and other obstacles.
    //Hint : while drawing the map, the given location is calculated by centerX/Y.
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int mapX = this.getWidth()/2-centerX;
        int mapY = this.getHeight()/2-centerY;
        g.drawImage(this.mapImg,mapX,mapY,this.mapImg.getWidth(null)/scaler,this.mapImg.getHeight(null)/scaler,null);
        for(A1073311_checkpoint7_Block x : obstacle){
            g.drawImage(x.getImg(),mapX + x.getX()*gridLen,mapY + x.getY()*gridLen, gridLen, gridLen, null);
        }
        for(A1073311_checkpoint7_Block x : sands){
            g.drawImage(x.getImg(),mapX + x.getX()*gridLen,mapY + x.getY()*gridLen, gridLen, gridLen, null);
        }
        if (player.getOnClick()){
            g.drawRect(player.getX(),player.getY(),gridLen ,gridLen );
        }
        
        
        if(!player.getMoving()){
            player.setLocation(mapX + player.getMapX()*gridLen,mapY+ player.getMapY()*gridLen);
        }else{
            Color color = new Color(255,0,0,64);
            g.setColor(color);
            g.fillRect(mapX + target.getX()*gridLen,mapY + target.getY()*gridLen, gridLen, gridLen);
            color = new Color(0,0,0,64);
            g.setColor(color);
            for(A1073311_checkpoint7_Block x : path){
                g.fillRect(mapX + x.getX()*gridLen,mapY + x.getY()*gridLen, gridLen, gridLen);
            }
        }
    }
    public Integer getCenterX(){
        return this.centerX;
    }
    public void setCenterX(Integer centerX){
        this.centerX = centerX;
    }
    public Integer getCenterY(){
        return this.centerY;
    }
    public void setCenterY(Integer centerY){
        this.centerY = centerY;
    }
    public int getGridLen(){
        return this.gridLen;
    }
    public void setGridLen(int gridLen){
        this.gridLen = gridLen;
    }
    public int getMapWidth(){
        return mapImg.getWidth(null)/scaler;
    }
    public int getMapHeight(){
        return mapImg.getHeight(null)/scaler;
    }
    public void setPath(ArrayList<A1073311_checkpoint7_Block> path){
        this.path = path;
    }
    public int getXGrids(){
        return (int)(getMapWidth()/gridLen);
    }
    public int getYGrids(){
        return (int)(getMapHeight()/gridLen);
    }
    public void setTarget(A1073311_checkpoint7_Block target){
        this.target = target;
    }
    public A1073311_checkpoint7_Block getTarget(){
        return this.target;
    }
}
