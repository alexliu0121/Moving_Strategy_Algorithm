import java.util.*;

/***********************************
 * Hint ********************************************
 * 
 * To finish the object, we define some variables in this code.
 * 
 * 1. Absolute Location --> The GUI location in Swing definition.
 * 2. Grid Location (GUI version) --> We divided the map into 16*16 grids,
 * use this variable to record the certain location of a grid.
 * 3. Delta X/Y --> The x,y location offsets of the map in the panel.
 * We calculate these two scalars by substracting the center point of the map
 * (width/2,height/2)
 * and the current panel center point (CenterX, CenterY) in panel.
 * 4. Relative Location --> The x,y location for painting the objects (e.g.
 * obstacle and players) on the panel.
 * We calculate this using Delta X/Y plus the grid location mutiply the length
 * of the grid (Absolute Coordinate).
 * 
 ********************************** The End of the Hint
 ***************************************/

public class A1073311_checkpoint7_PlayerMovement implements Runnable {
    // Description : the player object.
    private A1073311_checkpoint7_Player player;
    // Description : the panel object.
    private A1073311_checkpoint7_DemoPanel panel;
    // Description : while moving the player, it needs to move by.(in Grid Location)
    private A1073311_checkpoint7_RouteLinkedList route;
    // Description : the X absolute location the destination is on.
    private int newPointX;
    // Description : the Y absolute location the destination is on.
    private int newPointY;
    // Description : the time that the player moving in per step
    private int milliseconds;
    // Description : the path that the player has walked through.
    private ArrayList<A1073311_checkpoint7_Block> walkedPath;
    // Description : UCS : 2, BFS : 1, DFS : 0
    private int algorithm;
    // Description : the map with all blocks. (a. k. a. state space)
    // You can get the location block you want with typing map[x][y].
    private A1073311_checkpoint7_Block[][] map;
    // Description : record the block and it's ParentBlock.
    private HashMap<A1073311_checkpoint7_Block, A1073311_checkpoint7_Block> ParentBlock;
    // Description : record the block cost;
    private HashMap<A1073311_checkpoint7_Block, Integer> PathCost;
    // Description : record the block that has be visited.
    private boolean[][] visited;
    // Description : the player current location ( the root of the tree).
    private A1073311_checkpoint7_Block root;
    // Description : if algorithm equals 0 means using DFS.
    private final int DFS = 0;
    // Description : if algorithm equals 1 means using BFS.
    private final int BFS = 1;
    // Description : if algorithm equals 1 means using UCS.
    private final int UCS = 2;

    public A1073311_checkpoint7_PlayerMovement(A1073311_checkpoint7_Player player, A1073311_checkpoint7_DemoPanel panel,
            int newPointX, int newPointY, int milliseconds, int algorithm, A1073311_checkpoint7_Block[][] map) {
        this.player = player;
        this.panel = panel;
        this.newPointX = newPointX;
        this.newPointY = newPointY;
        this.milliseconds = milliseconds;
        this.route = new A1073311_checkpoint7_RouteLinkedList();
        this.walkedPath = new ArrayList<A1073311_checkpoint7_Block>();
        this.algorithm = algorithm;
        this.map = map;
        this.ParentBlock = new HashMap<A1073311_checkpoint7_Block, A1073311_checkpoint7_Block>();
        this.PathCost = new HashMap<A1073311_checkpoint7_Block, Integer>();
        this.visited = new boolean[panel.getXGrids()][panel.getYGrids()];
        this.root = map[player.getMapX()][player.getMapY()];
        for (int x = 0; x < panel.getXGrids(); x++) {
            for (int y = 0; y < panel.getYGrids(); y++) {
                visited[x][y] = false;
            }
        }
    }

