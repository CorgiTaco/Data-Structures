       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.DoubleFloatDoubleDoubleBox;


       public class DoubleFloatFloatDoubleFloatDoubleBox implements Box {

           private final double minX;
           private final float minY;
           private final float minZ;
           private final double maxX;
           private final float maxY;
           private final double maxZ;

           public DoubleFloatFloatDoubleFloatDoubleBox(double minX, float minY, float minZ, double maxX, float maxY, double maxZ) {
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
               return new DoubleFloatFloatDoubleFloatDoubleBox((double) minX, (float) minY, (float) minZ, (double) maxX, (float) maxY, (double) maxZ);
           }

           @Override
           public Box as2D() {
               return new DoubleFloatDoubleDoubleBox((double) minX, (float) minZ, (double) maxX, (double) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new DoubleFloatFloatDoubleFloatDoubleBox((double) minX, (float) minY, (float) minZ, (double) maxX, (float) maxY, (double) maxZ);
           }
       }
