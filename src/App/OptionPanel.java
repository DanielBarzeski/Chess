package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class OptionPanel extends JPanel {
    private final JButton friendButton, aiButton;

    public OptionPanel() {
        setLayout(new GridLayout(0, 1, 0, 15));
        setOpaque(false);
        JComboBox<String> colorCombo = new JComboBox<>(new String[]{" WHITE PIECES", " BLACK PIECES"});
        colorCombo.setBackground(Color.white);
        colorCombo.setForeground(new Color(143, 100, 23));
        colorCombo.addActionListener(e_ -> {
            Game.setWHITE_PLAYER(Objects.equals(colorCombo.getSelectedItem(), " WHITE PIECES"));
            colorCombo.setFocusable(false);
        });


        this.aiButton = new JButton("Play against AI");
        this.aiButton.setBackground(Color.white);
        this.aiButton.setForeground(new Color(143, 100, 23));
        this.aiButton.setFocusable(false);

        this.friendButton = new JButton("Play against friend");
        this.friendButton.setBackground(Color.white);
        this.friendButton.setForeground(new Color(143, 100, 23));
        this.friendButton.setFocusable(false);

        add(this.aiButton);
        add(this.friendButton);
        add(new TimerPanel(true));
        add(new TimerPanel(false));
        add(colorCombo);
    }

    public void setFriendButton(ActionListener e) {
        this.friendButton.addActionListener(e);
    }

    public void setAiButton(ActionListener e) {
        this.aiButton.addActionListener(e);
    }
}
