import javax.swing.*;

public class SnakeGame extends JFrame {
    public SnakeGame() {
        super("Snake Game");
        add(new Board());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}