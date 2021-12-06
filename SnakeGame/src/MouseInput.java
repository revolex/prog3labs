import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.System.exit;

public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * A gombokat különböző állapotokban egérrel lehet lenyomni,
     * ezeket az egér kattintásokat itt figyeljük
     * @param e egér kattintás
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(Board.state == Board.STATE.MENU) {
            //Kisebbik menü gombok x-beli pozicíója
            if (mx >= Board.w / 2 - 60 && mx <= Board.w / 2 + 50) {
                //Play gomb
                if (my >= 100 && my <= 155)
                    Board.state = Board.STATE.GAME;
                //Load gomb
                if (my >= 300 && my <= 355)
                    Board.state = Board.STATE.LOAD;
                //Exit gomb
                if (my >= 400 && my <= 455)
                    exit(1);
            }
            //Szélesebb Leaderboard gomb x-beli szélessége
            if (mx >= Board.w / 2 - 150 && mx <= Board.w / 2 + 150)
                //Leaderboard gomb pontos helye
                if (my >= 200 && my <= 255)
                    Board.state = Board.STATE.LBOARD;
        } else if(Board.state == Board.STATE.LBOARD)
            //Leaderboardnál a return gomb helye
            if (mx >= Board.w / 2 -150 && mx <= Board.w / 2 + 150)
                if (my >= Board.h-50 && my <= Board.h-10)
                    Board.state = Board.STATE.MENU;
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