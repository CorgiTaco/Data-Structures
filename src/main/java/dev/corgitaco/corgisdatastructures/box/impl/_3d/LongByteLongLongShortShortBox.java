       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.LongLongLongShortBox;


       public class LongByteLongLongShortShortBox implements Box {

           private final long minX;
           private final byte minY;
           private final long minZ;
           private final long maxX;
           private final short maxY;
           private final short maxZ;

           public LongByteLongLongShortShortBox(long minX, byte minY, long minZ, long maxX, short maxY, short maxZ) {
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
               return new LongByteLongLongShortShortBox((long) minX, (byte) minY, (long) minZ, (long) maxX, (short) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongLongLongShortBox((long) minX, (long) minZ, (long) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongByteLongLongShortShortBox((long) minX, (byte) minY, (long) minZ, (long) maxX, (short) maxY, (short) maxZ);
           }
       }
