import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DvdBouncer extends JPanel implements ActionListener {
    private BufferedImage logo;
    private int x = 100, y = 100;
    private int dx = 2, dy = 2;
    private Timer timer;
    private Color tint;

    public DvdBouncer() {
        try {
            logo = ImageIO.read(new File("dvd.png")); // Добавьте ваш логотип тут
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить изображение dvd.png");
            System.exit(1);
        }

        setBackground(Color.BLACK);
        dx = Math.random() > 0.5 ? 2 : -2;
        dy = Math.random() > 0.5 ? 2 : -2;
        tint = randomColor();

        timer = new Timer(10, this);
        timer.start();
    }

    private Color randomColor() {
        float r = (float)Math.random();
        float g = (float)Math.random();
        float b = (float)Math.random();
        return new Color(r, g, b);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(logo, x, y, logo.getWidth(), logo.getHeight(), tint, null);
        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        x += dx;
        y += dy;

        if (x <= 0 || x + logo.getWidth() >= panelWidth) {
            dx *= -1;
            tint = randomColor();
        }
        if (y <= 0 || y + logo.getHeight() >= panelHeight) {
            dy *= -1;
            tint = randomColor();
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DVD Screensaver");
        DvdBouncer panel = new DvdBouncer();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
    }
}