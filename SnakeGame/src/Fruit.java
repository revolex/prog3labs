import java.awt.*;
import java.io.Serializable;

public class Fruit extends Point implements Serializable {
    Color c;
    int growthValue;
    Fruit(int sx, int sy) {
        super(sx, sy);
    }
    public Color getColor(){
        return c;
    }
    public int getGrowthValue(){
        return growthValue;
    }


    public static class Apple extends Fruit{
        int growthValue;
        Apple(int sx, int sy) {
            super(sx, sy);
            c=Color.red;
            growthValue=1;
        }
        public int getGrowthValue(){
            return growthValue;
        }
    }
    public static class Pear extends Fruit{
        Pear(int sx, int sy) {
            super(sx, sy);
            c=Color.yellow;
        }
    }
    public static class Melon extends Fruit{
        int growthValue;
        Melon(int sx, int sy){
            super(sx, sy);
            growthValue=2;
            c=Color.green;
        }
        public int getGrowthValue(){
            return growthValue;
        }
    }
}
