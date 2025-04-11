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
}

tasks.test {
    useJUnitPlatform()
}

jmh {
    jvmArgs.addAll("-Djmh.ignoreLock=true")
    jvmArgs.addAll(listOf("-Djmh.separateClasspathJAR=true"))
}