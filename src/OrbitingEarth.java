import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class OrbitingEarth extends JPanel {

    private BufferedImage sunImage;
    private BufferedImage earthImage;
    private int sunX, sunY;
    private double earthAngle = 0;
    private double earthRadius = 300;
    private double earthSpeed = 0.01;
    private int earthWidth, earthHeight;
    private int sunWidth, sunHeight; // Добавляем размеры Солнца

    public OrbitingEarth() {
        setBackground(Color.BLACK);
        loadImages();
        // Размещаем Солнце в центре панели
        sunX = getWidth() / 2;
        sunY = getHeight() / 2;

        Timer timer = new Timer(20, e -> {
            earthAngle += earthSpeed;
            repaint();
        });
        timer.start();
    }

    private void loadImages() {
        try {
            // Замените URL на свои, если эти не подходят по размерам.
            URL sunImageUrl = new URL("https://i.pinimg.com/originals/8c/85/37/8c85375c2c298aba67f0ef8572c2602b.png"); // URL вашего изображения Солнца
            sunImage = ImageIO.read(sunImageUrl);
            URL earthImageUrl = new URL("https://i.pinimg.com/originals/07/8a/b5/078ab50f97a4a8031c9a11b17324f22c.png"); // URL вашего изображения Земли
            earthImage = ImageIO.read(earthImageUrl);


            // Масштабируем Солнце (уменьшаем, если оно слишком большое)
            if (sunImage != null) { // Проверяем, загрузилось ли изображение
                double sunScaleFactor = 0.2;
                sunWidth = (int) (sunImage.getWidth() * sunScaleFactor);
                sunHeight = (int) (sunImage.getHeight() * sunScaleFactor);
                BufferedImage scaledSun = new BufferedImage(sunWidth, sunHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D gSun = scaledSun.createGraphics();
                gSun.scale(sunScaleFactor, sunScaleFactor);
                gSun.drawImage(sunImage, 0, 0, null);
                gSun.dispose();
                sunImage = scaledSun;
            } else {
                System.err.println("Не удалось загрузить изображение Солнца.");
                JOptionPane.showMessageDialog(this, "Не удалось загрузить изображение Солнца.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                // Можно продолжить выполнение, но без Солнца
            }

            // Уменьшаем Землю
            if (earthImage != null) { // Проверяем, загрузилось ли изображение
                double earthScaleFactor = 0.03;
                earthWidth = (int) (earthImage.getWidth() * earthScaleFactor);
                earthHeight = (int) (earthImage.getHeight() * earthScaleFactor);
                BufferedImage scaledEarth = new BufferedImage(earthWidth, earthHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D gEarth = scaledEarth.createGraphics();
                gEarth.scale(earthScaleFactor, earthScaleFactor);
                gEarth.drawImage(earthImage, 0, 0, null);
                gEarth.dispose();
                earthImage = scaledEarth;
            } else {
                System.err.println("Не удалось загрузить изображение Земли.");
                JOptionPane.showMessageDialog(this, "Не удалось загрузить изображение Земли.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                // Можно продолжить выполнение, но без Земли
            }



        } catch (IOException e) {
            System.err.println("Ошибка загрузки изображения: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Ошибка загрузки изображения: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            // Решаем, что делать, если произошла ошибка
            System.exit(1); //  Завершаем программу, т.к. без изображений она не имеет смысла.
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Вычисляем положение Солнца, если размеры панели изменились
        sunX = getWidth() / 2 - (sunImage != null ? sunWidth / 2 : 0); // Используем масштабированные размеры
        sunY = getHeight() / 2 - (sunImage != null ? sunHeight / 2 : 0); // Используем масштабированные размеры

        if (sunImage != null && earthImage != null) {
            g.drawImage(sunImage, sunX, sunY, this);

            // Вычисляем координаты Земли на орбите
            int earthX = (int) (sunX + (sunImage != null ? sunWidth / 2 : 0) + earthRadius * Math.cos(earthAngle)) - earthWidth / 2; // Используем масштабированные размеры
            int earthY = (int) (sunY + (sunImage != null ? sunHeight / 2 : 0) + earthRadius * Math.sin(earthAngle)) - earthHeight / 2; // Используем масштабированные размеры

            g.drawImage(earthImage, earthX, earthY, this);
        } else if (sunImage != null) { // Рисуем только Солнце, если Земля не загрузилась
            g.drawImage(sunImage, sunX, sunY, this);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Orbiting Earth");
        OrbitingEarth orbitingEarth = new OrbitingEarth();
        frame.add(orbitingEarth);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}