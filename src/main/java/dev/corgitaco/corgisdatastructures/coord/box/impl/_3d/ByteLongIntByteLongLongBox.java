       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ByteIntByteLongBox;


       public class ByteLongIntByteLongLongBox implements Box {

           private final byte minX;
           private final long minY;
           private final int minZ;
           private final byte maxX;
           private final long maxY;
           private final long maxZ;

           public ByteLongIntByteLongLongBox(byte minX, long minY, int minZ, byte maxX, long maxY, long maxZ) {
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
               return new ByteLongIntByteLongLongBox((byte) minX, (long) minY, (int) minZ, (byte) maxX, (long) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new ByteIntByteLongBox((byte) minX, (int) minZ, (byte) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ByteLongIntByteLongLongBox((byte) minX, (long) minY, (int) minZ, (byte) maxX, (long) maxY, (long) maxZ);
           }
       }
