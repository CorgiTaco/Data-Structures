       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.DoubleFloatDoubleFloatBox;


       public class DoubleFloatFloatDoubleDoubleFloatBox implements Box {

           private final double minX;
           private final float minY;
           private final float minZ;
           private final double maxX;
           private final double maxY;
           private final float maxZ;

           public DoubleFloatFloatDoubleDoubleFloatBox(double minX, float minY, float minZ, double maxX, double maxY, float maxZ) {
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
               return new DoubleFloatFloatDoubleDoubleFloatBox((double) minX, (float) minY, (float) minZ, (double) maxX, (double) maxY, (float) maxZ);
           }

           @Override
           public Box as2D() {
               return new DoubleFloatDoubleFloatBox((double) minX, (float) minZ, (double) maxX, (float) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new DoubleFloatFloatDoubleDoubleFloatBox((double) minX, (float) minY, (float) minZ, (double) maxX, (double) maxY, (float) maxZ);
           }
       }
