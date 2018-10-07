package me.DevQuicks.FluuAPI.utils;

public class Onlinetime {

    private int minutes;
    private int hours;
    private int days;

    public Onlinetime(int minutes) {
        setTime(minutes);
    }


    private void setTime(int minutes) {
        if(minutes < 59) {
            this.minutes = minutes;
            this.hours = 0;
            this.days = 0;
        } else if(minutes < 1440){
            int hour = minutes / 60;
            int min = minutes - (hour * 60);
            this.minutes = min;
            this.hours = hour;
        } else {
            int days = minutes / 60 / 24;
            int hour = minutes / 60 - (days * 24);
            int min = minutes - (hour * 60 + days * 24 * 60);
            this.minutes = min;
            this.hours = hour;
            this.days = days;
        }
    }


    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDays() {
        return days;
    }

}
