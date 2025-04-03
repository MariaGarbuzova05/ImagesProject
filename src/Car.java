import java.awt.Image;

public class Car {
    private int x;
    private int y;
    private Image image;
    private int width;  // Добавлено: ширина машинки
    private int height; // Добавлено: высота машинки

    public Car(int x, int y, Image image, int width, int height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void move(int speed) {
        this.x += speed;
    }
}