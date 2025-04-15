package dev.corgitaco.corgisdatastructures.panel;

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

public abstract class WorldCanvasPanel extends JPanel {
    protected final boolean darkMode;
    protected int lastMouseX, lastMouseY;
    protected Box worldArea;
    protected int gridSize = 32;

    public WorldCanvasPanel(int width, int height, boolean darkMode) {
        this.darkMode = darkMode;
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

    private static final Box MAX_BOX = new SimpleBox(Integer.MIN_VALUE, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

    public void setWorldArea(Box worldArea) {
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

    protected double screenToWorldDistanceX(double screenDeltaX) {
        double scale = Math.min(worldArea.xSpan() / getWidth(), worldArea.zSpan() / getHeight());
        return screenDeltaX * scale;
    }

    protected double screenToWorldDistanceZ(double screenDeltaZ) {
        double scale = Math.min(worldArea.xSpan() / getWidth(), worldArea.zSpan() / getHeight());
        return screenDeltaZ * scale;
    }

    protected int worldToScreenX(double worldX) {
        double scale = Math.min(getWidth() / worldArea.xSpan(), getHeight() / worldArea.zSpan());
        return (int) ((worldX - worldArea.minX()) * scale);
    }

    protected int worldToScreenZ(double worldZ) {
        double scale = Math.min(getWidth() / worldArea.xSpan(), getHeight() / worldArea.zSpan());
        return (int) ((worldZ - worldArea.minZ()) * scale);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (this.darkMode) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Enable anti-aliasing for cleaner lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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


    protected final Color[] colorLookup = generateColorLookup(16);

    private Color[] generateColorLookup(int count) {
        Color[] colors = new Color[count];
        for (int i = 0; i < count; i++) {
            float hue = (float) i / count; // Hue ranges from 0.0 to 1.0
            colors[i] = Color.getHSBColor(hue, 1.0f, 1.0f); // Full saturation and brightness
        }
        return colors;
    }


}