    // Description : The Thread object running function(The thing that this thread
    // doing ).
    @Override
    public void run() {

        /*********************************
         * The Past TODO (Checkpoint6)********************************
         * 
         * TODO(1.1): This time you have need to calculate the target location (Grid
         * verb.) first
         * and get the target block.
         * TODO(1.2): After you find the target block, you have to set target in panel
         * via panel.setTarget()
         * and repaint it in order to display which block you clicked.
         * TODO(1.3): generate the route via detectWay().
         * TODO(1.4): follow the route via followRoute();
         * TODO(1.5): After following the route, you have to update the player's
         * status.(player.setOnClick(),
         * player.setMoving()) and clear the path that the player has walked through via
         * panel.setPath()
         * and repaint the panel.
         * 
         ********************************** The End of the TODO
         **************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        player.setMoving(true);
        // System.out.println("x:"+newPointX+",y:"+newPointY+",grid:"+panel.getGridLen());
        int x = (newPointX - (panel.getWidth() / 2 - panel.getCenterX())) / panel.getGridLen();
        int y = (newPointY - (panel.getHeight() / 2 - panel.getCenterY())) / panel.getGridLen();

        if (x < panel.getXGrids() && x >= 0 && y < panel.getYGrids() && y >= 0) {
            if (map[x][y].getType() != "obstacle") {
                A1073311_checkpoint7_Block target = map[x][y];
                panel.setTarget(target);
                panel.repaint();
                route = detectWay(root, target, ParentBlock, visited);
                checkCollided(player.getMapX(), player.getMapY());
                followRoute(route);
            }
        }

        player.setOnClick(false);
        player.setMoving(false);
        panel.setPath(new ArrayList<>());
        panel.repaint();
        player.repaint();
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    private A1073311_checkpoint7_RouteLinkedList detectWay(A1073311_checkpoint7_Block root,
            A1073311_checkpoint7_Block target,
            HashMap<A1073311_checkpoint7_Block, A1073311_checkpoint7_Block> ParentBlock, boolean[][] visited) {
        /**********************************
         * The TODO This Time (Checkpoint7)**************************
         * 
         * TODO(Past): For the TODO here, you have to Search the target block via the
         * function Search()
         * and back trace and generate the route by the function createRoute();
         * TODO(1.1): For the TODO here, add the case that the algorithm is UCS.
         * 
         * Hint(1): The BFS algorithm needs to use the queue to work, so we make a
         * object named BlockQueue
         * for BFS.
         * Hint(2): The DFS algorithm needs to use the stack to work, so we make a
         * object named BlockStack
         * for DFS.
         * Hint(3): These two object are all implemnt the fringe, the detail description
         * can be found
         * in the code of Fringe.java BlockQueue.java BlockStack.java.
         * Hint(4): Before send the fringe into the function Search(), you have to add
         * the root (the
         * player current location) into fringe.
         * Hint(5): (New) The UCS algorithm needs to use the priorityQueue to work, so
         * we make a object named
         * BlockPriorityQueue for UCS.
         * Hint(6): (New) To calculate the priority, you have to implement a
         * Comparator<block> object and make
         * it as an input in the constructor of BlockPriorityQueue.
         * Hint(7): (New) Before starting the searching, you need to initialize the
         * PathCost and set the root with
         * its cost.
         ********************************** The End of the TODO
         **************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        if (algorithm == 0) {
            A1073311_checkpoint7_BlockQueue bq = new A1073311_checkpoint7_BlockQueue();
            bq.add(root);
            return createRoute(ParentBlock, Search(bq, target, ParentBlock, visited));
        } else if (algorithm == 1) {
            A1073311_checkpoint7_BlockStack bs = new A1073311_checkpoint7_BlockStack();
            bs.add(root);
            return createRoute(ParentBlock, Search(bs, target, ParentBlock, visited));
        } else {
            A1073311_checkpoint7_BlockPriorityQueue bp = new A1073311_checkpoint7_BlockPriorityQueue(
                    new Comparator<A1073311_checkpoint7_Block>() {
                        @Override
                        public int compare(A1073311_checkpoint7_Block x, A1073311_checkpoint7_Block y) {
                            if (PathCost.get(x) < PathCost.get(y)) {
                                return -1;
                            } else if (PathCost.get(x) > PathCost.get(y)) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
            PathCost.put(root, root.getCost());
            bp.add(root);
            return createRoute(ParentBlock, Search(bp, target, ParentBlock, visited));
        }
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }

    public A1073311_checkpoint7_Block Search(A1073311_checkpoint7_Fringe fringe, A1073311_checkpoint7_Block target,
            HashMap<A1073311_checkpoint7_Block, A1073311_checkpoint7_Block> ParentBlock, boolean[][] visited) {
        /*********************************
         * The Past TODO (Checkpoint6)********************************
         * 
         * TODO(Past): For the TODO here, you have to implement the searching funciton;
         * TODO(Past): You MUST print the message of "Searching at (x, y)" in order to
         * let us check if you sucessfully do it.
         * TODO(Past): After you find the target, you just need to return the target
         * block.
         * //System.out.println("Searching at ("+Integer.toString(YOURBLOCK.getX())+",
         * "+Integer.toString(YOURBLOCK.getY())+")");
         * 
         * Hint1: Page 44 in the teaching material "lecture05" may be your reference.
         * Hint2: if the target can not be search you should return null(that means
         * failure).
         * 
         * pseudo code is provided here:
         * 1. get the block from fringe.
         * 2. print the message
         * 3. if that block equals target return it.
         * 4. if not, expand the block and insert then into fringe.
         * 5. return to 1. until the fringe does not have anyting in it.
         * 
         ********************************** The End of the TODO
         **************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        while (fringe.isEmpty() != true) {
            A1073311_checkpoint7_Block b = fringe.remove();
            System.out.println("Searching at (" + b.getX() + "," + b.getY() + ")");
            if (b == target) {
                return b;
            } else {
                ArrayList<A1073311_checkpoint7_Block> expand = expand(b, ParentBlock, visited);
                for (int i = 0; i < expand.size(); i++) {
                    fringe.add(expand.get(i));
                }
            }
        }
        return null;

        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }

    private ArrayList<A1073311_checkpoint7_Block> expand(A1073311_checkpoint7_Block currentBlock,
            HashMap<A1073311_checkpoint7_Block, A1073311_checkpoint7_Block> ParentBlock, boolean[][] visited) {
        /*********************************
         * The TODO This Time (Checkpoint7)*****************************
         * 
         * TODO(Past): For the TODO here, you have to implement the expand funciton and
         * return the Blocks(successor);
         * TODO(Past): the order that you expand is North(Up) West(Left) South(Down)
         * East(Right).
         * TODO(Past): before adding the block into successor, you have to check if it
         * is valid by checkBlock().
         * TODO(2.1): For the TODO here, you have to calculate the cost of the path that
         * the player walked from root
         * to new blocks and set it into the HashMap pathCost.
         * 
         * Hint1: Page 44 in the teaching material "lecture05" may be your reference.
         * Hint2: While the block is valid, before you add the block into successor,
         * you should set its ParentBlock (We prepare a HashMap to implement this).
         * And you should also set it is visited. (We prepare 2D boolean array for you)
         * (the (x,y) block <--> visited[x][y] )
         * Hint3: You can get the cost of the block via Block.getCost().
         ********************************** The End of the TODO
         **************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        ArrayList<A1073311_checkpoint7_Block> four = new ArrayList<A1073311_checkpoint7_Block>();
        // for(int i=0;i<=3;i++){
        if (checkBlock(currentBlock.getX(), currentBlock.getY() - 1, visited)) {
            if (algorithm == 2) {
                int cost = PathCost.get(currentBlock);
                PathCost.put(map[currentBlock.getX()][currentBlock.getY() - 1],
                        cost + map[currentBlock.getX()][currentBlock.getY() - 1].getCost());
            }
            ParentBlock.put(map[currentBlock.getX()][currentBlock.getY() - 1], currentBlock);
            four.add(map[currentBlock.getX()][currentBlock.getY() - 1]);
        }
        if (currentBlock.getX() < panel.getXGrids() && currentBlock.getX() >= 0
                && currentBlock.getY() - 1 < panel.getYGrids() && currentBlock.getY() - 1 >= 0) {
            visited[currentBlock.getX()][currentBlock.getY() - 1] = true;
        }
        if (checkBlock(currentBlock.getX() - 1, currentBlock.getY(), visited)) {
            if (algorithm == 2) {
                int cost = PathCost.get(currentBlock);
                PathCost.put(map[currentBlock.getX() - 1][currentBlock.getY()],
                        cost + map[currentBlock.getX() - 1][currentBlock.getY()].getCost());
            }
            ParentBlock.put(map[currentBlock.getX() - 1][currentBlock.getY()], currentBlock);
            four.add(map[currentBlock.getX() - 1][currentBlock.getY()]);
        }
        if (currentBlock.getX() - 1 < panel.getXGrids() && currentBlock.getX() - 1 >= 0
                && currentBlock.getY() < panel.getYGrids() && currentBlock.getY() >= 0) {
            visited[currentBlock.getX() - 1][currentBlock.getY()] = true;
        }
        if (checkBlock(currentBlock.getX(), currentBlock.getY() + 1, visited)) {
            if (algorithm == 2) {
                int cost = PathCost.get(currentBlock);
                PathCost.put(map[currentBlock.getX()][currentBlock.getY() + 1],
                        cost + map[currentBlock.getX()][currentBlock.getY() + 1].getCost());
            }
            ParentBlock.put(map[currentBlock.getX()][currentBlock.getY() + 1], currentBlock);
            four.add(map[currentBlock.getX()][currentBlock.getY() + 1]);
        }
        if (currentBlock.getX() < panel.getXGrids() && currentBlock.getX() >= 0
                && currentBlock.getY() + 1 < panel.getYGrids() && currentBlock.getY() + 1 >= 0) {
            visited[currentBlock.getX()][currentBlock.getY() + 1] = true;
        }
        if (checkBlock(currentBlock.getX() + 1, currentBlock.getY(), visited)) {
            if (algorithm == 2) {
                int cost = PathCost.get(currentBlock);
                PathCost.put(map[currentBlock.getX() + 1][currentBlock.getY()],
                        cost + map[currentBlock.getX() + 1][currentBlock.getY()].getCost());
            }
            ParentBlock.put(map[currentBlock.getX() + 1][currentBlock.getY()], currentBlock);
            four.add(map[currentBlock.getX() + 1][currentBlock.getY()]);
        }
        if (currentBlock.getX() + 1 < panel.getXGrids() && currentBlock.getX() + 1 >= 0
                && currentBlock.getY() < panel.getYGrids() && currentBlock.getY() >= 0) {
            visited[currentBlock.getX() + 1][currentBlock.getY()] = true;
        }

        return four;

        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }

    private Boolean checkBlock(int newLocationX, int newLocationY, boolean[][] visited) {
        /*********************************
         * The Past TODO (Checkpoint6)********************************
         * 
         * TODO(Past): For the TODO here, you have to implement the checkBlock funciton
         * and return if the Block is valid or not;
         * TODO(Past): First you have to check the new block is in your map or not.
         * TODO(Past): Second the block can not be obstacle.
         * TODO(Past): Third the block should not be visited.
         * 
         ********************************** The End of the TODO
         **************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        if (newLocationX >= panel.getXGrids()) {
            return false;
        } else if (newLocationX < 0) {
            return false;
        } else if (newLocationY < 0) {
            return false;
        } else if (newLocationY >= panel.getYGrids()) {
            return false;
        } else if (visited[newLocationX][newLocationY] == true) {
            return false;
        } else if (map[newLocationX][newLocationY].getType() == "obstacle") {
            return false;
        } else {
            return true;
        }
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }

    private A1073311_checkpoint7_RouteLinkedList createRoute(
            HashMap<A1073311_checkpoint7_Block, A1073311_checkpoint7_Block> ParentBlock,
            A1073311_checkpoint7_Block target) {
        /*********************************
         * The Past TODO (Checkpoint6)********************************
         * 
         * TODO(Past): For the TODO here, you have to trace back the route and return
         * the route;
         * 
         * Hint1: You can get the parent block of target by HashMap ParentBlock, thus
         * you can calculate
         * the last step of the route. And then you get the parent block of the parent
         * block of target,
         * you can calculate the backward step and so on.
         * 
         * presudo code is provided here:
         * 1. get parent block
         * 2. calculate the delta location
         * 3. insert into head
         * 4. make the target equals its parent block and so on.
         * 
         ********************************** The End of the TODO
         **************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        A1073311_checkpoint7_RouteLinkedList temp = new A1073311_checkpoint7_RouteLinkedList(null);
        boolean done = false;
        A1073311_checkpoint7_Block parent;
        int olddirec = 0;
        int oldaxis = 0;
        int direc = 0;
        int axis = 0;
        while (target != root) {
            parent = ParentBlock.get(target);
            if (parent == null) {
                return temp;
            }
            if (parent.getX() - target.getX() != 0) {
                axis = 0;
            } else {
                axis = 1;
            }
            if (parent.getX() - target.getX() > 0 || parent.getY() - target.getY() > 0) {
                direc = -1;
            } else if (parent.getX() - target.getX() < 0 || parent.getY() - target.getY() < 0) {
                direc = 1;
            } else {
                direc = 0;
            }
            temp.insert(oldaxis, olddirec, axis, direc);
            target = parent;
            oldaxis = axis;
            olddirec = direc;
            // if(target==root){
            // done=true;
            // }
        }
        return temp;

        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }

    private void followRoute(A1073311_checkpoint7_RouteLinkedList route) {

        /*********************************
         * The Past TODO (Checkpoint6)********************************
         * 
         * TODO(Past): This time in order to make moving more easily to trace and debug.
         * We make a ArrayList
         * walkedPath to record the block that the player had walked through. You have
         * to add the block into
         * walkedPath and set it into panel and then the panel will draw it
         * automatically.
         * 
         * pseudo code provide here
         * loop the route untill it does not have next node. # you have done this last
         * time.
         * movePlayerOneGrid() (base on the value of route node). # you have done this
         * last time.
         * add block that the player walk through into walkedPath. # This time you have
         * to implement it.
         * set the walkedPath in panel by panel.setPath().# This time you have to
         * implement it.
         * update the player's location # you have done this last time.
         * checkCollided() # you have done this last time.
         * 
         ********************************** The End of the TODO
         **************************************/
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        A1073311_checkpoint7_Node current = route.getHead();
        ArrayList<A1073311_checkpoint7_Block> temp = new ArrayList<A1073311_checkpoint7_Block>();

        while (current != null) {
            A1073311_checkpoint7_Block walked = map[player.getMapX()][player.getMapY()];
            movePlayerOneGrid(panel, player, current.getDirection(), current.getAxis(), milliseconds);
            temp.add(walked);
            panel.setPath(temp);
            // checkCollided(player.getMapX(), player.getMapY(), obstacle);
            /* route.setHead(route.getHead().getNext()); */
            if (current.getAxis() == 1) {
                if (current.getDirection() == 1) {
                    player.setMapY(player.getMapY() + current.getDirection());
                } else if (current.getDirection() == -1) {
                    player.setMapY(player.getMapY() + current.getDirection());
                }
            } else if (current.getAxis() == 0) {
                if (current.getDirection() == 1) {
                    player.setMapX(player.getMapX() + current.getDirection());
                } else if (current.getDirection() == -1) {
                    player.setMapX(player.getMapX() + current.getDirection());
                }
            }
            checkCollided(player.getMapX(), player.getMapY());

            current = current.getNext();
        }
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    // Description : Check the new point(Grid location) is collided or not.
    private void checkCollided(Integer newPointX, Integer newPointY) {
        if (map[newPointX][newPointY].getType() == "obstacle") {
            A1073311_checkpoint7_BugFrame bugFrame = new A1073311_checkpoint7_BugFrame();
            bugFrame.setVisible(true);
        }
        if (newPointX < 0 || newPointY < 0 || newPointY * this.panel.getGridLen() >= this.panel.getMapHeight()
                || newPointX * this.panel.getGridLen() >= this.panel.getMapWidth()) {
            A1073311_checkpoint7_BugFrame bugFrame = new A1073311_checkpoint7_BugFrame();
            bugFrame.setVisible(true);
        }
    }

    // Description : moving the player one gird location via this function.
    private void movePlayerOneGrid(A1073311_checkpoint7_DemoPanel panel, A1073311_checkpoint7_Player player,
            int direction, int axis, int milliseconds) {

        for (int i = 1; i <= player.getStepTime(); i++) {
            movePlayerOneStep(panel, player, direction, axis, milliseconds);
        }
    }

    private void movePlayerOneStep(A1073311_checkpoint7_DemoPanel panel, A1073311_checkpoint7_Player player,
            int direction, int axis, int milliseconds) {
        /*********************************
         * The Past TODO (Checkpoint4)********************************
         * 
         * TODO(Past) In this function you have to make your player moving one step
         * here.
         * You need to continue setting the player or panel location,
         * but with stopping a little time in order to make the player moving
         * continusly.
         * We have already prepared the continus function(movePlayerOneGrid()),
         * but you have to realize the stopping time and setting the player or panel
         * location here
         * There is a requirement that the player needs to keep being in the center
         * point of the windows.
         * So maybe not only player you have to consider, but also panel you need to do
         * too.
         * 
         * Hint pseudo code provide here
         * doNothing()
         * if axis = 1 -> move in y else x
         * panel.setCenter( new Center + player.getPerStep()) # Note the direction
         * panel.repaint(),player.repaint()
         * 
         * 
         ********************************** The End of the TODO
         ***************************************/
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        doNothing(milliseconds);
        if (axis == 1) {
            if (direction == -1) {
                /*
                 * panel.setCenterX(panel.getCenterX()-player.getPerStep()/panel.getGridLen());
                 */
                panel.setCenterY(panel.getCenterY() - player.getPerStep());
            } else if (direction == 1) {
                /*
                 * panel.setCenterX(panel.getCenterX()+player.getPerStep()/panel.getGridLen());
                 */
                panel.setCenterY(panel.getCenterY() + player.getPerStep());
            }
        } else if (axis == 0) {
            if (direction == -1) {
                panel.setCenterX(panel.getCenterX() - player.getPerStep());
                /*
                 * panel.setCenterY(panel.getCenterY()-player.getPerStep()/panel.getGridLen());
                 */
            } else if (direction == 1) {
                panel.setCenterX(panel.getCenterX() + player.getPerStep());
                /*
                 * panel.setCenterY(panel.getCenterY()+player.getPerStep()/panel.getGridLen());
                 */
            }
        }
        panel.repaint();
        player.repaint();
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    // Description : Stop the thread in milliseconds.
    private static void doNothing(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Unexpected interruption");
            System.exit(0);
        }
    }

}
