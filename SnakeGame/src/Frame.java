import javax.swing.JFrame;

public class Frame extends JFrame {
    Board board;

    /**
     * Játék frame-je
     */
    Frame(){
        board = new Board();
        this.add(board);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}
