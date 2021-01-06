package app.ridesharingapp.Model;

import java.util.Calendar;
import java.util.Objects;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        String sDay = day + "";
        String sMonth = month + "";

        if (day < 10) {
            sDay = "0" + sDay;
        }

        if (month < 10) {
            sMonth = "0" + sMonth;
        }

        return sDay + "." + sMonth + "." + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return day == date.day &&
                month == date.month &&
                year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    public boolean inFuture() {
        if (year >= Calendar.getInstance().get(Calendar.YEAR) &&
                month >= (Calendar.getInstance().get(Calendar.MONTH) + 1) &&
                day > Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){

            return true;
        }

            return false;
    }

    public boolean isNow() {
        if (year == Calendar.getInstance().get(Calendar.YEAR) &&
                month == (Calendar.getInstance().get(Calendar.MONTH) + 1) &&
                day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){

            return true;
        }

        return false;
    }
}
