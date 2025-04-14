       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.IntIntIntIntBox;


       public class IntByteIntIntIntIntBox implements Box {

           private final int minX;
           private final byte minY;
           private final int minZ;
           private final int maxX;
           private final int maxY;
           private final int maxZ;

           public IntByteIntIntIntIntBox(int minX, byte minY, int minZ, int maxX, int maxY, int maxZ) {
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
               return new IntByteIntIntIntIntBox((int) minX, (byte) minY, (int) minZ, (int) maxX, (int) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new IntIntIntIntBox((int) minX, (int) minZ, (int) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new IntByteIntIntIntIntBox((int) minX, (byte) minY, (int) minZ, (int) maxX, (int) maxY, (int) maxZ);
           }
       }
