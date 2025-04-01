import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MovingImage2 extends JPanel {

    private BufferedImage image;
    private double x = 200; // Начальная координата X
    private double y = 200; // Начальная координата Y
    private int imageWidth;
    private int imageHeight;
    private double scaleFactor = 0.5;
    private int sideLength = 200; // Длина стороны квадрата
    private enum Direction { RIGHT, DOWN, LEFT, UP }
    private Direction currentDirection = Direction.RIGHT;
    private double speed = 2.0;

    public MovingImage2() {
        setBackground(Color.WHITE);

        try {
            URL imageUrl = new URL("https://i.pinimg.com/736x/ba/9b/ca/ba9bcabd87b9750065ee89de32b687a1.jpg");
            BufferedImage originalImage = ImageIO.read(imageUrl);

            if (originalImage != null) {
                imageWidth = (int) (originalImage.getWidth() * scaleFactor);
                imageHeight = (int) (originalImage.getHeight() * scaleFactor);
                image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = image.createGraphics();
                g.scale(scaleFactor, scaleFactor);
                g.drawImage(originalImage, 0, 0, null);
                g.dispose();
            } else {
                System.err.println("Не удалось загрузить изображение (image == null)");
                JOptionPane.showMessageDialog(this, "Не удалось загрузить изображение.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
                return;
            }

        } catch (IOException e) {
            System.err.println("Не удалось загрузить изображение: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Ошибка загрузки изображения: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return;
        }


        Timer timer = new Timer(20, e -> {
            moveImage();
            repaint();
        });
        timer.start();
    }

    private void moveImage() {
        switch (currentDirection) {
            case RIGHT:
                x += speed;
                if (x >= 50 + sideLength) {
                    x = 50 + sideLength; // Устанавливаем точное положение
                    currentDirection = Direction.DOWN;
                }
                break;
            case DOWN:
                y += speed;
                if (y >= 50 + sideLength) {
                    y = 50 + sideLength; // Устанавливаем точное положение
                    currentDirection = Direction.LEFT;
                }
                break;
            case LEFT:
                x -= speed;
                if (x <= 50) {
                    x = 50; // Устанавливаем точное положение
                    currentDirection = Direction.UP;
                }
                break;
            case UP:
                y -= speed;
                if (y <= 50) {
                    y = 50; // Устанавливаем точное положение
                    currentDirection = Direction.RIGHT;
                }
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, (int) x, (int) y, this);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Moving Image");
        MovingImage2 movingImage = new MovingImage2();
        frame.add(movingImage);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}