package App;

public class Clock {
    public static final Clock whiteTimer = new Clock(), blackTimer = new Clock();

    private int startHour, startMinute, startSecond, hour, minute, second;
    private boolean run;

    public void move() {
        if (run) {
            second--;
            if (second < 0) {
                second = 59;
                minute--;
                if (minute < 0) {
                    minute = 59;
                    hour--;
                }
            }
            if (hour == 0 && minute == 0 && second == 0) {
                Game.END();
            }
        }
    }


    public void reset() {
        hour = startHour;
        minute = startMinute;
        second = startSecond;
    }

    public void setHour(int startHour) {
        this.startHour = startHour;
        this.hour = startHour;
        run = this.startHour != 0 || this.startMinute != 0 || this.startSecond != 0;
    }

    public void setMinute(int minute) {
        this.startMinute = minute;
        this.minute = minute;
        run = this.startHour != 0 || this.startMinute != 0 || this.startSecond != 0;
    }

    public void setSecond(int second) {
        this.startSecond = second;
        this.second = second;
        run = this.startHour != 0 || this.startMinute != 0 || this.startSecond != 0;
    }

    @Override
    public String toString() {
        if (run) {
            String time = "";
            if (hour < 10) {
                time += "0";
            }
            time += hour + ":";
            if (minute < 10) {
                time += "0";
            }
            time += minute + ":";
            if (second < 10) {
                time += "0";
            }
            time += second;
            return time;
        } else return "infinite";
    }
}
