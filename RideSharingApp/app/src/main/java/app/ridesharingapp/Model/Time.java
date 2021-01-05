package app.ridesharingapp.Model;

public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
    }

    public boolean greaterOrEqual(Time time) {
        return (time.getHour() > this.hour) || (time.getHour() == this.hour && time.getMinute() >= this.minute);
    }
}
