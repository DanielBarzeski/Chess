package App;

import File.Picture;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("CHESS GAME");
        getContentPane().setBackground(new Color(180, 100, 9));
        setIconImage(Picture.ImageType.BLACK_PAWN.getImage());
        setLayout(new FlowLayout(FlowLayout.CENTER)); // change to border layout
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        MainPanel mainPanel = new MainPanel();
        mainPanel.run();
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
