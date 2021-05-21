import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Dialog;
import Sweeper.Game;
import Sweeper.Ranges;

public class Minesweeper extends JFrame {

    private final Game game;
    private JPanel panel;
    private JLabel label;

    private final int cols = 10;
    private final int rows = 10;
    private int bombs = 10;
    private final int image_wigth = 50;
    private final int image_height = 60;


    public static void main(String[] args) {

        new Minesweeper().setVisible(true);
    }

    private Minesweeper () {
        game = new Game (cols, rows, bombs);
        game.start();
        setImages();

        initPanel();
        initFrame();
        initLabel();

    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
    }

    private String getMessage () {
        switch (game.getState()) {
            case PLAYED: return "You are playing now.";
            case BOMBED: return "YOU LOSE!";
            case WINNER: return "YOU WIN! You found all the bombs!";
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
                    int offsetX = 0;
                    if (coord.y % 2 != 0) offsetX = 25;
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * image_wigth + offsetX , coord.y * image_height, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int y = e.getY() / image_height;
                int x;
                int offsetX = 0;
                if (y % 2 != 0) {
                    offsetX = -25;
                }
                x = (e.getX() + offsetX) / image_wigth;
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
                Ranges.getSize().x * image_wigth + 25, Ranges.getSize().y * image_height + 40 ));
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
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        menu.addSeparator();
        menu.add(new JMenuItem("Exit")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenuItem help = new JMenuItem("Help");
        bar.add(menu);
        bar.add(help);
        Dialog rule = new Dialog();
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rule.setVisible(true);
            }
        });
        setJMenuBar(bar);
        revalidate();

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
