package app.ridesharingapp.Model;

import java.util.Calendar;

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

    public boolean lessOrEqual(String departureTime) {

        String[] timeEnitites = departureTime.split(":");
        Time time = new Time(Integer.parseInt(timeEnitites[0]), Integer.parseInt(timeEnitites[1]));

        return (this.hour < time.getHour()) || (time.getHour() == this.hour && this.minute < time.getMinute());
    }

    public boolean isCorrect() {
        if (hour > Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ||
                (hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && minute > Calendar.getInstance().get(Calendar.MINUTE))) {

            return true;
        }

        return false;
    }
}
