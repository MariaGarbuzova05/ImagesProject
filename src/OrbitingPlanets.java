import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OrbitingPlanets extends JPanel {

    private BufferedImage sunImage;
    private Map<String, BufferedImage> planetImages = new HashMap<>();
    private Map<String, Double> planetAngles = new HashMap<>();
    private Map<String, Double> planetSpeeds = new HashMap<>();
    private Map<String, Double> planetRadii = new HashMap<>();
    private int sunX, sunY;
    private int sunWidth, sunHeight;

    public OrbitingPlanets() {
        setBackground(Color.BLACK);
        loadImages();
        sunX = getWidth() / 2;
        sunY = getHeight() / 2;

        Timer timer = new Timer(20, e -> {
            for (String planet : planetAngles.keySet()) {
                planetAngles.put(planet, planetAngles.get(planet) + planetSpeeds.get(planet));
            }
            repaint();
        });
        timer.start();
    }

    private void loadImages() {
        try {
            URL sunImageUrl = new URL("https://i.pinimg.com/originals/8c/85/37/8c85375c2c298aba67f0ef8572c2602b.png");
            sunImage = ImageIO.read(sunImageUrl);
            double sunScaleFactor = 0.05;
            sunWidth = (int) (sunImage.getWidth() * sunScaleFactor);
            sunHeight = (int) (sunImage.getHeight() * sunScaleFactor);
            sunImage = scaleImage(sunImage, sunScaleFactor);

            // Загрузка изображений планет и установка параметров
            String[] planetNames = {"Earth", "Mars", "Venus", "Jupiter"};
            String[] planetURL = {"https://i.pinimg.com/originals/07/8a/b5/078ab50f97a4a8031c9a11b17324f22c.png",
                    "https://293bdf67-vt-journal.s3.timeweb.com/7_5c0794989d.png",
                    "https://userscontent2.emaze.com/images/8be2e7dc-dbdb-4b47-b987-a49f187d2594/49af445220e9ce4cb3e291b03bd11c7b.png",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Jupiter_%28transparent%29.png/640px-Jupiter_%28transparent%29.png"};
            double[] planetRadiiArray = {100, 175, 50, 250}; // Радиусы орбит
            double[] planetSpeedsArray = {0.01, 0.007, 0.008, 0.005};
            double[] planetScaleFactor = {0.02, 0.04, 0.04, 0.05};// Скорости вращения

            for (int i = 0; i < planetNames.length; i++) {
                String planetName = planetNames[i];
                URL planetImageUrl = new URL(planetURL[i]); // Замените URL на свои
                BufferedImage planetImage = ImageIO.read(planetImageUrl);
                planetImages.put(planetName, scaleImage(planetImage, planetScaleFactor[i]));
                planetAngles.put(planetName, 0.0);
                planetSpeeds.put(planetName, planetSpeedsArray[i]);
                planetRadii.put(planetName, planetRadiiArray[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private BufferedImage scaleImage(BufferedImage image, double scaleFactor) {
        int width = (int) (image.getWidth() * scaleFactor);
        int height = (int) (image.getHeight() * scaleFactor);
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.scale(scaleFactor, scaleFactor);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return scaledImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        sunX = getWidth() / 2 - sunWidth / 2;
        sunY = getHeight() / 2 - sunHeight / 2;

        g.drawImage(sunImage, sunX, sunY, this);

        for (String planet : planetImages.keySet()) {
            BufferedImage planetImage = planetImages.get(planet);
            double angle = planetAngles.get(planet);
            double radius = planetRadii.get(planet);
            int planetX = (int) (sunX + sunWidth / 2 + radius * Math.cos(angle)) - planetImage.getWidth() / 2;
            int planetY = (int) (sunY + sunHeight / 2 + radius * Math.sin(angle)) - planetImage.getHeight() / 2;
            g.drawImage(planetImage, planetX, planetY, this);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Orbiting Planets");
        OrbitingPlanets orbitingPlanets = new OrbitingPlanets();
        frame.add(orbitingPlanets);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
