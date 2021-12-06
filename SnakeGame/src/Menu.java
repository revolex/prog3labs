import javax.swing.*;
import java.awt.*;



public class Menu extends JPanel {
    /**
     * Menü gömbök kerete.
     */
    public Rectangle playB,loadB,exitB,lboardB;

    /**
     * Beállítjuk a menühöz szükséges gombok, helyét, méretét és a menü dimenzióit.
     */
    Menu() {
        playB=new Rectangle(Board.w/2-60,100,110,55);
        lboardB=new Rectangle(Board.w/2-150,200,300,55);
        loadB=new Rectangle(Board.w/2-60,300,110,55);
        exitB=new Rectangle(Board.w/2-60,400,110,55);
        this.setPreferredSize(new Dimension(Board.w,Board.h));
        this.setBackground((Color.black));
        this.setFocusable(true);

    }

    /**
     * Kirajzoljuk a menüt a képernyőre, a SNAKE feliratot és a 4 gombot,
     * azaz a Play, Leaderboard, Load, Exit gombokat és a játékos begépelt nevét.
     * @param g graphics
     * @param name Begépelt játékos neve
     */
    public void draw(Graphics g, String name){
        Graphics2D g2d = (Graphics2D)g;

        //Snake felirat
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metric1 = getFontMetrics(g.getFont());
        g.drawString("SNAKE",(Board.w- metric1.stringWidth("SNAKE"))/2,75);

        //Gombok feliratának beállítása
        g.setFont(new Font("Ink Free",Font.BOLD,50));
        g.drawString("Play",playB.x+10, playB.y+45);
        g.drawString("Load",loadB.x, loadB.y+45);
        g.drawString("Exit",exitB.x+10, exitB.y+45);
        g.drawString("Leaderboard",lboardB.x+10, lboardB.y+45);
        //Név és kapott név kirajzolása
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metric2 = getFontMetrics(g.getFont());
        g.drawString("Name:",Board.w/2-metric2.stringWidth("Name: ")/2,Board.h-100);
        g.drawString(name,Board.w/2-metric2.stringWidth(name)/2,Board.h-50);
        //Gombok keretének kirajzolása
        g2d.draw(playB);
        g2d.draw(lboardB);
        g2d.draw(loadB);
        g2d.draw(exitB);
    }
}
