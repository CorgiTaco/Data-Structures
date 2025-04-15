       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ShortByteByteByteBox;


       public class ShortShortByteByteByteByteBox implements Box {

           private final short minX;
           private final short minY;
           private final byte minZ;
           private final byte maxX;
           private final byte maxY;
           private final byte maxZ;

           public ShortShortByteByteByteByteBox(short minX, short minY, byte minZ, byte maxX, byte maxY, byte maxZ) {
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
               return new ShortShortByteByteByteByteBox((short) minX, (short) minY, (byte) minZ, (byte) maxX, (byte) maxY, (byte) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortByteByteByteBox((short) minX, (byte) minZ, (byte) maxX, (byte) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortShortByteByteByteByteBox((short) minX, (short) minY, (byte) minZ, (byte) maxX, (byte) maxY, (byte) maxZ);
           }
       }
