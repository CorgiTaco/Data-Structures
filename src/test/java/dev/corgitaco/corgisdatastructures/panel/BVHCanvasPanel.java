package dev.corgitaco.corgisdatastructures.panel;

import dev.corgitaco.corgisdatastructures.coord.box.Box;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BVHCanvasPanel extends WorldCanvasPanel {
    private final Settings settings;
    private final BVH2D<Box> bvh2D;

    public BVHCanvasPanel(BVH2D<Box> bvh2D, int width, int height, Settings settings) {
        super(width, height, settings.darkMode);
        this.bvh2D = bvh2D;
        this.settings = settings;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (settings.renderGrid) {
            // Draw grid
            g2d.setColor(Color.LIGHT_GRAY);

            double startX = Math.floor(worldArea.minX() / gridSize) * gridSize;
            double endX = worldArea.maxX();
            double startZ = Math.floor(worldArea.minZ() / gridSize) * gridSize;
            double endZ = worldArea.maxZ();


            for (double x = startX; x <= endX; x += gridSize) {
                int screenX = worldToScreenX(x);
                g2d.drawLine(screenX, 0, screenX, getHeight());
            }

            for (double z = startZ; z <= endZ; z += gridSize) {
                int screenZ = worldToScreenZ(z);
                g2d.drawLine(0, screenZ, getWidth(), screenZ);
            }
        }

        int[] visibleBoxes = new int[1];
        double queryTime = -1L;
        if (this.settings.renderBoxes) {
            long queryStart = System.nanoTime();
            bvh2D.query(worldArea, boxEntry -> {
                Box box = boxEntry.value();
                g2d.setColor(colorLookup[((int) (box.xSpan() + box.zSpan())) % colorLookup.length]);
                int x = worldToScreenX(box.minX());
                int y = worldToScreenZ(box.minZ());
                int width = worldToScreenX(box.maxX()) - x;
                int height = worldToScreenZ(box.maxZ()) - y;
                g2d.fillRect(x, y, width, height);
                visibleBoxes[0]++;
                return false;
            });
            long queryEnd = System.nanoTime();
            queryTime = (queryEnd - queryStart) / 1_000_000.0; // Convert to milliseconds
        }

        double renderNodeQueryTime = -1L;

        int[] highestDepth = new int[] {-1};
        int[] nodeCount = new int[1];
        if (this.settings.renderNodes) {
            long queryStart = System.nanoTime();
            // Draw BVH boxes
            bvh2D.nodeView(worldArea, Box::intersects2D, Integer.MAX_VALUE, (depth, box) -> {
                nodeCount[0]++;
                highestDepth[0] = Math.max(highestDepth[0], depth);

                g2d.setStroke(new BasicStroke(Math.max(1, 8 - depth)));
                g2d.setColor(colorLookup[depth % colorLookup.length]);
                int x = worldToScreenX(box.minX());
                int y = worldToScreenZ(box.minZ());
                int width = worldToScreenX(box.maxX()) - x;
                int height = worldToScreenZ(box.maxZ()) - y;
                g2d.drawRect(x, y, width, height);
            });
            long queryEnd = System.nanoTime();
            renderNodeQueryTime = (queryEnd - queryStart) / 1_000_000.0; // Convert to milliseconds
        }

        drawStringsFromTop(g2d, java.util.List.of("World Area: " + worldArea.areaString(), "Settings: " + settings.getAllSettingsString()), 5, 32, 32);
        List<String> bvhInformation = new ArrayList<>();
        bvhInformation.add("BVH Information:");
        bvhInformation.add("BVH Boxes: " + bvh2D.size());
        if (settings.renderBoxes) {
            bvhInformation.add("Visible BVH boxes: " + visibleBoxes[0]);
        }

        if (settings.renderNodes) {
            bvhInformation.add("Visible BVH nodes: " + nodeCount[0]);
        }

        if (queryTime > 0) {
            bvhInformation.add(String.format("Last Query Time: %.2fms", queryTime));
        }

        if (renderNodeQueryTime > 0) {
            bvhInformation.add(String.format("Last Render Node Query Time: %.2fms", renderNodeQueryTime));
        }

        if (highestDepth[0] > -1) {
            bvhInformation.add("Highest Depth: " + highestDepth[0]);
        }

        drawStringsFromBottom(g2d, bvhInformation, 5, 32, 32);
    }

    public static class Settings {

        private boolean renderBoxes = true;
        private boolean renderGrid = true;
        private boolean renderNodes = false;
        private boolean darkMode = true;


        public Settings setRenderBoxes(boolean renderBoxes) {
            this.renderBoxes = renderBoxes;
            return this;
        }

        public Settings setRenderGrid(boolean renderGrid) {
            this.renderGrid = renderGrid;
            return this;
        }

        public Settings setRenderNodes(boolean renderNodes) {
            this.renderNodes = renderNodes;
            return this;
        }

        public Settings setDarkMode(boolean darkMode) {
            this.darkMode = darkMode;
            return this;
        }

        public String getAllSettingsString() {
            return "RenderBoxes: " + renderBoxes + ", RenderGrid: " + renderGrid + ", RenderNodes: " + renderNodes;
        }
    }
}
