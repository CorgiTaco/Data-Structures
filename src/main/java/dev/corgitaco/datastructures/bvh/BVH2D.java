package dev.corgitaco.datastructures.bvh;

import dev.corgitaco.datastructures.Box;
import dev.corgitaco.datastructures.SimpleBox2D;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class BVH2D<VALUE> implements BVH<VALUE> {
    private static final int DEFAULT_MAX_ENTRIES = 4;
    private static final int DEFAULT_CHILD_COUNT = 2;
    private Node root;

    public BVH2D() {
        this(DEFAULT_MAX_ENTRIES, DEFAULT_CHILD_COUNT);
    }

    public BVH2D(int maxEntries, int childCount) {
        this.root = new Node(null, 0, maxEntries, childCount);
    }

    public void insert(Box bound, VALUE value) {
        bound = bound.as2D();
        root = root.insert(bound, value);
    }

    public boolean removeValue(@Nullable Box bound, VALUE value, OverlapFunction overlapFunction, BiPredicate<VALUE, VALUE> test) {
        Node node = new Node(null, 0, this.root.maxEntries, this.root.childCount);
        boolean[] changed = {false};
        this.root.queryUntil(bound, overlapFunction, (entry) -> {
            if (!test.test(entry.value(), value)) {
                node.insert(entry.bound(), entry.value());
            } else {
                changed[0] = true;
            }
            return false;
        });
        if (changed[0]) {
            this.root = node;
        }

        return changed[0];
    }

    @Override
    public boolean removeArea(Box bound, OverlapFunction overlapFunction) {
        if (isEmpty()) {
            return false;
        }
        Node node = new Node(null, 0, this.root.maxEntries, this.root.childCount);
        boolean[] changed = {false};
        this.root.queryUntil(null, (box, box2) -> true, (entry) -> {
            if (!overlapFunction.test(entry.bound(), bound)) {
                node.insert(entry.bound(), entry.value());
            } else {
                changed[0] = true;
            }
            return false;
        });
        this.root = node;

        return changed[0];
    }

    @Override
    public boolean query(@Nullable Box bound, OverlapFunction overlapFunction, Predicate<Entry<VALUE>> interruptionTest) {
        if (isEmpty()) {
            return false;
        }
        return this.root.queryUntil(bound, overlapFunction, interruptionTest);
    }

    public void clear() {
        this.root = new Node(null, 0, this.root.maxEntries, this.root.childCount);
    }

    public boolean isEmpty() {
        return root.entryCount == 0 && root.children[0] == null;
    }

    @Override
    public OverlapFunction overlapFunction() {
        return Box::intersects2D;
    }

    @Override
    public void nodeView(Box bound, BiFunction<Box, Box, Boolean> overlapFunction, int maxDepth, BiConsumer<Integer, Box> consumer) {
        this.root.nodeView(bound, overlapFunction, maxDepth, consumer);
    }

    class Node {

        private Entry<VALUE>[] entries;
        private final int maxEntries;
        private final Node[] children;
        private final int childCount;
        private int entryCount;
        private final int depth;
        private Box nodeBound;

        public Node(Box bound, int depth, int maxEntries, int childCount) {
            this.nodeBound = bound;
            this.entries = new Entry[maxEntries];
            this.maxEntries = maxEntries;
            this.children = uncheckedCast(Array.newInstance(Node.class, childCount));
            this.childCount = childCount;
            this.entryCount = 0;
            this.depth = depth;
        }

        @SuppressWarnings("unchecked")
        public static <T> T uncheckedCast(Object value) {
            return (T) value;
        }

        public Node insert(Box newBound, VALUE value) {
            if (children[0] == null) {
                if (entryCount < DEFAULT_MAX_ENTRIES) {
                    entries[entryCount++] = BVH2D.this.createEntry(newBound, value);
                    this.nodeBound = (this.nodeBound == null) ? newBound : this.nodeBound.encapsulate(newBound);
                    return this;
                } else {
                    split();
                    return insert(newBound, value);
                }
            } else {
                int bestChildIndex = -1;
                double minAreaIncrease = Double.MAX_VALUE;

                for (int i = 0; i < DEFAULT_CHILD_COUNT; i++) {
                    Node child = children[i];
                    if (child != null) {
                        Box originalChildBound = child.nodeBound;
                        Box combinedBound = newBound;
                        if (originalChildBound != null) {
                            combinedBound = originalChildBound.encapsulate(newBound);
                        }
                        double areaIncrease = (combinedBound.xSpan() * combinedBound.zSpan()) -
                                (originalChildBound != null ? originalChildBound.xSpan() * originalChildBound.zSpan() : 0);

                        if (areaIncrease < minAreaIncrease) {
                            minAreaIncrease = areaIncrease;
                            bestChildIndex = i;
                        } else if (bestChildIndex == -1) {
                            bestChildIndex = i;
                        }
                    } else if (bestChildIndex == -1) {
                        bestChildIndex = i;
                    }
                }

                if (bestChildIndex != -1) {
                    if (children[bestChildIndex] == null) {
                        children[bestChildIndex] = new Node(newBound, this.depth + 1, this.maxEntries, this.childCount);
                        children[bestChildIndex].entries[children[bestChildIndex].entryCount++] = BVH2D.this.createEntry(newBound, value);
                    } else {
                        children[bestChildIndex].insert(newBound, value);
                    }
                    this.nodeBound = (this.nodeBound == null) ? newBound : this.nodeBound.encapsulate(newBound);
                }
                return this;
            }
        }

        public void nodeView(Box bound, BiFunction<Box, Box, Boolean> overlapFunction, int maxDepth, BiConsumer<Integer, Box> consumer) {
            if (this.depth > maxDepth) return;
            if (this.nodeBound != null && overlapFunction.apply(this.nodeBound, bound)) {
                consumer.accept(depth, this.nodeBound);
                for (int i = 0; i < entryCount; i++) {
                    if (overlapFunction.apply(entries[i].bound(), bound)) {
                        consumer.accept(depth + 1, entries[i].bound());
                    }
                }
                if (children[0] != null) {
                    for (Node child : children) {
                        if (child != null) {
                            child.nodeView(bound, overlapFunction, maxDepth, consumer);
                        }
                    }
                }
            }
        }

        private void updateBounds() {
            this.nodeBound = null;
            for (int i = 0; i < entryCount; i++) {
                this.nodeBound = (this.nodeBound == null) ? entries[i].bound() : this.nodeBound.encapsulate(entries[i].bound());
            }
            for (Node child : children) {
                if (child != null && child.nodeBound != null) {
                    this.nodeBound = (this.nodeBound == null) ? child.nodeBound : this.nodeBound.encapsulate(child.nodeBound);
                }
            }
        }

        public boolean queryUntil(@Nullable Box queryBound, OverlapFunction overlapFunction, Predicate<Entry<VALUE>> test) {
            if (queryBound == null || overlapFunction.test(this.nodeBound, queryBound)) {
                for (int i = 0; i < entryCount; i++) {
                    if (queryBound == null || overlapFunction.test(entries[i].bound(), queryBound)) {
                        if (test.test(entries[i])) {
                            return true;
                        }
                    }
                }
                if (children[0] != null) {
                    for (Node child : children) {
                        if (child.queryUntil(queryBound, overlapFunction, test)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        public void clear() {
            this.nodeBound = null;
            this.entryCount = 0;
            for (int i = 0; i < DEFAULT_MAX_ENTRIES; i++) {
                this.entries[i] = null;
            }
            for (int i = 0; i < DEFAULT_CHILD_COUNT; i++) {
                if (children[i] != null) {
                    children[i].clear();
                    children[i] = null;
                }
            }
        }

        public boolean isEmpty() {
            return entryCount == 0 && children[0] == null;
        }

        private void split() {
            children[0] = new Node(null, depth + 1, maxEntries, childCount);
            children[1] = new Node(null, depth + 1, maxEntries, childCount);

            if (entryCount == 0) return;

            int splitAxis = 0;

            if (this.nodeBound != null) {
                double xSpan = this.nodeBound.xSpan();
                double zSpan = this.nodeBound.zSpan();

                if (zSpan > xSpan) {
                    splitAxis = 2;
                }
            }

            final int sortAxis = splitAxis;
            Arrays.sort(entries, 0, entryCount, (e1, e2) -> {
                double center1 = (sortAxis == 0) ? e1.bound().center().x() : e1.bound().center().z();
                double center2 = (sortAxis == 0) ? e2.bound().center().x() : e2.bound().center().z();
                return Double.compare(center1, center2);
            });

            int midpoint = entryCount / 2;
            for (int i = 0; i < entryCount; i++) {
                if (i < midpoint) {
                    children[0].insert(entries[i].bound(), entries[i].value());
                } else {
                    children[1].insert(entries[i].bound(), entries[i].value());
                }
            }

            this.entries = new Entry[DEFAULT_MAX_ENTRIES];
            this.entryCount = 0;
            updateBounds();
        }
    }

    public static BVH2D<Box> floodedBVH(long seed, int numberOfBoxes, int size, int minBoxSize, int maxBoxSize, boolean skipChecks) {
        Random random = new Random(seed);
        BVH2D<Box> bvh2D = new BVH2D<>();
        for (int i = 0; i < numberOfBoxes; i++) {
            double minX = random.nextDouble() * size - size / 2.0;
            double minY = random.nextDouble() * size - size / 2.0;
            double boxWidth = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
            double boxHeight = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
            double maxX = minX + boxWidth;
            double maxY = minY + boxHeight;

            SimpleBox2D box = new SimpleBox2D(minX, minY, maxX, maxY);
            if (skipChecks || !bvh2D.hasAny(box)) {
                bvh2D.insert(box, box);
            }
        }

        return bvh2D;
    }
}