import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class A1073311_checkpoint7_QueryDB {
    // Description : the driver description of mysql
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // Description : the protocol description of mysql
    private static final String PROTOCOL = "jdbc:mysql:";
    // Description : the obstacle set queried from database.
    private static ArrayList<Integer[]> data;
    // Description : the filename of obstacle image queried from database.
    private static HashMap<Integer, String> typeChar;
    // Description : the ID of the map in database;
    private static String mapID;
    // Description : the sand set queried from database.
    private static ArrayList<Integer[]> sands;

    public A1073311_checkpoint7_QueryDB(String mapID) {
        this.data = new ArrayList<Integer[]>();
        this.sands = new ArrayList<Integer[]>();
        this.typeChar = new HashMap<Integer, String>();
        this.mapID = mapID;
        queryData(this.data, this.typeChar);
        querySand(this.sands);
    }

    private static void queryData(ArrayList<Integer[]> data, HashMap<Integer, String> typeChar) {
        /*********************************
         * The Past TODO (Checkpoint2)********************************
         * 
         * TODO(Past) Querying the barrier location from the server, and set it into
         * Arraylist.
         * 
         * TODO(Past) Querying the bar_type and the corresponding file_name from the
         * server, and set it into HashMap.
         * 
         * Hint: for ckp2 to after, you need replace querying column "file_name" with
         * querying column "display".
         * 
         ********************************** The End of the TODO
         ***************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        Connection conn = null;

        String url = "jdbc:mysql://140.127.220.220:3306/CHECKPOINT";
        String user = "checkpoint";
        String password = "ckppwd";
        int HEIGHT = 0;
        int WIDTH = 0;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception err) {
            System.err.println("error ! ");
            err.printStackTrace(System.err);
            System.exit(0);
        }
        try {
            Statement s = conn.createStatement();
            ResultSet result_1 = s.executeQuery("SELECT * FROM map WHERE map_id=" + mapID);

            while (result_1.next()) {
                HEIGHT = result_1.getInt("height");
                WIDTH = result_1.getInt("width");
            }

            ResultSet result_2 = s
                    .executeQuery("SELECT * FROM barrier b,map m WHERE b.map_id=m.map_id AND m.map_id=" + mapID);

            Integer a;
            String b;

            while (result_2.next()) {
                // if(result_2.getInt("height")>=result_2.getInt("row_idx") &&
                // result_2.getInt("width")>=result_2.getInt("column_idx")){
                Integer[] barrier_list = new Integer[3];
                barrier_list[0] = result_2.getInt("row_idx");
                barrier_list[1] = result_2.getInt("column_idx");
                barrier_list[2] = result_2.getInt("bar_type");

                data.add(barrier_list);
                // }
            }

            ResultSet result_3 = s.executeQuery(
                    "SELECT * FROM barrier_type b1,barrier b2,map m WHERE m.map_id=b2.map_id AND b1.bar_type=b2.bar_type AND m.map_id="
                            + mapID);

            while (result_3.next()) {
                a = result_3.getInt("bar_type");
                b = result_3.getString("file_name");
                typeChar.put(a, b);
            }
        }

        catch (Exception err) {
            System.err.println("SQL error ! ");
            err.printStackTrace(System.err);
            System.exit(0);
        }

        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    private static void querySand(ArrayList<Integer[]> sands) {
        /*********************************
         * The TODO This Time (Checkpoint7)***************************
         * 
         * TODO(1) Querying the map size and the sand location from the server, and set
         * it into Arraylist.
         * 
         ********************************** The End of the TODO
         ***************************************/

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        Connection conn = null;

        String url = "jdbc:mysql://140.127.220.220:3306/CHECKPOINT";
        String user = "checkpoint";
        String password = "ckppwd";

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception err) {
            System.err.println("error ! ");
            err.printStackTrace(System.err);
            System.exit(0);
        }
        try {
            Statement s = conn.createStatement();
            ResultSet result_4 = s.executeQuery("SELECT * FROM sand s WHERE s.map_id=" + mapID);

            while (result_4.next()) {
                Integer[] sandlist = new Integer[2];
                sandlist[0] = result_4.getInt("row_idx");
                sandlist[1] = result_4.getInt("column_idx");
                sands.add(sandlist);
            }
        }

        catch (Exception err) {
            System.err.println("SQL error ! ");
            err.printStackTrace(System.err);
            System.exit(0);
        }
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    public ArrayList getObstacle() {
        return this.data;
    }

    public void setObstacle(ArrayList<Integer[]> data) {
        this.data = data;
    }

    public String getMapID() {
        return this.mapID;
    }

    public void setMapID(String mapID) {
        this.mapID = mapID;
    }

    public HashMap getObstacleImg() {
        return this.typeChar;
    }

    public void setObstacleImg(HashMap<Integer, String> typeChar) {
        this.typeChar = typeChar;
    }

    public ArrayList getSands() {
        return this.sands;
    }

    public void setSands(ArrayList<Integer[]> sands) {
        this.sands = sands;
    }
}
