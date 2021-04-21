import javax.swing.*;
import java.awt.*;
import Sweeper.Box;
import Sweeper.Coord;

public class Minesweeper extends JFrame {

    private JPanel panel;
    private final int cols = 15;
    private final int rows = 1;
    private final int image_size = 50;

    public static void main(String[] args) {
        new Minesweeper().setVisible(true);
    }

    private Minesweeper () {
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
                for (Box box : Box.values()) {
                    Coord coord = new Coord(box.ordinal() * image_size, 0);
                    g.drawImage((Image) box.image,
                            coord.x, coord.y, this);
                }
            }
        };
        panel.setPreferredSize(new Dimension(cols * image_size,rows * image_size));
        add(panel);
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
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
