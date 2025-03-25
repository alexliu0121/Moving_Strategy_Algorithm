import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class A1073311_checkpoint7_DemoFrame extends JFrame {
    //Description : Width of Frame
    private int FWidth;
    //Description : Height of Frame
    private int FHeight;
    //Description : the displaysize of the map
    private int jfScaler;
    //Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer,Image> obstacleImg;
    //Description : the filenames  of the obstacle image set.  bar_id -> filename 
    private HashMap<Integer, String> typeChar;
    //Description : the obstacle location set queryed from database
    private ArrayList<Integer[]> obstacleDataStructure; 
    //Description : the obstacle location set in GUI index version.
    private ArrayList<A1073311_checkpoint7_Block> obstacle;
    //Description : the sand location set queryed from database
    private ArrayList<Integer[]> sandsDataStructure; 
    //Description : the sand location set in GUI index version.
    private ArrayList<A1073311_checkpoint7_Block> sands;
    //Description : the object to query data.
    private A1073311_checkpoint7_QueryDB querydb;
    //Description : the player object.
    private A1073311_checkpoint7_Player player;
    //Description : the X axis that mouse pressed.
    private int PressedX;
    //Description : the Y axis that mouse pressed.
    private int PressedY;
    //Description : the X axis that mouse release.
    private int ReleasedX;
    //Description : the Y axis that mouse release.
    private int ReleasedY;
    //Description : the time that the player moving in per step.
    private int milliseconds;
    //Description : algorithm BFS : 1, DFS : 0.
    private int algorithm;
    //Description : the map with all blocks. (a. k. a. state space)
    //You can get the location block you want with typing map[x][y].
    private A1073311_checkpoint7_Block[][] map;
    //Description : the cost of sand weight;
    private final int SANDWEIGHT = 3;
    //Description : the cost of space weight;
    private final int SAPCEWEIGHT = 1;
    //Description : the String that is the block type of the obstacle.
    private final String OBSTACLETYPE = "obstacle";
    //Description : the String that is the block type of the sand.
    private final String SANDTYPE = "sand";
    public A1073311_checkpoint7_DemoFrame(int FWidth, int FHeight,String mapID,int jfScaler, int milliseconds, int algorithm) throws HeadlessException {
        this.FWidth = FWidth;
        this.FHeight = FHeight;
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FWidth, FHeight);
        this.PressedX = 0;
        this.PressedY = 0;
        this.ReleasedX = 0;
        this.ReleasedY = 0;
        this.jfScaler = jfScaler;
        this.milliseconds = milliseconds;
        this.obstacleImg = new HashMap<>();
        this.typeChar = new HashMap<Integer, String>();
        this.obstacle = new ArrayList<A1073311_checkpoint7_Block>();
        this.obstacleDataStructure = new ArrayList<Integer[]>();
        this.sands = new ArrayList<A1073311_checkpoint7_Block>();
        this.sandsDataStructure = new ArrayList<Integer[]>();
        this.algorithm = algorithm;
        this.querydb = new A1073311_checkpoint7_QueryDB(mapID);
        this.obstacleDataStructure = querydb.getObstacle();
        this.typeChar = querydb.getObstacleImg();
        for (Integer key : typeChar.keySet()) {
            obstacleImg.put(key,new ImageIcon("./Resource/"+typeChar.get(key)).getImage());
        }
        this.obstacle = toGUIIdx(obstacleDataStructure, OBSTACLETYPE);
        this.player = new A1073311_checkpoint7_Player("player",jfScaler,SwingConstants.CENTER);
        this.sandsDataStructure = querydb.getSands();
        this.sands = toGUIIdx(sandsDataStructure, SANDTYPE);
        A1073311_checkpoint7_DemoPanel panel = new A1073311_checkpoint7_DemoPanel(player, obstacle,jfScaler, sands);
        this.add(panel);
        this.map = createMap(panel.getXGrids(), panel.getYGrids());

        this.addComponentListener(new ComponentAdapter() {
            @Override
            //Description : While resizing the windows, the evnet will be happenned(Reset the location of player).
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int x = panel.getWidth()/2-panel.getCenterX();
                int y = panel.getHeight()/2-panel.getCenterY();
                player.setLocation(x + player.getMapX()*panel.getGridLen(),y + player.getMapY()*panel.getGridLen());
            }
        });

        player.addMouseListener(new MouseAdapter() {
            @Override
            //Description : the event happenned while player be clicked.
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                /*********************************The Past TODO (Checkpoint4)********************************
                 * 
                 * TODO(Past):  When player is clicked, the moving action needs to be ready for realize.
                 *              Therefore, the player must set into the center location of the map.
                 * Hint:  While seting the location, you may have to set the center location of the map as the player's location.
                 * 
                 **********************************The End of the TODO***************************************/
                /*******************************************************************************************
                 START OF YOUR CODE
                 ********************************************************************************************/
                player.setOnClick(true);
                if(player.getOnClick()){
                    panel.setCenterX(player.getMapX()*panel.getGridLen());
                    panel.setCenterY(player.getMapY()*panel.getGridLen());
                }
                
                panel.repaint();
                 /********************************************************************************************
                 END OF YOUR CODE
                 ********************************************************************************************/

            }
        });
        /*********************************The Past TODO (Checkpoint4)********************************
         * 
         * TODO(Past):  For mouse event here, you should implement map drag here.
         *              And you have to aslo implement the moving action here while the player has been clicked.
         * Hint:  For example, if you click on the top and release in the bottom, 
         *        the map should be dragged from up to down.
         * Hint2: You should got both pressed location and release location 
         *        and than calculate the moving.
         * Hint3: While the map dragging, you are not allowed to make the player moving, and vice versa.
         **********************************The End of the TODO***************************************/
        panel.addMouseListener(new MouseAdapter() {
            //Description : the event happenned while mouse be pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // TODO(Past) While dragging the map, you need to get the location of mousePressed.
                // TODO(Past) If player is clicked(judgement by player.getOnClick()), you don't have to do dragging.
                /********************************************************************************************
                 START OF YOUR CODE
                 ********************************************************************************************/
                PressedX=e.getX();
                PressedY=e.getY();
                 /********************************************************************************************
                 END OF YOUR CODE
                 ********************************************************************************************/
            }
            //Description : the event happenned while mouse be released
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                /*********************************The Past TODO (Checkpoint4)********************************
                 * 
                 * TODO(past)   While dragging the map, you need to get the location of mouseReleased.
                 * TODO(past)   The map displacement will be calculated by Released location minus Pressed location.
                 * TODO(past)   And then make the map moving by controlling it's location variable
                 *              and repaint the map via repaint() in object JPanel.
                 * TODO(past)   If player is clicked(judgement by player.getOnClick()), you don't have to do dragging.
                 * TODO(past)   If player is clicked(judgement by player.getOnClick()), you have to moving the player by the multithread.
                 * 
                 * Hint1        You can annouce the object PlayerMovement and realize the multithread here.
                 * Hint2        While moving the player(judgement by player.getMoving()), you can't move the player again.
                 * 
                 **********************************The End of the TODO***************************************/

                /*************************************Updated Description***********************************
                 * 
                 * In order to make code clearly, we make a Object named Block to replace and implement the 
                 * all of the location on map (includes obstacles). This object contatins four variable, and you can see 
                 * them at the code of Block.java. 
                 * TODO(2)
                 * For the TODO here, we modify the constructor of PlayerMovement input into (player, ArrayList<Block>
                 * DemoPanel, int, int, int, int, Block[][]). You have to additionally input algroithm and map into the  
                 * constructor this time.
                 * 
                 **********************************The End of the Description*******************************/


                /********************************************************************************************
                 START OF YOUR CODE
                 ********************************************************************************************/
                ReleasedX=e.getX();
                ReleasedY=e.getY();
                int changeX=0;
                int changeY=0;
                changeX=(ReleasedX-PressedX)/jfScaler;
                changeY=(ReleasedY-PressedY)/jfScaler;
                if(player.getOnClick()!=true){
                    panel.setCenterX(panel.getCenterX()-changeX);
                    panel.setCenterY(panel.getCenterY()-changeY);
                    repaint();
                }

                A1073311_checkpoint7_PlayerMovement p=new A1073311_checkpoint7_PlayerMovement(player, panel,  ReleasedX, ReleasedY, milliseconds,algorithm,map);
                if(player.getOnClick()==true){
                    if(player.getMoving()!=true){
                        Thread r1=new Thread(p);
                        r1.start();
                        player.setMoving(true);
                    }
                }

                 /********************************************************************************************
                 END OF YOUR CODE
                 ********************************************************************************************/
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            //Description : the event happenned while mouse be dragged.
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                /*********************************The Past TODO (Checkpoint3)********************************
                 * 
                 * TODO(past)(optional) we hope you can drag the map smoothly, you can override this function
                 * 
                 **********************************The End of the TODO***************************************/

                /********************************************************************************************
                 START OF YOUR CODE
                 ********************************************************************************************/
                ReleasedX=e.getX();
                ReleasedY=e.getY();
                int changeX=0;
                int changeY=0;
                
                changeX=(ReleasedX-PressedX);
                changeY=(ReleasedY-PressedY);
                
                if(player.getOnClick()!=true){
                    
                    panel.setCenterX(panel.getCenterX()-changeX);
                    panel.setCenterY(panel.getCenterY()-changeY);
                    repaint();
                    PressedX=ReleasedX;
                    PressedY=ReleasedY;
                }
                /********************************************************************************************
                 END OF YOUR CODE
                 ********************************************************************************************/

            }
        });
    }
    
    //Description : transform the obstacle location from database version to GUI index version
    //              data is the database one, and the other.
    public ArrayList toGUIIdx(ArrayList<Integer[]> data, String blockType){
        ArrayList<A1073311_checkpoint7_Block> dataGui = new ArrayList<A1073311_checkpoint7_Block>();
        for(Integer[] x : data){
            if(blockType == "obstacle"){
                dataGui.add( new A1073311_checkpoint7_Block(x[1]-1, x[0]-1,blockType, obstacleImg.get(x[2]), 100000));
            }else if(blockType == "sand"){
                dataGui.add( new A1073311_checkpoint7_Block(x[1]-1, x[0]-1,blockType, new ImageIcon("./Resource/sand.jpg").getImage(), SANDWEIGHT));
            }
        }
        return dataGui;
    }
    //Description : create the map, if each of the loaction will be tag as "obstacle" or "space".
    //              If the location is space, it will be without it's own image.(set null)
    public A1073311_checkpoint7_Block[][] createMap(int height,int width){
        A1073311_checkpoint7_Block[][] map = new A1073311_checkpoint7_Block[width][height];
        for(A1073311_checkpoint7_Block block : obstacle){
            map[block.getX()][block.getY()] = block;
        }
        for(A1073311_checkpoint7_Block block : sands){
            map[block.getX()][block.getY()] = block;
        }
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(map[x][y] == null){
                    map[x][y] = new A1073311_checkpoint7_Block(x,y,"space",null, SAPCEWEIGHT);
                }
            }
        }
        return map;
    }
}
