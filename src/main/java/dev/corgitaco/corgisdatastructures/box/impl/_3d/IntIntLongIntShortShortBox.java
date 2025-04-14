       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.IntLongIntShortBox;


       public class IntIntLongIntShortShortBox implements Box {

           private final int minX;
           private final int minY;
           private final long minZ;
           private final int maxX;
           private final short maxY;
           private final short maxZ;

           public IntIntLongIntShortShortBox(int minX, int minY, long minZ, int maxX, short maxY, short maxZ) {
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
               return new IntIntLongIntShortShortBox((int) minX, (int) minY, (long) minZ, (int) maxX, (short) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new IntLongIntShortBox((int) minX, (long) minZ, (int) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new IntIntLongIntShortShortBox((int) minX, (int) minY, (long) minZ, (int) maxX, (short) maxY, (short) maxZ);
           }
       }
