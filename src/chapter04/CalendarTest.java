package chapter04;

import java.time.DayOfWeek;
import java.time.LocalDate;

final class CalendarTest {
    static void main(String[] args) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int today = now.getDayOfMonth();

        LocalDate currentMonth = now.minusDays(today - 1); // * set to 1st of Month
        DayOfWeek weekDay = currentMonth.getDayOfWeek();
        int weekNum = weekDay.getValue(); // * MON-SUN: 1-7
        System.out.println("MON  TUE  WED  THU  FRI  SAT  SUN");
        for (int i = 1; i < weekNum; i++) {
            System.out.print(" ".repeat(5));
        }

        while (currentMonth.getMonthValue() == month) {
            System.out.printf("%3d", currentMonth.getDayOfMonth());
            if (currentMonth.getDayOfMonth() == today) {
                System.out.print("* ");
            } else {
                System.out.print("  ");
            }
            currentMonth = currentMonth.plusDays(1L);
            if (currentMonth.getDayOfWeek().getValue() == 1) {
                System.out.println();
            }
        }
        if (currentMonth.getDayOfWeek().getValue() != 1) {
            System.out.println();
        }
    }
}
