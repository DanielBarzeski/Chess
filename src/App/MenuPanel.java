package App;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final JButton back, restart, pause;
    private final JPanel panel;

    public MenuPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(89, 38, 11));
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(90, 5));
        panel.setBackground(new Color(89, 38, 11));
        panel.setVisible(false);

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, height / 4));
        back = new JButton("Back");
        back.setBackground(Color.white);
        back.setForeground(new Color(143,100,23));
        back.setFocusable(false);
        back.setVisible(false);
        back.addActionListener(e_ -> {
            Game.setVISIBLE(false);
            if (!Game.isFINISHED()) {
                Game.END();
            }
        });

        restart = new JButton("Restart");
        restart.setBackground(Color.white);
        restart.setForeground(new Color(143,100,23));
        restart.setFocusable(false);
        restart.setVisible(false);
        restart.addActionListener(e_ -> {
            Game.setRESTARTING(true);
            Game.START();
        });

        pause = new JButton("Pause");
        pause.setBackground(Color.white);
        pause.setForeground(new Color(143,100,23));
        pause.setFocusable(false);
        pause.setVisible(false);
        pause.setPreferredSize(new Dimension(90, restart.getPreferredSize().height));
        pause.addActionListener(e_ -> Game.setPAUSED(!Game.isPAUSED()));

        add(back);
        add(restart);
        add(pause);
        add(panel);
    }

    public void update() {
        back.setVisible(Game.isVISIBLE());
        restart.setVisible(Game.isVISIBLE());
        pause.setVisible(!Game.isFINISHED());
        panel.setVisible(Game.isVISIBLE() && Game.isFINISHED());
        if (Game.isPAUSED()) pause.setText("Resume");
        else pause.setText("Pause");
        repaint();
        // maybe will make problems: {
       // pause.requestFocus();
        // }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.drawRect(1,1, getWidth()-3, getHeight()-3);
    }
}
