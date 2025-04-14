       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.IntIntLongLongBox;


       public class IntIntIntLongLongLongBox implements Box {

           private final int minX;
           private final int minY;
           private final int minZ;
           private final long maxX;
           private final long maxY;
           private final long maxZ;

           public IntIntIntLongLongLongBox(int minX, int minY, int minZ, long maxX, long maxY, long maxZ) {
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
               return new IntIntIntLongLongLongBox((int) minX, (int) minY, (int) minZ, (long) maxX, (long) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new IntIntLongLongBox((int) minX, (int) minZ, (long) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new IntIntIntLongLongLongBox((int) minX, (int) minY, (int) minZ, (long) maxX, (long) maxY, (long) maxZ);
           }
       }
