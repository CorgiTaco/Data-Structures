       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.LongLongLongIntBox;


       public class LongIntLongLongIntIntBox implements Box {

           private final long minX;
           private final int minY;
           private final long minZ;
           private final long maxX;
           private final int maxY;
           private final int maxZ;

           public LongIntLongLongIntIntBox(long minX, int minY, long minZ, long maxX, int maxY, int maxZ) {
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
               return new LongIntLongLongIntIntBox((long) minX, (int) minY, (long) minZ, (long) maxX, (int) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongLongLongIntBox((long) minX, (long) minZ, (long) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongIntLongLongIntIntBox((long) minX, (int) minY, (long) minZ, (long) maxX, (int) maxY, (int) maxZ);
           }
       }
