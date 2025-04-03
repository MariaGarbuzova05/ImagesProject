import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarPanel extends JPanel implements ActionListener {

    private List<Car> cars;
    private boolean raceStarted = false;
    private boolean raceFinished = false;
    private int winner = -1;
    private Random random = new Random();
    private Timer timer;
    private int carWidth = 140;   // Установите желаемую ширину машинки
    private int carHeight = 50;  // Установите желаемую высоту машинки
    private int finishLineX; // X-координата финишной прямой

    public CarPanel() {
        cars = new ArrayList<>();
        try {
            // Замените URL-адреса своими ссылками на изображения машинок
            cars.add(new Car(50, 50, ImageIO.read(new java.net.URL("https://i.pinimg.com/originals/d6/4e/5c/d64e5caeb3473cda4af50ae3edea8168.png")), carWidth, carHeight));
            cars.add(new Car(50, 150, ImageIO.read(new java.net.URL("https://upload.wikimedia.org/wikipedia/commons/b/b2/Dodge.png")), carWidth, carHeight));
            cars.add(new Car(50, 250, ImageIO.read(new java.net.URL("https://img.freepik.com/free-psd/sleek-silver-mini-cooper-side-profile-view_632498-46365.jpg?t=st=1743660855~exp=1743664455~hmac=0243176c465b14ed49879e331f6f3dade309d4acc581a8d8dc5848741c5da0ee&w=740")), carWidth, carHeight));
            cars.add(new Car(50, 350, ImageIO.read(new java.net.URL("https://www.needpix.com/file_download.php?url=https://storage.needpix.com/rsynced_images/car-29078_1280.png")), carWidth, carHeight));
            cars.add(new Car(50, 450, ImageIO.read(new java.net.URL("https://www.thesupercars.org/wp-content/uploads/2013/08/2013-Hamann-McLaren-MP4-12c-memoR-studio-1.jpg")), carWidth, carHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(20, this); // Частота обновления (примерно 50 кадров в секунду)
        timer.start();

        // Запуск гонки через 3 секунды
        Timer startTimer = new Timer(3000, e -> raceStarted = true);
        startTimer.setRepeats(false);
        startTimer.start();

        // Устанавливаем положение финишной прямой (ближе к правому краю)
        finishLineX = getWidth() - 100; // Или другое значение, какое вам нужно.  Будет пересчитано при первом вызове paintComponent()
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Пересчитываем положение финишной прямой, чтобы учитывать изменение размеров окна
        finishLineX = getWidth() - 100;


        // Рисуем финишную прямую
        g.setColor(Color.RED);
        g.fillRect(finishLineX, 0, 5, getHeight()); // Вертикальная линия

        for (Car car : cars) {
            g.drawImage(car.getImage(), car.getX(), car.getY(), car.getWidth(), car.getHeight(), null); // Используем ширину и высоту
        }

        if (raceFinished) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String winnerText = "Car #" + (winner + 1) + " wins!";
            int stringWidth = g.getFontMetrics().stringWidth(winnerText);
            g.drawString(winnerText, (getWidth() - stringWidth) / 2, getHeight() / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (raceStarted && !raceFinished) {
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                int speed = random.nextInt(5) + 1; // Случайная скорость от 1 до 5
                car.move(speed);

                // Проверка финиша
                if (car.getX() + car.getWidth() > finishLineX) { // Учитываем ширину машинки
                    raceFinished = true;
                    winner = i;
                    timer.stop();
                    repaint();
                    break; // Останавливаем цикл, как только кто-то финишировал
                }
            }
            repaint();
        }
    }
}