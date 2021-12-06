import java.awt.*;
import java.io.Serializable;

public class Fruit extends Point implements Serializable {
    /**
     * A gyümölcs színe
     */
    Color c;
    /**
     * A kígyót ennyivel növeszti meg a gyümölcs, ha megeszi a kígyó
     */
    int growthValue;

    /**
     * Pozicíó beállítása őskonstruktorral
     * @param sx x pozicíó
     * @param sy y pozicíó
     */
    Fruit(int sx, int sy) {
        super(sx, sy);
    }

    /**
     * Gyümölcs színének visszaadása
     * @return c szín
     */
    public Color getColor(){
        return c;
    }

    /**
     * Visszaadja, hogy mennyivel nő meg a Snake,
     * ha feltételek szerint ette meg a gyümölcsöt.
     * @return growthValue
     */
    public int getGrowthValue(){
        return growthValue;
    }

    /**
     * Alma gyümölcs osztálya
     */
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

    /**
     * Halálos körte gyümölcs osztálya
     */
    public static class Pear extends Fruit{
        Pear(int sx, int sy) {
            super(sx, sy);
            c=Color.yellow;
        }
    }

    /**
     * Nagyon tápláló dinnye gyümölcs osztálya
     */
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
