import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

import static java.lang.System.exit;


public class Board extends JPanel implements ActionListener {
    /**
     * Tábla szélessége
     */
    public static int w=600;
    /**
     * Tábla magassága
     */
    public static int h=600;
    /**
     * Tábla egysége
     */
    public static int unitsize=20;
    /**
     * Timer tickelése
     */
    static int sleep = 75;
    Menu menu;
    /**
     * Dicsőségfal
     */
    Leaderboard lboard;
    /**
     * Játékos neve
     */
    public String playerName="";
    /**
     * Azt jelzi, hogy el van-e indítva a játék.
     */
    boolean start = false;
    Snake s;
    Fruit[] f;
    int score;
    Timer timer;
    Random rnd;

    /**
     * A játék állapotai, hiszen eseményvezérelt a játék.
     */
    public enum STATE{GAME,MENU,OVER,LOAD,LBOARD};
    public static STATE state;

    /**
     * Konstruktor, minden szükséges dolgot beállítunk a játékfelület, és maga  játék működéséhez.
     * Leaderboardot betöltjük fájlból.
     */
    Board(){
        state=STATE.MENU;
        menu = new Menu();
        try{
            loadLeaderboard();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        rnd = new Random();
        this.setPreferredSize(new Dimension(w,h));
        this.setBackground((Color.black));
        this.setFocusable(true);
        this.addKeyListener(new Adapter());
        this.addMouseListener(new MouseInput());
        timer = new Timer(sleep,this);
        timer.start();
    }

    /**
     * Kígyó és gyümölcsök inicializálása
     */
    public void startGame() {
        s=new Snake(w/2,h/2);
        f=new Fruit[15];
        initFruits();
    }

    /**
     * Visszaadja a gyümölcseinket
     * @return f
     */
    public Fruit[] getFruits(){
        return f;
    }

    /**
     * Visszaadja a kígyót
     * @return s
     */
    public Snake getSnake(){
        return s;
    }

    /**
     * Visszaadja a dicsőségfalat
     * @return lboard
     */
    public Leaderboard getLboard(){
        return lboard;
    }

    /**
     * Minden repaint()-kor meghívódik, ez indítja az újabb kör rajzolást
     * @param g graphics
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Itt történnek a rajzolások minden állapotra leosztva,
     * azaz GAME,MENU,OVER,LBOARD állapotokra, ha valamelyik állapotban vagyunk,
     * akkor kirajzoljuk azon állapothoz tartozó képernyőt.
     * OVER állapot esetén, meghívjuk a leaderBoardot,
     * és beadjuk neki a legútóbbi játék pontszámát, majd elmentjük a leaderboardot.
     * @param gra rajzoláshoz graphics
     */
    public void draw(Graphics gra){
        if(state==STATE.GAME) {
            for (Fruit fruit : f) {
                gra.setColor(fruit.getColor());
                gra.fillOval(fruit.getX(), fruit.getY(), unitsize, unitsize);
            }

            for (int i = 0; i < s.getLen(); i++) {
                if (i == 0)
                    gra.setColor(Color.green);
                else
                    gra.setColor(new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
                Point p = s.getBodyPart(i);
                gra.fillRect(p.getX(), p.getY(), unitsize, unitsize);
            }
            gra.setColor(Color.red);
            gra.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metric1 = getFontMetrics(gra.getFont());
            gra.drawString("Score: "+s.getLen(),(w- metric1.stringWidth("Score: "+s.getLen()))/2,gra.getFont().getSize());

        }else if(state==STATE.MENU){
            menu.draw(gra,playerName);
        }else if (state == STATE.OVER) {
            lboard.newScore(playerName,s.getLen());
            try{
                saveLeaderboard();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                exit(4);
            }
            gameOver(gra);
        }else if(state==STATE.LBOARD){
            try{
                loadLeaderboard();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                exit(5);
            }
            lboard.draw(gra);
        }
    }

    /**
     * Ha meghalt a kígyó behozzuk a játék vége képernyőt
     * @param g graphics
     */
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metric1 = getFontMetrics(g.getFont());
        g.drawString("Game Over",(w- metric1.stringWidth("Game Over"))/2,h/2);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metric2 = getFontMetrics(g.getFont());
        g.drawString("Score: "+s.getLen(),(w- metric2.stringWidth("Score: "+s.getLen()))/2,h/2+75);
    }

    /**
     * Pozicíókat adunk a gyümölcsöknek, első kettő az alma és a dinnye,
     * a többi pedig a halálos körte.
     */
    public void initFruits(){
        int randX,randY;
        do {
            randX = rnd.nextInt(w / unitsize) * unitsize;
            randY = rnd.nextInt(h / unitsize) * unitsize;
            f[0] = new Fruit.Apple(randX, randY);
        } while (s.spawnDeny(f[0]));
        do {
            randX = rnd.nextInt(w / unitsize) * unitsize;
            randY = rnd.nextInt(h / unitsize) * unitsize;
            f[1] = new Fruit.Melon(randX, randY);
        } while (s.spawnDeny(f[1]));
        for(int i = 2;i<f.length;i++){
            do {
                randX = rnd.nextInt(w / unitsize) * unitsize;
                randY = rnd.nextInt(h / unitsize) * unitsize;
                f[i] = new Fruit.Pear(randX, randY);
            } while (s.spawnDeny(f[i]));
        }
    }

    /**
     * Megnézi, hogy megette-e a kígyó a gyümölcsöt, ha igen akkor új poziciót állítunk be a gyümölcsnek
     */
    public void checkFruit(){
        for (Fruit fruit: f){
            int eaten = s.eatFruit(fruit);
            if(eaten==1){
                do {
                    f[0].setX(rnd.nextInt(w / unitsize) * unitsize);
                    f[0].setY(rnd.nextInt(h / unitsize) * unitsize);
                } while (s.spawnDeny(f[0]));
            }else if(eaten==2){
                do {
                        f[1].setX(rnd.nextInt(w / unitsize) * unitsize);
                        f[1].setY(rnd.nextInt(h / unitsize) * unitsize);
                } while (s.spawnDeny(f[1]));
            }
        }
    }


    /**
     * Ha GAME állapotban vagyunk és a start false,
     * akkor inicializáljuk a kígyót és a gyümölcsöket és a start-ot truera állítjuk.
     * Ha LOAD állapotban vagyunk és a start false,
     * akkor betöltjük mentésből a kígyót és a gyümölcsöket és a start-ot truera állítjuk.
     * Ha GAME állapotban vagyunk és start true, akkor futtatjuk a játékot, újra és újra rajzoljuk a területet
     * Minden esetben repaintet futtatunk, a timer meghívja ezt a függvényt sleep időnként.
     * @param e nem használjuk
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == STATE.GAME && !start) {
            start = true;
            startGame();
        }
        if(state==STATE.LOAD&&!start){
            start=true;
            try {
                loadGame();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                exit(3);
            }
            state=STATE.GAME;
        }
        if (state == STATE.GAME && start) {
            s.move(unitsize);
            checkFruit();
            s.checkCollision();
            if (score < s.getLen())
                score = s.getLen();
            if (s.getLife() == 0) {
                state = STATE.OVER;
                start = false;
            }
        }
        repaint();

    }

    /**
     * Fájlba mentjük a játékot, azaz a kígyót és a gyümölcsöket
     * @throws IOException ha nincs fájl, vagy nem sikerül streamet csinálni
     */
    public void saveGame() throws IOException {
        ObjectOutputStream snakeOut = new ObjectOutputStream(new FileOutputStream("snakeSave"));
        ObjectOutputStream fruitsOut = new ObjectOutputStream(new FileOutputStream("fruitSave"));
        snakeOut.writeObject(s);
        fruitsOut.writeObject(f);
        snakeOut.close();
        fruitsOut.close();
    }

    /**
     * Betöltjük a mentésben lévő állapotot, azaz a kígyót és gyümölcsöket
     * @throws IOException ha nincs fájl, vagy nem sikerül streamet csinálni
     * @throws ClassNotFoundException ha nem deszeriazálható class van
     */
    public void loadGame() throws IOException, ClassNotFoundException {
        ObjectInputStream snakeIn = new ObjectInputStream(new FileInputStream("snakeSave"));
        ObjectInputStream fruitsIn = new ObjectInputStream(new FileInputStream("fruitSave"));
        s = (Snake)snakeIn.readObject();
        f = (Fruit[])fruitsIn.readObject();
        snakeIn.close();
        fruitsIn.close();
    }

    /**
     * Kimentjük a dicsőségfal állapotát fájlba
     * @throws IOException ha nincs fájl, vagy nem sikerül streamet csinálni
     * @throws ClassNotFoundException ha nem deszeriazálható class van
     */
    public void saveLeaderboard() throws IOException, ClassNotFoundException{
        ObjectOutputStream lboardOut = new ObjectOutputStream(new FileOutputStream("lboardSave"));
        lboardOut.writeObject(lboard);
        lboardOut.close();
    }

    /**
     * Betölti a dicsőségfalat fájlból
     * @throws IOException ha nincs fájl, vagy nem sikerül streamet csinálni
     * @throws ClassNotFoundException ha nem deszeriazálható class van
     */
    public void loadLeaderboard() throws IOException, ClassNotFoundException {
        ObjectInputStream lboardIn = new ObjectInputStream(new FileInputStream("lboardSave"));
        lboard = (Leaderboard) lboardIn.readObject();
        lboardIn.close();
    }

    /**
     * A gomblenyomásokat figyeli az alkalmazásban,
     * GAME állapotban a nyilakat a kígyó mozgásához
     * S betűt a mentéshez
     * GAME és OVER állapotban Escapet a menübe való kilépéshez
     * MENU állapotban a névhez hozzáadja, az összes lenyomott gombot
     */
    public class Adapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke){
            char kbutton= (char) ke.getKeyCode();
            if(state==STATE.MENU) {
                if(kbutton==KeyEvent.VK_BACK_SPACE)
                    playerName="";
                else
                    playerName += kbutton;
            }
            switch (kbutton) {
                case KeyEvent.VK_UP:
                    s.changeDirection(direction.U);
                    break;
                case KeyEvent.VK_DOWN:
                    s.changeDirection(direction.D);
                    break;
                case KeyEvent.VK_LEFT:
                    s.changeDirection(direction.L);
                    break;
                case KeyEvent.VK_RIGHT:
                    s.changeDirection(direction.R);
                    break;
                case KeyEvent.VK_S:
                    if(state==STATE.GAME) {
                        try {
                            saveGame();
                            state = STATE.MENU;
                            start = false;
                        } catch (IOException e) {
                            e.printStackTrace();
                            exit(2);
                        }
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if(state==STATE.GAME||state==STATE.OVER){
                        state=STATE.MENU;
                        start = false;
                    }
                    break;
            }
        }
    }
}
