import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

import static java.lang.System.exit;


public class Board extends JPanel implements ActionListener {
    public static int w=1000;
    public static int h=1000;
    static int unitsize=25;
    static int sleep = 75;
    Menu menu;
    boolean start = false;
    Snake s;
    Fruit[] f;
    int score;
    Timer timer;
    Random rnd;
    public enum STATE{GAME,MENU,OVER,LOAD};
    public static STATE state;

    Board(){
        state=STATE.MENU;
        menu = new Menu();
        rnd = new Random();
        this.setPreferredSize(new Dimension(w,h));
        this.setBackground((Color.black));
        this.setFocusable(true);
        this.addKeyListener(new Adapter());
        this.addMouseListener(new MouseInput());
        timer = new Timer(sleep,this);
        timer.start();
    }

    public void startGame() {
        s=new Snake(w/2,h/2);
        f=new Fruit[15];
        initFruits();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

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
            menu.draw(gra);
        }else if (state == STATE.OVER) {
            gameOver(gra);
        }
    }

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
     * Megnézi, hogy megette-e a kígyó a gyümölcsöt, ha igen akkor új poziciót állítunk be.
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
            s.checkCollision(w, h);
            if (score < s.getLife())
                score = s.getLife();
            if (s.getLife() == 0) {
                state = STATE.OVER;
                start = false;
            }
        }
        repaint();

    }
    public void saveGame() throws IOException {
        ObjectOutputStream snakeOut = new ObjectOutputStream(new FileOutputStream("snakeSave"));
        ObjectOutputStream fruitsOut = new ObjectOutputStream(new FileOutputStream("fruitSave"));
        snakeOut.writeObject(s);
        fruitsOut.writeObject(f);
        snakeOut.close();
        fruitsOut.close();
    }

    public void loadGame() throws IOException, ClassNotFoundException {
        ObjectInputStream snakeIn = new ObjectInputStream(new FileInputStream("snakeSave"));
        ObjectInputStream fruitsIn = new ObjectInputStream(new FileInputStream("fruitSave"));
        s = (Snake)snakeIn.readObject();
        f = (Fruit[])fruitsIn.readObject();
        snakeIn.close();
        fruitsIn.close();
    }

    private class Adapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke){
            switch (ke.getKeyCode()) {
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
                case KeyEvent.VK_ESCAPE:
                         try {
                             saveGame();
                             state=STATE.MENU;
                             start=false;
                         } catch (IOException e) {
                             e.printStackTrace();
                             exit(2);
                         }
                         break;
                case KeyEvent.VK_ENTER:
                     state=STATE.MENU;
                     start = false;
                     break;
            }
        }
    }
}
