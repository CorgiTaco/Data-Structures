package dev.corgitaco.corgisdatastructures.test.bvh;

import dev.corgitaco.corgisdatastructures.coord.box.Box;
import dev.corgitaco.corgisdatastructures.coord.box.SimpleBox;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.AABBQuery;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;
import dev.corgitaco.corgisdatastructures.panel.BVHCanvasPanel;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class BVHViewerTest {

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
        System.out.println(Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");

        System.out.println(usedMemory() + " MB used");

        int numberOfBoxes = 500000;

        BVH2D<Box> bvh2D = AABBQuery.floodAABBQueryBoxes2D(new BVH2D<>(), 32, numberOfBoxes, size, minBoxSize, maxBoxSize, false, false, inserted -> false);

        if (false) {
            int range = 10000000;
            bvh2D.removeArea(new SimpleBox(-range, 0, -range, range, 0, range));
        }

        frame.add(new BVHCanvasPanel(bvh2D, width, height, new BVHCanvasPanel.Settings().setRenderNodes(false).setRenderBoxes(true).setRenderGrid(false)));
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

    private static long totalMemory() {
        return Runtime.getRuntime().totalMemory() / (1024 * 1024);
    }

}