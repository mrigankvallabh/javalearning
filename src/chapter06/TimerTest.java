package chapter06;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.swing.Timer;

final class TimerTest {
    final static void main(String[] args) {
        var listener = new TimePrinter();
        var timer = new Timer(1_000, listener);
        timer.start();
        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}

final class TimePrinter implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("At the tone, the time is " + Instant.ofEpochMilli(e.getWhen()));
        Toolkit.getDefaultToolkit().beep();
    }

}

final class LambdaTest {
    static void main(String[] args) {
        var planets = new String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };
        System.out.println(Arrays.toString(planets));
        // Arrays.sort(planets, (p1, p2) -> p1.length() - p2.length());
        Arrays.sort(planets, Comparator.comparing(String::length));
        System.out.println(Arrays.toString(planets));
        var timer = new Timer(1_000, System.out::println);
        timer.start();
        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}
