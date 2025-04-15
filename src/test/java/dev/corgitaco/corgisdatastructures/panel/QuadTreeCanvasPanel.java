package dev.corgitaco.corgisdatastructures.panel;

import dev.corgitaco.corgisdatastructures.position.Position;
import dev.corgitaco.corgisdatastructures.position.SimplePosition;
import dev.corgitaco.corgisdatastructures.datastructure.position.nearest.QuadTreeNearestPosition;
import dev.corgitaco.corgisdatastructures.datastructure.position.nearest.Target;

import java.awt.*;

public class QuadTreeCanvasPanel extends WorldCanvasPanel {

    private final QuadTreeNearestPosition<Position> quadTree = new QuadTreeNearestPosition<>();

    public QuadTreeCanvasPanel(int width, int height, boolean darkMode) {
        super(width, height, darkMode);
        int offset = 50000;
        quadTree.setPosition(new SimplePosition(offset + 25, 25, offset + 25));
        quadTree.setPosition(new SimplePosition(offset, 0, offset));
        quadTree.setPosition(new SimplePosition(offset + 500, 500, offset + 500));
        quadTree.setPosition(new SimplePosition(offset + 1000, 1000, offset + 1000));
        quadTree.setPosition(new SimplePosition(offset - 1000, -1000, offset - 1000));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Target<Position, Position> targetsInBox : this.quadTree.getTargetsInBox(worldArea)) {
            Position position = targetsInBox.position();

            int screenX = worldToScreenX(position.getX());
            int screenY = worldToScreenZ(position.getZ());
            g.setColor(Color.RED);
            g.fillRect(screenX - 5, screenY - 5, 10, 10);
        }

        drawStringsFromTop((Graphics2D) g, java.util.List.of("World Area: " + worldArea.areaString()), 5, 32, 32);
    }
}
