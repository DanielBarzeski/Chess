package App;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private final JButton undo, redo;

    public ControlPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        setBackground(new Color(89, 38, 11));

        undo = new JButton("Undo");
        undo.setBackground(Color.white);
        undo.setForeground(new Color(143, 100, 23));
        undo.setFocusable(false);
        undo.setVisible(false);
        undo.addActionListener(e_ -> {

        });

        redo = new JButton("Redo");
        redo.setBackground(Color.white);
        redo.setForeground(new Color(143, 100, 23));
        redo.setFocusable(false);
        redo.setVisible(false);
        redo.addActionListener(e_ -> {

        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(undo, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(Box.createVerticalStrut(100), gbc);
        add(redo, gbc);
    }

    public void update() {
        undo.setVisible(Game.isVISIBLE());
        redo.setVisible(Game.isVISIBLE());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }
}
