package dev.corgitaco.datastructures;

import dev.corgitaco.datastructures.bvh.BVH;
import dev.corgitaco.datastructures.bvh.BVH2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.BiPredicate;

public class CanvasPanel extends JPanel {
    private final BVH2D<Box> bvh2D;
    private final Settings settings;
    private int lastMouseX, lastMouseY;
    private Box worldArea;
    private int gridSize = 32;

    public CanvasPanel(BVH2D<Box> bvh2D, int width, int height, Settings settings) {
        this.bvh2D = bvh2D;
        this.settings = settings;
        this.worldArea = new SimpleBox(-width / 2.0, 0, -height / 2.0, width / 2.0, 0, height / 2.0);

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

                worldArea = worldArea.translate(-worldDeltaX, 0, -worldDeltaZ);

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

        worldArea = new SimpleBox(minX, 0, minZ, maxX, 0, maxZ);
    }

    private void recenterWorld() {
        int width = getWidth();
        int height = getHeight();
        double centerX = worldArea.center().x();
        double centerZ = worldArea.center().z();
        double halfWorldWidth = screenToWorldDistanceX(width / 2.0);
        double halfWorldHeight = screenToWorldDistanceZ(height / 2.0);
        this.worldArea = new SimpleBox(
                centerX - halfWorldWidth, 0, centerZ - halfWorldHeight,
                centerX + halfWorldWidth, 0, centerZ + halfWorldHeight
        );
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

        if (this.settings.renderBoxes) {
            bvh2D.query(worldArea, boxEntry -> {
                Box box = boxEntry.value();
                g2d.setColor(colorLookup[((int) (box.xSpan() + box.zSpan())) % colorLookup.length]);
                int x = worldToScreenX(box.minX());
                int y = worldToScreenZ(box.minZ());
                int width = worldToScreenX(box.maxX()) - x;
                int height = worldToScreenZ(box.maxZ()) - y;
                g2d.fillRect(x, y, width, height);
                return false;
            });
        }

        if (this.settings.renderNodes) {
            // Draw BVH boxes
            bvh2D.nodeView(worldArea, Box::intersects2D, Integer.MAX_VALUE, (depth, box) -> {
                g2d.setStroke(new BasicStroke(Math.max(1, 8 - depth)));
                g2d.setColor(colorLookup[depth % colorLookup.length]);
                int x = worldToScreenX(box.minX());
                int y = worldToScreenZ(box.minZ());
                int width = worldToScreenX(box.maxX()) - x;
                int height = worldToScreenZ(box.maxZ()) - y;
                g2d.drawRect(x, y, width, height);
            });
        }
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

    }
}
