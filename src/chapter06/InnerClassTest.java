package chapter06;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

import javax.swing.JOptionPane;
import javax.swing.Timer;

final class InnerClassTest {
    final static void main(String[] args) {
        var clock = new TalkingClock();
        clock.start(2_000, true);
        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}

final class TalkingClock {

    final void start(int interval, boolean beep) {
        final class TimePrinter implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("At the tone, the time is " + Instant.ofEpochMilli(e.getWhen()));
                if (beep) { // * same as TalkingClock.this.beep
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        var listener = new TimePrinter(); // * same as this.new TimePrinter()
        /*
         * Can use the Anonymous inner class
         * var listener = new ActionListener() {
         * 
         * @Override
         * public void actionPerformed(ActionEvent e) {
         * System.out.println("At the tone, the time is " +
         * Instant.ofEpochMilli(e.getWhen()));
         * if (beep) {
         * Toolkit.getDefaultToolkit().beep();
         * }
         * }
         * };
         */
        var timer = new Timer(interval, listener);
        timer.start();
    }

}