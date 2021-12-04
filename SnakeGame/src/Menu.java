import javax.swing.*;

import java.awt.*;

import java.awt.event.*;



public class Menu extends JPanel implements ActionListener
{
    public Rectangle playB,loadB,exitB;

    Menu() {
        playB=new Rectangle(Board.w/2-60,200,110,55);
        loadB=new Rectangle(Board.w/2-60,300,110,55);
        exitB=new Rectangle(Board.w/2-60,400,110,55);
        this.setPreferredSize(new Dimension(Board.w,Board.h));
        this.setBackground((Color.black));
        this.setFocusable(true);

    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metric1 = getFontMetrics(g.getFont());
        g.drawString("SNAKE",(Board.w- metric1.stringWidth("SNAKE"))/2,75);

        g.setFont(new Font("Ink Free",Font.BOLD,50));
        g.drawString("Play",playB.x+10, playB.y+45);
        g.drawString("Load",loadB.x, loadB.y+45);
        g.drawString("Exit",exitB.x+10, exitB.y+45);
        g2d.draw(playB);
        g2d.draw(loadB);
        g2d.draw(exitB);
    }

    public void actionPerformed(ActionEvent e)
    {
    }

}
