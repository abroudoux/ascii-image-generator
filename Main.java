import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    private final int unit = 2;
    private BufferedImage img;
    private StringBuilder asciiArt = new StringBuilder();
    private static final String ASCII_CHARS = "@#%*+=-:. ";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the path to the image.");
            return;
        }

        Main instance = new Main();
        instance.loadImage(args[0]);
        instance.convertToAscii();
    }

    public void loadImage(String imagePath) {
        try {
            this.img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void convertToAscii() {
        if (this.img == null) {
            System.out.println("Image not loaded.");
            return;
        }

        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();

        int newWidth = originalWidth / this.unit;
        int newHeigth = originalHeight / this.unit;

        for (int y = 0; y < newHeigth; y++) {
            for (int x = 0; x < newWidth; x++) {
                int pixelColor = img.getRGB(x * this.unit, y * this.unit);
                int red = (pixelColor >> 16) & 0xff;
                int green = (pixelColor >> 8) & 0xff;
                int blue = pixelColor & 0xff;

                int brightness = (red + green + blue) / 3;
                char asciiChar = ASCII_CHARS.charAt((brightness * (ASCII_CHARS.length() - 1)) / 255);
                this.asciiArt.append(asciiChar);
            }
            this.asciiArt.append("\n");
        }

        this.printAsciiArt();
    }

    public void printAsciiArt() {
        System.out.println(this.asciiArt.toString());
    }
}