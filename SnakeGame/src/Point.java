import java.io.Serializable;

public class Point implements Serializable {
    private int x,y;

    /**
     * X és Y értékeket megadjuk a konstruktorban kapott értékekkel
     * @param sx kapott x start pozicíó
     * @param sy kapott y start pozicíó
     */
    Point(int sx, int sy){
        this.x=sx;
        this.y=sy;
    }

    /**
     * Visszaadja x-et
     * @return int x
     */
    public int getX(){
        return x;
    }

    /**
     * Visszaadja y-t
     * @return int y
     */
    public int getY() {
        return y;
    }

    /**
     * x settere
     * @param x beállítandó x érték
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * y settere
     * @param y beállítandó y érték
     */
    public void setY(int y) {
        this.y = y;
    }
}