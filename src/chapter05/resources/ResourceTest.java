package chapter05.resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

class ResourceTest {
    static void main() throws IOException {
        var clazz = ResourceTest.class;
        var aboutUrl = clazz.getResource("about.gif");
        var icon = new ImageIcon(aboutUrl);
        var inStream1 = clazz.getResourceAsStream("data/about.txt");
        var about = new String(inStream1.readAllBytes(), StandardCharsets.UTF_8);
        var inStream2 = clazz.getResourceAsStream("data/title.txt");
        var title = new String(inStream2.readAllBytes(), StandardCharsets.UTF_8).strip();

        JOptionPane.showMessageDialog(null, about, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }
}
