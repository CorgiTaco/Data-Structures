       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.LongShortIntIntBox;


       public class LongShortShortIntShortIntBox implements Box {

           private final long minX;
           private final short minY;
           private final short minZ;
           private final int maxX;
           private final short maxY;
           private final int maxZ;

           public LongShortShortIntShortIntBox(long minX, short minY, short minZ, int maxX, short maxY, int maxZ) {
               this.minX = minX;
               this.minY = minY;
               this.minZ = minZ;
               this.maxX = maxX;
               this.maxY = maxY;
               this.maxZ = maxZ;
           }

           @Override
           public double minX() {
               return this.minX;
           }

           @Override
           public double minY() {
               return this.minY;
           }

           @Override
           public double minZ() {
               return this.minZ;
           }

           @Override
           public double maxX() {
               return this.maxX;
           }

           @Override
           public double maxY() {
               return this.maxY;
           }

           @Override
           public double maxZ() {
               return this.maxZ;
           }

           @Override
           public Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongShortShortIntShortIntBox((long) minX, (short) minY, (short) minZ, (int) maxX, (short) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongShortIntIntBox((long) minX, (short) minZ, (int) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongShortShortIntShortIntBox((long) minX, (short) minY, (short) minZ, (int) maxX, (short) maxY, (int) maxZ);
           }
       }
