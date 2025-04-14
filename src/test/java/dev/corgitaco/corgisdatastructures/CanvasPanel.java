package dev.corgitaco.corgisdatastructures;

import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.box.SimpleBox;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel {
    private final BVH2D<dev.corgitaco.corgisdatastructures.box.Box> bvh2D;
    private final Settings settings;
    private int lastMouseX, lastMouseY;
    private dev.corgitaco.corgisdatastructures.box.Box worldArea;
    private int gridSize = 32;

    public CanvasPanel(BVH2D<dev.corgitaco.corgisdatastructures.box.Box> bvh2D, int width, int height, Settings settings) {
        this.bvh2D = bvh2D;
        this.settings = settings;
        setWorldArea(new SimpleBox(-width / 2.0, 0, -height / 2.0, width / 2.0, 0, height / 2.0));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                recenterWorld();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                if (SwingUtilities.isRightMouseButton(e)) {
                    // TODO: Add mouse right click to create a new box
                } else {
                    lastMouseX = e.getX();
                    lastMouseY = e.getY();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - lastMouseX;
                int deltaY = e.getY() - lastMouseY;

                double worldDeltaX = screenToWorldDistanceX(deltaX);
                double worldDeltaZ = screenToWorldDistanceZ(deltaY);

                setWorldArea(worldArea.translate(-worldDeltaX, 0, -worldDeltaZ));

                lastMouseX = e.getX();
                lastMouseY = e.getY();

                repaint();
            }
        });

        addMouseWheelListener(e -> {
            double zoomFactor = 1.1;
            int notches = e.getWheelRotation();
            double scale = Math.pow(zoomFactor, notches); // zoom in on scroll up, out on scroll down

            zoomAtPoint(e.getX(), e.getY(), scale);
            repaint();
        });

    }

    private void zoomAtPoint(int screenX, int screenZ, double scale) {
        double worldX = worldArea.minX() + screenX * worldArea.xSpan() / getWidth();
        double worldZ = worldArea.minZ() + screenZ * worldArea.zSpan() / getHeight();

        double newXSpan = worldArea.xSpan() * scale;
        double newZSpan = worldArea.zSpan() * scale;

        double minX = worldX - (worldX - worldArea.minX()) * scale;
        double maxX = minX + newXSpan;

        double minZ = worldZ - (worldZ - worldArea.minZ()) * scale;
        double maxZ = minZ + newZSpan;

        setWorldArea(new SimpleBox(minX, 0, minZ, maxX, 0, maxZ));
    }

    private void recenterWorld() {
        int width = getWidth();
        int height = getHeight();
        double centerX = worldArea.center().x();
        double centerZ = worldArea.center().z();
        double halfWorldWidth = screenToWorldDistanceX(width / 2.0);
        double halfWorldHeight = screenToWorldDistanceZ(height / 2.0);
        setWorldArea(new SimpleBox(
                centerX - halfWorldWidth, 0, centerZ - halfWorldHeight,
                centerX + halfWorldWidth, 0, centerZ + halfWorldHeight
        ));
    }

    private static final dev.corgitaco.corgisdatastructures.box.Box MAX_BOX = new SimpleBox(Integer.MIN_VALUE, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

    public void setWorldArea(dev.corgitaco.corgisdatastructures.box.Box worldArea) {
        if (MAX_BOX.contains(worldArea)) {
            this.worldArea = new SimpleBox(
                    Math.round(worldArea.minX() * 1000) / 1000.0,
                    Math.round(worldArea.minY() * 1000) / 1000.0,
                    Math.round(worldArea.minZ() * 1000) / 1000.0,
                    Math.round(worldArea.maxX() * 1000) / 1000.0,
                    Math.round(worldArea.maxY() * 1000) / 1000.0,
                    Math.round(worldArea.maxZ() * 1000) / 1000.0
            );
        }
    }

    private double screenToWorldDistanceX(double screenDeltaX) {
        double scale = Math.min(worldArea.xSpan() / getWidth(), worldArea.zSpan() / getHeight());
        return screenDeltaX * scale;
    }

    private double screenToWorldDistanceZ(double screenDeltaZ) {
        double scale = Math.min(worldArea.xSpan() / getWidth(), worldArea.zSpan() / getHeight());
        return screenDeltaZ * scale;
    }

    private int worldToScreenX(double worldX) {
        double scale = Math.min(getWidth() / worldArea.xSpan(), getHeight() / worldArea.zSpan());
        return (int) ((worldX - worldArea.minX()) * scale);
    }

    private int worldToScreenZ(double worldZ) {
        double scale = Math.min(getWidth() / worldArea.xSpan(), getHeight() / worldArea.zSpan());
        return (int) ((worldZ - worldArea.minZ()) * scale);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (settings.darkMode) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Enable anti-aliasing for cleaner lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
                dev.corgitaco.corgisdatastructures.box.Box box = boxEntry.value();
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

        drawStringsFromTop(g2d, List.of("World Area: " + worldArea.areaString(), "Settings: " + settings.getAllSettingsString()), 5, 32, 32);
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

    public void drawStringsFromBottom(Graphics2D g2d, List<String> strings, int xOffset, int offsetFromBottom, int fontSize, Color backgroundColor, Color fontColor) {
        int y = getHeight() - offsetFromBottom;
        g2d.setFont(new Font("Default", Font.PLAIN, fontSize)); // Set a larger font size
        for (int i = strings.size() - 1; i >= 0; i--) {
            String string = strings.get(i);
            int stringWidth = g2d.getFontMetrics().stringWidth(string);
            int stringHeight = g2d.getFontMetrics().getHeight();
            g2d.setColor(backgroundColor);
            g2d.fillRect(xOffset, y - stringHeight + g2d.getFontMetrics().getDescent(), stringWidth, stringHeight);
            g2d.setColor(fontColor);
            g2d.drawString(string, xOffset, y);
            y -= fontSize + 5; // Adjust line spacing as needed
        }
    }

    public void drawStringsFromTop(Graphics2D g2d, List<String> strings, int xOffset, int offsetFromTop, int fontSize, Color backgroundColor, Color fontColor) {
        int y = offsetFromTop;
        g2d.setFont(new Font("Default", Font.PLAIN, fontSize)); // Set a larger font size
        for (String string : strings) {
            int stringWidth = g2d.getFontMetrics().stringWidth(string);
            int stringHeight = g2d.getFontMetrics().getHeight();
            g2d.setColor(backgroundColor);
            g2d.fillRect(xOffset, y - stringHeight + g2d.getFontMetrics().getDescent(), stringWidth, stringHeight);
            g2d.setColor(fontColor);
            g2d.drawString(string, xOffset, y);
            y += fontSize + 5; // Adjust line spacing as needed
        }
    }

    public void drawStringsFromBottom(Graphics2D g2d, List<String> strings, int xOffset, int offsetFromBottom, int fontSize) {
        drawStringsFromBottom(g2d, strings, xOffset, offsetFromBottom, fontSize, new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 200), Color.WHITE);
    }

    public void drawStringsFromTop(Graphics2D g2d, List<String> strings, int xOffset, int offsetFromTop, int fontSize) {
        drawStringsFromTop(g2d, strings, xOffset, offsetFromTop, fontSize, new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 200), Color.WHITE);
    }


    private final Color[] colorLookup = generateColorLookup(16);

    private Color[] generateColorLookup(int count) {
        Color[] colors = new Color[count];
        for (int i = 0; i < count; i++) {
            float hue = (float) i / count; // Hue ranges from 0.0 to 1.0
            colors[i] = Color.getHSBColor(hue, 1.0f, 1.0f); // Full saturation and brightness
        }
        return colors;
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
