import java.io.Serializable;

public class Point implements Serializable {
    private int x,y;
    Point(int sx, int sy){
        this.x=sx;
        this.y=sy;
    }
    public Point getPoint(){
        return this;
    }
    public int getX(){
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}