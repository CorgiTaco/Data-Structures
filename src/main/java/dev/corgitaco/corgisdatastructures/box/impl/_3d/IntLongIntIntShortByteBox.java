       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.IntIntIntByteBox;


       public class IntLongIntIntShortByteBox implements Box {

           private final int minX;
           private final long minY;
           private final int minZ;
           private final int maxX;
           private final short maxY;
           private final byte maxZ;

           public IntLongIntIntShortByteBox(int minX, long minY, int minZ, int maxX, short maxY, byte maxZ) {
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
               return new IntLongIntIntShortByteBox((int) minX, (long) minY, (int) minZ, (int) maxX, (short) maxY, (byte) maxZ);
           }

           @Override
           public Box as2D() {
               return new IntIntIntByteBox((int) minX, (int) minZ, (int) maxX, (byte) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new IntLongIntIntShortByteBox((int) minX, (long) minY, (int) minZ, (int) maxX, (short) maxY, (byte) maxZ);
           }
       }
