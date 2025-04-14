       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.ByteByteByteIntBox;


       public class ByteByteByteByteByteIntBox implements Box {

           private final byte minX;
           private final byte minY;
           private final byte minZ;
           private final byte maxX;
           private final byte maxY;
           private final int maxZ;

           public ByteByteByteByteByteIntBox(byte minX, byte minY, byte minZ, byte maxX, byte maxY, int maxZ) {
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
               return new ByteByteByteByteByteIntBox((byte) minX, (byte) minY, (byte) minZ, (byte) maxX, (byte) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new ByteByteByteIntBox((byte) minX, (byte) minZ, (byte) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ByteByteByteByteByteIntBox((byte) minX, (byte) minY, (byte) minZ, (byte) maxX, (byte) maxY, (int) maxZ);
           }
       }
