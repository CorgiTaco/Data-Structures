       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ShortShortByteShortBox;


       public class ShortLongShortByteIntShortBox implements Box {

           private final short minX;
           private final long minY;
           private final short minZ;
           private final byte maxX;
           private final int maxY;
           private final short maxZ;

           public ShortLongShortByteIntShortBox(short minX, long minY, short minZ, byte maxX, int maxY, short maxZ) {
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
               return new ShortLongShortByteIntShortBox((short) minX, (long) minY, (short) minZ, (byte) maxX, (int) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortShortByteShortBox((short) minX, (short) minZ, (byte) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortLongShortByteIntShortBox((short) minX, (long) minY, (short) minZ, (byte) maxX, (int) maxY, (short) maxZ);
           }
       }
