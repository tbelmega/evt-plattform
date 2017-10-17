package de.belmega.eventers.booking;

public enum EventTime {

    TIME_00_00(0, 0),
    TIME_00_30(0, 30),
    TIME_01_00(1, 0),
    TIME_01_30(1, 30),
    TIME_02_00(2, 0),
    TIME_02_30(2, 30),
    TIME_03_00(3, 0),
    TIME_03_30(3, 30),
    TIME_04_00(4, 0),
    TIME_04_30(4, 30),
    TIME_05_00(5, 0),
    TIME_05_30(5, 30),
    TIME_06_00(6, 0),
    TIME_06_30(6, 30),
    TIME_07_00(7, 0),
    TIME_07_30(7, 30),
    TIME_08_00(8, 0),
    TIME_08_30(8, 30),
    TIME_09_00(9, 0),
    TIME_09_30(9, 30),

    TIME_10_00(10, 0),
    TIME_10_30(10, 30),
    TIME_11_00(11, 0),
    TIME_11_30(11, 30),
    TIME_12_00(12, 0),
    TIME_12_30(12, 30),
    TIME_13_00(13, 0),
    TIME_13_30(13, 30),
    TIME_14_00(14, 0),
    TIME_14_30(14, 30),
    TIME_15_00(15, 0),
    TIME_15_30(15, 30),
    TIME_16_00(16, 0),
    TIME_16_30(16, 30),
    TIME_17_00(17, 0),
    TIME_17_30(17, 30),
    TIME_18_00(18, 0),
    TIME_18_30(18, 30),
    TIME_19_00(19, 0),
    TIME_19_30(19, 30),

    TIME_20_00(20, 0),
    TIME_20_30(20, 30),
    TIME_21_00(21, 0),
    TIME_21_30(21, 30),
    TIME_22_00(22, 0),
    TIME_22_30(22, 30),
    TIME_23_00(23, 0),
    TIME_23_30(23, 30);

    private final int hours;
    private final int minutes;

    private EventTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return getText();
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public String getText() {
        // output hours and minutes in a format with trailing zeros, like 08:00 for 8 und 0
        return String.format("%1$02d:%2$02d", hours, minutes);
    }
}
