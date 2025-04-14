       package dev.corgitaco.corgisdatastructures.box.impl._2d;

       import dev.corgitaco.corgisdatastructures.box.Box;

       public class ShortShortByteByteBox implements Box {

           private final short minX;
           private final short minZ;
           private final byte maxX;
           private final byte maxZ;

           public ShortShortByteByteBox(short minX, short minZ, byte maxX, byte maxZ) {
               this.minX = minX;
               this.minZ = minZ;
               this.maxX = maxX;
               this.maxZ = maxZ;
           }

           @Override
           public double minX() {
               return this.minX;
           }

           @Override
           public double minY() {
               return 0;
           }

           @Override
           public double minZ() {
               return this.minZ;
           }

           @Override
           public double maxY() {
               return 0;
           }

           @Override
           public double maxX() {
               return this.maxX;
           }

           @Override
           public double maxZ() {
               return this.maxZ;
           }

           @Override
           public Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortShortByteByteBox((short) minX, (short) minZ, (byte) maxX, (byte) maxZ);
           }

           @Override
           public Box as2D() {
               return this;
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortShortByteByteBox((short) minX, (short) minZ, (byte) maxX, (byte) maxZ);
           }
       }
