       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.LongLongByteShortBox;


       public class LongLongLongByteIntShortBox implements Box {

           private final long minX;
           private final long minY;
           private final long minZ;
           private final byte maxX;
           private final int maxY;
           private final short maxZ;

           public LongLongLongByteIntShortBox(long minX, long minY, long minZ, byte maxX, int maxY, short maxZ) {
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
               return new LongLongLongByteIntShortBox((long) minX, (long) minY, (long) minZ, (byte) maxX, (int) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongLongByteShortBox((long) minX, (long) minZ, (byte) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongLongLongByteIntShortBox((long) minX, (long) minY, (long) minZ, (byte) maxX, (int) maxY, (short) maxZ);
           }
       }
