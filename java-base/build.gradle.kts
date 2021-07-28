plugins {
    java
}

group = "com.maeteno.study.java.base"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // log
    implementation("org.slf4j:slf4j-log4j12:1.7.32")

    // hutool
    implementation("cn.hutool:hutool-all:5.7.5")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}