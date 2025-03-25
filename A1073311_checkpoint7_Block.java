import java.awt.Image;
public class A1073311_checkpoint7_Block{
        private int X;
        private int Y;
        private String type;
        private Image img;
        private int cost;
        public A1073311_checkpoint7_Block(int X, int Y, String type, Image img, int cost){
            this.X = X;
            this.Y = Y;
            this.type = type;
            this.img = img;
            this.cost = cost;
        }
        public int getX(){
            return this.X;
        }
        public void setX(int X){
            this.X = X;
        }
        public int getY(){
            return this.Y;
        }
        public void setY(int Y){
            this.Y = Y;
        }
        public String getType(){
            return this.type;
        }
        public void setType(String type){
            this.type = type;
        }
        public Image getImg(){
            return this.img;
        }
        public void setImg(Image img){
            this.img = img;
        }
        public int getCost(){
            return this.cost;
        }
        public void setCost(int cost){
            this.cost = cost;
        }
}
