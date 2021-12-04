import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.System.exit;

public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
            /*playB=new Rectangle(Board.w/2-60,200,110,55);
            loadB=new Rectangle(Board.w/2-60,300,110,55);
            exitB=new Rectangle(Board.w/2-60,400,110,55);*/
        if(mx>=Board.w/2-60 && mx<=Board.w/2+50){
            if(my>=200&&my<=255) {
                Board.state = Board.STATE.GAME;
            }
            if(my>=300&&my<=355)
                Board.state = Board.STATE.LOAD;
            if(my>=400&&my<=455)
                exit(1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}