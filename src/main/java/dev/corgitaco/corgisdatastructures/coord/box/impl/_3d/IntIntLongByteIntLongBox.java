       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.IntLongByteLongBox;


       public class IntIntLongByteIntLongBox implements Box {

           private final int minX;
           private final int minY;
           private final long minZ;
           private final byte maxX;
           private final int maxY;
           private final long maxZ;

           public IntIntLongByteIntLongBox(int minX, int minY, long minZ, byte maxX, int maxY, long maxZ) {
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
               return new IntIntLongByteIntLongBox((int) minX, (int) minY, (long) minZ, (byte) maxX, (int) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new IntLongByteLongBox((int) minX, (long) minZ, (byte) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new IntIntLongByteIntLongBox((int) minX, (int) minY, (long) minZ, (byte) maxX, (int) maxY, (long) maxZ);
           }
       }
