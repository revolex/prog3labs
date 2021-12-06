import java.io.*;
import java.util.ArrayList;

/**
 * A kígyó lehetséges irányai, sorra fel-le-balra-jobbra
 */
enum direction{U,D,L,R};

public class Snake implements Serializable {
    /**
     * a kígyó hossza, élete és megevett almáinak száma
     */
    int size,life,applesEaten;
    /**
     * Kígyó teste
     */
    ArrayList<Point> body;
    /**
     * Kígyó iránya
     */
    direction dir;


    /**
     * Beállítjuk a Snake paramétereit, feltöltjük a testét
     * @param sx kezdő pozíció x tengelyen
     * @param sy kezdő pozíció y tengelyen
     */
    Snake(int sx, int sy) {
        size=1;
        applesEaten=0;
        body= new ArrayList<Point>();
        for(int i = 0; i<size;i++)
            body.add(new Point(sx,sy));
        dir= direction.R;
        life=1;

    }


    /**
     * Visszaadjuk a lekért darabkáját a kígyónak
     * @param p Adott része a kígyónak számban
     * @return egy pont, a kígyó egyik testrésze
     */
    public Point getBodyPart(int p){
        return body.get(p);
    }

    /**
     * Megváltoztatja a kígyó irányát, de csak 90°-al
     * @param d az irány, amibe el kell irányítani a kígyót
     */
    public void changeDirection(direction d){
        switch(d){
            case U:
                if(dir!=direction.D)
                    dir=d;
                break;
            case D:
                if(dir!=direction.U)
                    dir=d;
                break;
            case L:
                if(dir!=direction.R)
                    dir=d;
                break;
            case R:
                if(dir!=direction.L)
                    dir=d;
                break;
        }
    }

    /**
     * Megnézi, hogy ne inicializálhassunk Fruit-ot a kígyó testébe.
     * @param f Fruit, amit vizsgálunk
     * @return true,ha benne van false, ha nincs
     */
    public boolean spawnDeny(Fruit f){
        int fx=f.getX();
        int fy=f.getY();
        for (int i = 0;i<size;i++){
            if(fx==body.get(i).getX()||fy==body.get(i).getY())
                return true;
        }
        return false;
    }

    /**
     * Mindegyik testrészt az eggyel utána lévőre mozgat,
     * ezután a fejét is elmozgatja eggyel
     * @param us az egység, amivel el kell tolni a kígyó fejét
     */
    public void move(int us){
        for (int i = size-1;i>0;i--) {
            body.get(i).setX(body.get(i-1).getX());
            body.get(i).setY(body.get(i-1).getY());
        }
        int x= getBodyPart(0).getX();
        int y= getBodyPart(0).getY();
        switch (dir) {
            case U -> getBodyPart(0).setY(y - us);
            case D -> getBodyPart(0).setY(y + us);
            case L -> getBodyPart(0).setX(x - us);
            case R -> getBodyPart(0).setX(x + us);
        }
    }
    /**
     * visszaadja a kígyó hosszát
     * @return size
     */
    public int getLen(){
        return size;
    }
    /**
     * Visszaadja a kígyó életeinek számát
     * @return life
     */
    public int getLife(){
        return life;
    }

    /**
     * Visszaadja a kígyó irányát
     * @return dir
     */
    public direction getDir() { return dir; }

    /**
     * Visszaadja kígyó testét
     * @return body
     */
    public ArrayList<Point> getBody(){return body;}

    /**
     * Beállítja a kígyó hosszát.
     * @param s új érték
     */
    public void setSize(int s){size=s;}

    /**
     * Megnézzük, hogy a kígyó ütközött-e önmagával úgy,
     * hogy megnézzük a fej koordinátái egyenlő-e a testének
     * bármely koordinátájával, ha igen, akkor meghívjuk a loseTail függvényt.
     * Ha nem ütközött magával, akkor megnézzük,
     * hogy a falakkal ütközött-e, ha igen akkor 0-ra csökkentjük az életét
     */
    public void checkCollision(){
        Point head = body.get(0);
        for (int i =1;i<size;i++){
            Point bodyPart = body.get(i);
            if(head.getX()==bodyPart.getX()&&head.getY()==bodyPart.getY()){
                if(loseTail(i))
                    return;
            }
        }
        int x = head.getX();
        int y = head.getY();
        if(x<0||x>Board.w-Board.unitsize||y<0||y>Board.h-Board.unitsize)
            life = 0;
    }

    /**
     * Kitöröljük a listából a kígyó last-tól size-ig terjedő részét.
     * Ezután a méretét átállítjuk az első levágott farok darab-1-re
     * Ha a farok levágódott igazat adunk vissza, hogy ne nézze tovább
     * a kinti ciklus a testet(performance).
     * @param first az első rész amit levágunk a kígyóról.
     * @return
     */
    public boolean loseTail(int first){
        if (size >= first) {
            body.subList(first, size).clear();
            size=first;
            return true;
        }
        return false;
    }

    /**
     * Ez a függvény nézi, meg hogy a Fruiton rajta van-e a kígyó feje,
     * ha igen megnöveljük a méretét a Fruit growthValue-val.
     * @param f Fruit amit vizsgálunk
     * @return 1,ha almát ettünk, 2,ha dinnyét, 3,ha nem volt evés
     */
    public int eatFruit(Fruit f){
        if(f.getX()==body.get(0).getX()&&f.getY()==body.get(0).getY()) {
            if (f instanceof Fruit.Apple) {
                applesEaten+=f.getGrowthValue();
                if (applesEaten % 2 == 0&&applesEaten!=0) {
                    size++;
                    body.add(new Point(150000, 150000));
                }
                return 1;
            } else if (f instanceof Fruit.Melon) {
                size+=f.getGrowthValue();
                body.add(new Point(150000, 150000));
                body.add(new Point(150000, 15000));
                return 2;
            } else {
                life=0;
            }
        }
        return 3;
    }
}
