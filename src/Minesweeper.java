import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;

public class Minesweeper extends JFrame {

    private Game game;
    private JPanel panel;
    private final int cols = 9;
    private final int rows = 9;
    private final int bombs = 10;
    private final int image_size = 50;

    public static void main(String[] args) {
        new Minesweeper().setVisible(true);
    }

    private Minesweeper () {
        game = new Game (cols, rows, bombs);
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
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / image_size;
                int y = e.getY() / image_size;
                Coord coord = new Coord (x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                panel.repaint();
            }
        });
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
