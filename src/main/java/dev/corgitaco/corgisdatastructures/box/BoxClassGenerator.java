package dev.corgitaco.corgisdatastructures.box;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BoxClassGenerator {

    private static final String[] PRIME_PRIMITIVE_TYPES = {
            "byte", "short", "int", "long"
    };

    private static final String[] DECIMAL_PRIMITIVE_TYPES = {
            "float", "double"
    };

    public static void main(String[] args) throws IOException {
        StringBuilder typeMap = new StringBuilder("Map<String, Factory> LOOKUP = Util.make(() -> new HashMap<>(), map -> {\n");

        generateBoxClasses(PRIME_PRIMITIVE_TYPES, typeMap);
        generateBoxClasses(DECIMAL_PRIMITIVE_TYPES, typeMap);
        typeMap.append("});\n");
        String typeMapString = typeMap.toString();
        System.out.println(typeMapString);
    }

    private static void generateBoxClasses(String[] types, StringBuilder typeMap) throws IOException {
        for (String first : types) {
            for (String second : types) {
                for (String third : types) {
                    for (String fourth : types) {
                        for (String fifth : types) {
                            for (String sixth : types) {
                                String className = BoxFactory.getClassName3D(first, second, third, fourth, fifth, sixth);
                                String info = get3DBoxClassString(className, first, second, third, fourth, fifth, sixth);
                                typeMap.append("map.put(\"").append(className).append("\", ").append(className).append("::createStatically").append(");\n");
                                Path path = Path.of("C:\\coding\\personal\\DataStructures\\src\\main\\java\\dev\\corgitaco\\corgisdatastructures\\box\\impl\\_3d\\" + className + ".java");
                                Files.createDirectories(path.getParent());
                                Files.write(path, info.getBytes());
                            }
                        }
                    }
                }
            }
        }

        for (String first : types) {
            for (String second : types) {
                for (String third : types) {
                    for (String fourth : types) {
                        String className = BoxFactory.getClassName2D(first, second, third, fourth);
                        String info = get2DBoxClassString(className, first, second, third, fourth);
                        typeMap.append("map.put(\"").append(className).append("\", ").append(className).append("::createStatically").append(");\n");
                        Path path = Path.of("C:\\coding\\personal\\DataStructures\\src\\main\\java\\dev\\corgitaco\\corgisdatastructures\\box\\impl\\_2d\\" + className + ".java");
                        Files.createDirectories(path.getParent());
                        Files.write(path, info.getBytes());
                    }
                }

            }
        }


    }


    public static String get3DBoxClassString(String className, String minXType, String minYType, String minZType,
                                             String maxXType, String maxYType, String maxZType) {

        String className2D = BoxFactory.getClassName2D(minXType, minZType, maxXType, maxZType);


        return """
                       package dev.corgitaco.corgisdatastructures.box.impl._3d;
                
                       import dev.corgitaco.corgisdatastructures.box.Box;
                       import dev.corgitaco.corgisdatastructures.box.impl._2d.%s;
                
                
                       public class %s implements Box {
                
                           private final %s minX;
                           private final %s minY;
                           private final %s minZ;
                           private final %s maxX;
                           private final %s maxY;
                           private final %s maxZ;
                
                           public %s(%s minX, %s minY, %s minZ, %s maxX, %s maxY, %s maxZ) {
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
                               return new %s((%s) minX, (%s) minY, (%s) minZ, (%s) maxX, (%s) maxY, (%s) maxZ);
                           }
                
                           @Override
                           public Box as2D() {
                               return new %s((%s) minX, (%s) minZ, (%s) maxX, (%s) maxZ);
                           }
                
                           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
                               return new %s((%s) minX, (%s) minY, (%s) minZ, (%s) maxX, (%s) maxY, (%s) maxZ);
                           }
                       }
                """.formatted(className2D,
                className, // Class name
                minXType, minYType, minZType, maxXType, maxYType, maxZType, // Params
                className, minXType, minYType, minZType, maxXType, maxYType, maxZType, // Constructor
                className, minXType, minYType, minZType, maxXType, maxYType, maxZType,// create method
                className2D, minXType, minZType, maxXType, maxZType, // as2D method
                className, minXType, minYType, minZType, maxXType, maxYType, maxZType // static create method
        );
    }

    public static String get2DBoxClassString(String className, String minXType, String minYType,
                                             String maxXType, String maxYType) {

        return """
                       package dev.corgitaco.corgisdatastructures.box.impl._2d;
                
                       import dev.corgitaco.corgisdatastructures.box.Box;
                
                       public class %s implements Box {
                
                           private final %s minX;
                           private final %s minZ;
                           private final %s maxX;
                           private final %s maxZ;
                
                           public %s(%s minX, %s minZ, %s maxX, %s maxZ) {
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
                               return new %s((%s) minX, (%s) minZ, (%s) maxX, (%s) maxZ);
                           }
                
                           @Override
                           public Box as2D() {
                               return this;
                           }
                
                           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
                               return new %s((%s) minX, (%s) minZ, (%s) maxX, (%s) maxZ);
                           }
                       }
                """.formatted(
                className, // Class name
                minXType, minYType, maxXType, maxYType, // Params
                className, minXType, minYType, maxXType, maxYType, // Constructor
                className, minXType, minYType, maxXType, maxYType, // create method
                className, minXType, minYType, maxXType, maxYType // static create method
        );
    }
}