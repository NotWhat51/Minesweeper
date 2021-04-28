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
    private JLabel label;

    private final int cols = 9;
    private final int rows = 9;
    private final int bombs = 10;
    private final int image_wigth = 50;
    private final int image_height = 60;

    public static void main(String[] args) {
        new Minesweeper().setVisible(true);
    }

    private Minesweeper () {
        game = new Game (cols, rows, bombs);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }
    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
    }

    private String getMessage () {
        switch (game.getState()) {
            case PLAYED: return "You are playing now.";
            case BOMBED: return "YOU LOSE!";
            case WINNER: return "Well done, you found all the bombs!";
            default: return "";
        }
    }

    private void initPanel() {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent (Graphics g){
                super.paintComponents(g);
                for (Coord coord: Ranges.getAllCoords()) {
                    int shiftX = 0;
                    if (coord.y % 2 != 0) shiftX = 25;
                    //int shiftY = coord.y * 15;
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * image_wigth + shiftX, coord.y * image_height, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / image_wigth;
                int y = e.getY() / image_height;
                Coord coord = new Coord (x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * image_wigth + 25, Ranges.getSize().y * image_height ));
        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }

    private void setImages () {
        for (Box box: Box.values())
            box.image = getImage(box.name());
    }

    private Image getImage (String name) {
        String filename = "img2/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
