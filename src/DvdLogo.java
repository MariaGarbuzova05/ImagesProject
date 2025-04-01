import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DvdLogo extends JPanel {

    private BufferedImage dvdLogo;
    private int x, y;
    private int width, height;
    private int xDirection = 2; // Constant speed in X direction
    private int yDirection = 2; // Constant speed in Y direction
    private Color logoColor = Color.BLUE;
    private Color backgroundColor = Color.BLACK;

    public DvdLogo() {
        setBackground(backgroundColor);
        loadLogo();

        // Initial random position
        x = (int) (Math.random() * (getWidth() - width));
        y = (int) (Math.random() * (getHeight() - height));

        Timer timer = new Timer(5, e -> {
            moveLogo();
            repaint();
        });
        timer.start();
    }

    private void loadLogo() {
        try {
            File logoFile = new File("dvd_logo.png");
            BufferedImage originalLogo = ImageIO.read(logoFile);

            // Scale the logo
            width = 80;
            height = 60;

            dvdLogo = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = dvdLogo.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(originalLogo, 0, 0, width, height, null);
            g.dispose();

        } catch (IOException e) {
            System.err.println("Не удалось загрузить логотип: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Ошибка загрузки логотипа: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void moveLogo() {
        x += xDirection;
        y += yDirection;

        // Collision detection and direction change
        if (x + width > getWidth() || x < 0) {
            xDirection *= -1;
            changeBackgroundColor();
            changeLogoColor(); // change logo color as well.
        }
        if (y + height > getHeight() || y < 0) {
            yDirection *= -1;
            changeBackgroundColor();
            changeLogoColor(); // change logo color as well
        }

        // Keep logo within the bounds (prevents getting stuck at edge)
        x = Math.max(0, Math.min(x, getWidth() - width));
        y = Math.max(0, Math.min(y, getHeight() - height));
    }

    private void changeLogoColor() {
        float hue = (float) Math.random();
        logoColor = Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    private void changeBackgroundColor() {
        float hue = (float) Math.random();
        backgroundColor = Color.getHSBColor(hue, 1.0f, 0.8f);
        setBackground(backgroundColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dvdLogo != null) {
            g.setColor(logoColor);
            g.drawImage(dvdLogo, x, y, this);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DVD Logo Bouncing");
        DvdLogo dvdLogoPanel = new DvdLogo();
        frame.add(dvdLogoPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}