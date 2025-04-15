package dev.corgitaco.corgisdatastructures.test.nearestposition;

import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.box.SimpleBox;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.AABBQuery;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;
import dev.corgitaco.corgisdatastructures.panel.BVHCanvasPanel;
import dev.corgitaco.corgisdatastructures.panel.QuadTreeCanvasPanel;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class NearestPositionViewerTest {

    @Test
    public void swing2DQuadTreeView() {
        JFrame frame = new JFrame("BVH Node Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800;
        int height = 600;
        frame.setSize(width, height);

        System.out.println(Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");

        System.out.println(usedMemory() + " MB used");

        frame.add(new QuadTreeCanvasPanel(width, height, true));
        frame.pack();
        frame.setVisible(true);

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static long usedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
    }
}