import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class CarRace {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Race");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(new CarPanel());
        frame.setVisible(true);
    }
}