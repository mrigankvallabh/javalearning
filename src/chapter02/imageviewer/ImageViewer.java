package chapter02.imageviewer;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

final class ImageViewer {
    static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new ImageViewerFrame();
            frame.setTitle("Image Viewer");
            frame.setVisible(true);
        });    
    }    
}

final class ImageViewerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 480;
    
    ImageViewerFrame() {
        super();
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var label = new JLabel(); // * Use a label to display the image
        add(label);

        var chooser = new JFileChooser(); // * Set up the file chooser
        chooser.setCurrentDirectory(new File("."));

        var menuBar = new JMenuBar(); // * Set up the Menu bar
        setJMenuBar(menuBar);
        var menu = new JMenu("File");
        menuBar.add(menu);
        var openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(_ -> {
            int showOpenDialog = chooser.showOpenDialog(null); // * Show file chooser dialog
            if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
                var fileName = chooser.getSelectedFile().getPath();
                label.setIcon(new ImageIcon(fileName));
            }
        });

        var exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(_ -> System.exit(0));
    }
}
