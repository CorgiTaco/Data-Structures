       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.LongByteShortIntBox;


       public class LongLongByteShortIntIntBox implements Box {

           private final long minX;
           private final long minY;
           private final byte minZ;
           private final short maxX;
           private final int maxY;
           private final int maxZ;

           public LongLongByteShortIntIntBox(long minX, long minY, byte minZ, short maxX, int maxY, int maxZ) {
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
               return new LongLongByteShortIntIntBox((long) minX, (long) minY, (byte) minZ, (short) maxX, (int) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongByteShortIntBox((long) minX, (byte) minZ, (short) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongLongByteShortIntIntBox((long) minX, (long) minY, (byte) minZ, (short) maxX, (int) maxY, (int) maxZ);
           }
       }
