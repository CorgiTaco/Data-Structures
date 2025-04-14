       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.ByteByteLongShortBox;


       public class ByteByteByteLongLongShortBox implements Box {

           private final byte minX;
           private final byte minY;
           private final byte minZ;
           private final long maxX;
           private final long maxY;
           private final short maxZ;

           public ByteByteByteLongLongShortBox(byte minX, byte minY, byte minZ, long maxX, long maxY, short maxZ) {
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
               return new ByteByteByteLongLongShortBox((byte) minX, (byte) minY, (byte) minZ, (long) maxX, (long) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new ByteByteLongShortBox((byte) minX, (byte) minZ, (long) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ByteByteByteLongLongShortBox((byte) minX, (byte) minY, (byte) minZ, (long) maxX, (long) maxY, (short) maxZ);
           }
       }
