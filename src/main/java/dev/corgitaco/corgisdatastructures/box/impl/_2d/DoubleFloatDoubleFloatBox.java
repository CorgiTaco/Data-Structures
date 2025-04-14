       package dev.corgitaco.corgisdatastructures.box.impl._2d;

       import dev.corgitaco.corgisdatastructures.box.Box;

       public class DoubleFloatDoubleFloatBox implements Box {

           private final double minX;
           private final float minZ;
           private final double maxX;
           private final float maxZ;

           public DoubleFloatDoubleFloatBox(double minX, float minZ, double maxX, float maxZ) {
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
               return new DoubleFloatDoubleFloatBox((double) minX, (float) minZ, (double) maxX, (float) maxZ);
           }

           @Override
           public Box as2D() {
               return this;
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new DoubleFloatDoubleFloatBox((double) minX, (float) minZ, (double) maxX, (float) maxZ);
           }
       }
