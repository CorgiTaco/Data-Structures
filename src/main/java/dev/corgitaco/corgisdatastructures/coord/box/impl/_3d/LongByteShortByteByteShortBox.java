       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.LongShortByteShortBox;


       public class LongByteShortByteByteShortBox implements Box {

           private final long minX;
           private final byte minY;
           private final short minZ;
           private final byte maxX;
           private final byte maxY;
           private final short maxZ;

           public LongByteShortByteByteShortBox(long minX, byte minY, short minZ, byte maxX, byte maxY, short maxZ) {
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
               return new LongByteShortByteByteShortBox((long) minX, (byte) minY, (short) minZ, (byte) maxX, (byte) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongShortByteShortBox((long) minX, (short) minZ, (byte) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongByteShortByteByteShortBox((long) minX, (byte) minY, (short) minZ, (byte) maxX, (byte) maxY, (short) maxZ);
           }
       }
