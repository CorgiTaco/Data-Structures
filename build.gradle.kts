import me.champeau.jmh.JMHTask

plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.2"
}

group = "com.example.examplemod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.jetbrains:annotations:24.0.1")
    implementation("org.ow2.asm:asm:9.7")
    implementation("org.ow2.asm:asm-util:9.7")     // optional: for debugging / printing
    implementation("org.ow2.asm:asm-commons:9.7")  // optional: for tree API & more utilities
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf("-Xmx2G") // Set maximum heap size to 2GB
}

jmh {
    jvmArgs.addAll("-Djmh.ignoreLock=true", "-Xmx2G")
}