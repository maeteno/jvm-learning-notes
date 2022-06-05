plugins {
    java
}

group = "com.maeteno.study.java.concurrent"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // log
    implementation("org.slf4j:slf4j-log4j12:1.7.32")

    // hutool
    implementation("cn.hutool:hutool-all:5.7.5")

    // https://mvnrepository.com/artifact/org.redisson/redisson
    implementation("org.redisson:redisson:3.17.0")

    // https://mvnrepository.com/artifact/io.reactivex.rxjava3/rxjava
    implementation("io.reactivex.rxjava3:rxjava:3.1.4")

    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:31.1-jre")

    // lombok
    implementation("org.projectlombok:lombok:1.18.20")
    
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}