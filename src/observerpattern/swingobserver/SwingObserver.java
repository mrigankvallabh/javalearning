package observerpattern.swingobserver;

import javax.swing.JFrame;
import javax.swing.JButton;

class SwingObserver {
    JFrame frame;
    static void main(String[] args) {
        var example = new SwingObserver();
        example.go();        
    }

    void go() {
        frame = new JFrame();
        frame.setSize(300, 400);
        frame.setVisible(true);
        var button = new JButton("Should I do it?");
        frame.add(button);
        button.addActionListener(event -> System.out.println("Don't do it!"));
        button.addActionListener(event -> System.out.println("Do it!"));
    }
}
