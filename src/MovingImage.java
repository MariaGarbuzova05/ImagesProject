import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MovingImage extends JPanel {

    private BufferedImage image;
    private int x = 0;
    private int y = 0;
    private int imageWidth;
    private int imageHeight;
    private double scaleFactor = 0.5; // Фактор масштабирования (0.5 = уменьшение в 2 раза)

    public MovingImage() {
        setBackground(Color.WHITE);
        try {
            // Замените этой ссылкой на URL вашего изображения.
            URL imageUrl = new URL("https://i.pinimg.com/736x/ba/9b/ca/ba9bcabd87b9750065ee89de32b687a1.jpg");
            BufferedImage originalImage = ImageIO.read(imageUrl);

            if (originalImage != null) {
                // Масштабируем изображение
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

        // Запускаем таймер для анимации
        Timer timer = new Timer(20, e -> {
            x += 2;
            y += 2;

            // Проверяем, вышло ли изображение за границы панели
            if (x > getWidth() || y > getHeight()) {
                x = 0;
                y = 0;
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, x, y, this);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Moving Image");
        MovingImage movingImage = new MovingImage();
        frame.add(movingImage);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}