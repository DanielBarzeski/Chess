package App;

import javax.swing.*;
import java.awt.*;

public class TimerPanel extends JPanel {

    public TimerPanel(boolean white) {
        String[] minSecInts = new String[60];
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minSecInts[i] = "0" + i;
            } else {
                minSecInts[i] = Integer.toString(i);
            }
        }

        JComboBox<String> hours = new JComboBox<>(new String[]{"00", "01", "02"}),
                minutes = new JComboBox<>(minSecInts),
                seconds = new JComboBox<>(minSecInts);

        hours.addActionListener(e_ -> {
            int hoursValue = Integer.parseInt(hours.getSelectedItem() + "");
            if (white)
                Clock.whiteTimer.setHour(hoursValue);
            else
                Clock.blackTimer.setHour(hoursValue);
        });

        minutes.addActionListener(e_ -> {
            int minutesValue = Integer.parseInt(minutes.getSelectedItem() + "");
            if (white)
                Clock.whiteTimer.setMinute(minutesValue);
            else
                Clock.blackTimer.setMinute(minutesValue);
        });

        seconds.addActionListener(e_ -> {
            int secondsValue = Integer.parseInt(seconds.getSelectedItem() + "");
            if (white)
                Clock.whiteTimer.setSecond(secondsValue);
            else
                Clock.blackTimer.setSecond(secondsValue);
        });

//        hours.setSelectedItem("1");
//        minutes.setSelectedItem("30");

        JLabel label = new JLabel();
        if (white) {
            setBackground(Color.white);
            label.setForeground(new Color(143, 100, 23));
            label.setText("White timer :");
        } else {
            setBackground(Color.white);
            label.setForeground(new Color(143, 100, 23));
            label.setText("Black timer :");
        }
        add(label);
        add(hours);
        add(minutes);
        add(seconds);
    }

}
