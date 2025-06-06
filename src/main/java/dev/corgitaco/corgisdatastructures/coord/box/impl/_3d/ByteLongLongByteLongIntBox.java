       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ByteLongByteIntBox;


       public class ByteLongLongByteLongIntBox implements Box {

           private final byte minX;
           private final long minY;
           private final long minZ;
           private final byte maxX;
           private final long maxY;
           private final int maxZ;

           public ByteLongLongByteLongIntBox(byte minX, long minY, long minZ, byte maxX, long maxY, int maxZ) {
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
               return new ByteLongLongByteLongIntBox((byte) minX, (long) minY, (long) minZ, (byte) maxX, (long) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new ByteLongByteIntBox((byte) minX, (long) minZ, (byte) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ByteLongLongByteLongIntBox((byte) minX, (long) minY, (long) minZ, (byte) maxX, (long) maxY, (int) maxZ);
           }
       }
