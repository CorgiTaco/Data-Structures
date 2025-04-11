package dev.corgitaco.datastructures;

import dev.corgitaco.datastructures.bvh.BVH2D;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class BVHViewer {

    @Test
    public void renderPanel() {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Simple Swing Example");

        // Set the size of the frame
        frame.setSize(300, 200);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Exit the application when the frame is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLabel with some text
        JLabel label = new JLabel("Hello, Swing!", SwingConstants.CENTER);

        // Add the label to the frame's content pane
        frame.getContentPane().add(label);

        // Set the frame to be visible
        frame.setVisible(true);
    }

    @Test
    public void swing2DBVHView() {
        JFrame frame = new JFrame("BVH Node Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800;
        int height = 600;
        frame.setSize(width, height);


        int minBoxSize = 1;
        int maxBoxSize = 5000000;
        int size = Integer.MAX_VALUE - (maxBoxSize + minBoxSize);

        int numberOfBoxes = 500000;

        BVH2D<Box> bvh2D = BVH2D.floodedBVH(32, numberOfBoxes, size, minBoxSize, maxBoxSize, false);

        if (false) {
            int range = 10000000;
            bvh2D.removeArea(new SimpleBox(-range, 0, -range, range, 0, range));
        }

        frame.add(new CanvasPanel(bvh2D, width, height, new CanvasPanel.Settings().setRenderNodes(true).setRenderGrid(false)));
        frame.pack();
        frame.setVisible(true);

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}