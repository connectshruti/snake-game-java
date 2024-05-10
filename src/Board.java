import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

public class Board extends JPanel implements ActionListener {
    private int DOTS;
    private int ALL_DOTS = 900;
    private int DOT_SIZE = 10;
    private int[] X = new int[ALL_DOTS];
    private int[] Y = new int[ALL_DOTS];
    private Image apple;
    private Image head;
    private Image dot;
    private int appleX;
    private int appleY;
    private int score = 0;
    private boolean gameOver = false;
    private boolean right = true;
    private boolean left = false;
    private boolean down = false;
    private boolean up = false;
    private Timer timer = new Timer(140, this);

    public Board() {
        addKeyListener(new MyKeyListener());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(300, 300));
        loadImages();
        initGame();
        timer.start();
    }

    public void initGame() {
        DOTS = 3;
        for (int i = 0; i < DOTS; i++) {
            Y[i] = 50;
            X[i] = 50 - (i * DOT_SIZE);
        }
        locateApple();

    }

    public void locateApple() {
        appleX = ((int) (Math.random() * 29)) * DOT_SIZE;
        appleY = ((int) (Math.random() * 29)) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        head = i2.getImage();
        dot = i3.getImage();
        apple = i1.getImage();
        System.out.println(head);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void gameoverFunction(Graphics g) {
        String msg = "GAME OVER!!!";
        FontMetrics fontMetrics = g.getFontMetrics();
        // g.setColor(Color.WHITE);
        int x = (getWidth() - fontMetrics.stringWidth(msg)) / 2;
        int y = (getHeight() / 2) + fontMetrics.getAscent() / 2;
        g.drawString(msg, x, y);
    }

    public void draw(Graphics g) {
        if (!gameOver) {
            g.drawImage(apple, appleX, appleY, DOT_SIZE, DOT_SIZE, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, X[i], Y[i], this);
                } else {
                    g.drawImage(dot, X[i], Y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameoverFunction(g);
        }
    }

    public void move() {
        for (int i = DOTS; i > 0; i--) {
            X[i] = X[i - 1];
            Y[i] = Y[i - 1];
        }
        if (right) {
            X[0] += DOT_SIZE;
        }
        if (left) {
            X[0] -= DOT_SIZE;
        }
        if (down) {
            Y[0] += DOT_SIZE;
        }
        if (up) {
            Y[0] -= DOT_SIZE;
        }
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            detectCollision();
            move();
        } else {
            timer.stop();
        }
    }

    public void detectCollision() {
        if (appleX == X[0] && appleY == Y[0]) {
            score++;
            locateApple();
            DOTS = DOTS + 1;
        }
        if (X[0] >= 300 || X[0] < 0 || Y[0] >= 300 || Y[0] < 0) {
            gameOver = true;
        }
        for (int i = 1; i < DOTS; i++) {
            if (X[0] == X[i] && Y[0] == Y[i]) {
                gameOver = true;
            }
        }
    }

    public class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if ((keyCode == KeyEvent.VK_LEFT && !right) || (keyCode == KeyEvent.VK_A && !right)) {
                left = true;
                up = false;
                down = false;
            }
            if ((keyCode == KeyEvent.VK_RIGHT && !left) || (keyCode == KeyEvent.VK_D && !left)) {
                right = true;
                up = false;
                down = false;
            }
            if ((keyCode == KeyEvent.VK_UP && !down) || (keyCode == KeyEvent.VK_W && !down)) {
                up = true;
                right = false;
                left = false;
            }
            if ((keyCode == KeyEvent.VK_DOWN && !up) || (keyCode == KeyEvent.VK_S && !up)) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
