       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.FloatDoubleFloatDoubleBox;


       public class FloatDoubleDoubleFloatFloatDoubleBox implements Box {

           private final float minX;
           private final double minY;
           private final double minZ;
           private final float maxX;
           private final float maxY;
           private final double maxZ;

           public FloatDoubleDoubleFloatFloatDoubleBox(float minX, double minY, double minZ, float maxX, float maxY, double maxZ) {
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
               return new FloatDoubleDoubleFloatFloatDoubleBox((float) minX, (double) minY, (double) minZ, (float) maxX, (float) maxY, (double) maxZ);
           }

           @Override
           public Box as2D() {
               return new FloatDoubleFloatDoubleBox((float) minX, (double) minZ, (float) maxX, (double) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new FloatDoubleDoubleFloatFloatDoubleBox((float) minX, (double) minY, (double) minZ, (float) maxX, (float) maxY, (double) maxZ);
           }
       }
