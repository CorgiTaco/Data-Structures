       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ShortIntByteShortBox;


       public class ShortIntIntByteShortShortBox implements Box {

           private final short minX;
           private final int minY;
           private final int minZ;
           private final byte maxX;
           private final short maxY;
           private final short maxZ;

           public ShortIntIntByteShortShortBox(short minX, int minY, int minZ, byte maxX, short maxY, short maxZ) {
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
               return new ShortIntIntByteShortShortBox((short) minX, (int) minY, (int) minZ, (byte) maxX, (short) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortIntByteShortBox((short) minX, (int) minZ, (byte) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortIntIntByteShortShortBox((short) minX, (int) minY, (int) minZ, (byte) maxX, (short) maxY, (short) maxZ);
           }
       }
