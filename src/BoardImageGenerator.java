import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BoardImageGenerator {
    private static final int CELL_SIZE = 50; 

    public static void generateImage(char[][] board, String fileName) {
        int rows = board.length;
        int cols = board[0].length;
        int width = cols * CELL_SIZE;
        int height = rows * CELL_SIZE;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        Map<Character, Color> colorMap = generateColorMap();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = board[i][j];
                Color color = colorMap.getOrDefault(c, Color.WHITE); 

                g2d.setColor(color);
                g2d.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                g2d.setColor(Color.BLACK);
                g2d.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        g2d.dispose(); 

        try {
            ImageIO.write(image, "png", new File(fileName));
            System.out.println("Image saved as: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Character, Color> generateColorMap() {
        Map<Character, Color> colorMap = new HashMap<>();
        Color[] colors = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN,
            Color.MAGENTA, Color.ORANGE, Color.PINK, Color.LIGHT_GRAY, Color.GRAY,
            new Color(128, 0, 128), new Color(255, 165, 0), new Color(0, 128, 128),
            new Color(128, 128, 0), new Color(255, 105, 180), new Color(139, 69, 19),
            new Color(0, 255, 127), new Color(0, 191, 255), new Color(147, 112, 219),
            new Color(255, 222, 173), new Color(0, 255, 255), new Color(255, 20, 147),
            new Color(218, 165, 32), new Color(70, 130, 180), new Color(152, 251, 152),
            new Color(176, 224, 230)
        };

        for (int i = 0; i < 26; i++) {
            colorMap.put((char) ('A' + i), colors[i]);
        }
        return colorMap;
    }
}
