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
        String sHour = hour + "";
        String sMinute = minute + "";

        if (hour < 10) {
            sHour = "0" + sHour;
        }

        if (minute < 10) {
            sMinute = "0" + sMinute;
        }

        return sHour + ":" + sMinute;
    }

    public boolean greaterOrEqual(Time time) {
        return (this.hour > time.getHour()) || (time.getHour() == this.hour && this.minute >= time.getMinute());
    }
}
