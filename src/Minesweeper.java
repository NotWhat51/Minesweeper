import javax.swing.*;
import java.awt.*;
import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;

public class Minesweeper extends JFrame {

    private Game game;
    private JPanel panel;
    private final int cols = 9;
    private final int rows = 9;
    private final int image_size = 50;

    public static void main(String[] args) {
        new Minesweeper().setVisible(true);
    }

    private Minesweeper () {
        game = new Game (cols, rows);
        game.start();
        setImages();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent (Graphics g){
                super.paintComponents(g);
                for (Coord coord: Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * image_size, coord.y * image_size, this);
                }
            }
        };
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * image_size, Ranges.getSize().y * image_size));
        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
    }

    private void setImages () {
        for (Box box: Box.values())
            box.image = getImage(box.name());
    }

    private Image getImage (String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
