       package dev.corgitaco.corgisdatastructures.box.impl._2d;

       import dev.corgitaco.corgisdatastructures.box.Box;

       public class DoubleFloatDoubleDoubleBox implements Box {

           private final double minX;
           private final float minZ;
           private final double maxX;
           private final double maxZ;

           public DoubleFloatDoubleDoubleBox(double minX, float minZ, double maxX, double maxZ) {
               this.minX = minX;
               this.minZ = minZ;
               this.maxX = maxX;
               this.maxZ = maxZ;
           }

           @Override
           public double minX() {
               return this.minX;
           }

           @Override
           public double minY() {
               return 0;
           }

           @Override
           public double minZ() {
               return this.minZ;
           }

           @Override
           public double maxY() {
               return 0;
           }

           @Override
           public double maxX() {
               return this.maxX;
           }

           @Override
           public double maxZ() {
               return this.maxZ;
           }

           @Override
           public Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new DoubleFloatDoubleDoubleBox((double) minX, (float) minZ, (double) maxX, (double) maxZ);
           }

           @Override
           public Box as2D() {
               return this;
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new DoubleFloatDoubleDoubleBox((double) minX, (float) minZ, (double) maxX, (double) maxZ);
           }
       }
