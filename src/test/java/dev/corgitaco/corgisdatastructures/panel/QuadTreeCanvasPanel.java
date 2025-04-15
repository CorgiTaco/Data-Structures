package dev.corgitaco.corgisdatastructures.panel;

import dev.corgitaco.corgisdatastructures.coord.position.FlooredSimplePosition2D;
import dev.corgitaco.corgisdatastructures.coord.position.Position;
import dev.corgitaco.corgisdatastructures.coord.position.SimplePosition;
import dev.corgitaco.corgisdatastructures.datastructure.position.nearest.QuadTreeNearestPosition;
import dev.corgitaco.corgisdatastructures.datastructure.position.nearest.Target;

import java.awt.*;
import java.util.Collection;
import java.util.Random;

public class QuadTreeCanvasPanel extends WorldCanvasPanel {

    private final QuadTreeNearestPosition<FlooredSimplePosition2D> quadTree = new QuadTreeNearestPosition<>();

    public QuadTreeCanvasPanel(int width, int height, boolean darkMode) {
        super(width, height, darkMode);

        int areaRadius = 32000;

        Random random = new Random(43);

        for (int i = 0; i < 50000; i++) {
            int x = random.nextInt(-areaRadius, areaRadius);
            int z = random.nextInt(-areaRadius, areaRadius);
            FlooredSimplePosition2D position = new FlooredSimplePosition2D(x, z);
            quadTree.setPosition(position);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Collection<Target<FlooredSimplePosition2D, FlooredSimplePosition2D>> targetsInBox1 = this.quadTree.getTargetsInBox(worldArea);
        for (Target<FlooredSimplePosition2D, FlooredSimplePosition2D> targetsInBox : targetsInBox1) {
            Position position = targetsInBox.position();

            int screenX = worldToScreenX(position.getX());
            int screenY = worldToScreenZ(position.getZ());
            g.setColor(Color.RED);
            g.fillRect(screenX - 5, screenY - 5, 10, 10);
        }

        drawStringsFromTop((Graphics2D) g, java.util.List.of("World Area: " + worldArea.areaString()), 5, 32, 32);
    }
}
