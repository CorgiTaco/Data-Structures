import me.champeau.jmh.JMHTask

plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.2"
    id("maven-publish")
}

group = "dev.corgitaco.corgisdatastructures"
version = "0.0.1"

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
    implementation("it.unimi.dsi:fastutil:8.5.15")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf("-Xmx2G") // Set maximum heap size to 2GB
}

jmh {
    jvmArgs.addAll("-Djmh.ignoreLock=true", "-Xmx2G")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "dev.corgitaco.corgisdatastructures"
            artifactId = "corgisdatastructures"
            version = project.version as String
        }
    }
    repositories {
        mavenLocal()
        maven {
            val releasesRepoUrl = "https://maven.jt-dev.tech/releases"
            val snapshotsRepoUrl = "https://maven.jt-dev.tech/snapshots"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT") || project.version.toString().startsWith("0")) snapshotsRepoUrl else releasesRepoUrl)
            name = "JTDev-Maven-Repository"
            credentials {
                username = project.properties["repoLogin"]?.toString()
                password = project.properties["repoPassword"]?.toString()
            }
        }
    }
}