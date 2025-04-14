       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.ShortByteIntShortBox;


       public class ShortLongByteIntLongShortBox implements Box {

           private final short minX;
           private final long minY;
           private final byte minZ;
           private final int maxX;
           private final long maxY;
           private final short maxZ;

           public ShortLongByteIntLongShortBox(short minX, long minY, byte minZ, int maxX, long maxY, short maxZ) {
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
               return new ShortLongByteIntLongShortBox((short) minX, (long) minY, (byte) minZ, (int) maxX, (long) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortByteIntShortBox((short) minX, (byte) minZ, (int) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortLongByteIntLongShortBox((short) minX, (long) minY, (byte) minZ, (int) maxX, (long) maxY, (short) maxZ);
           }
       }
