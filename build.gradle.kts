plugins {
    java
    kotlin("jvm") version "1.5.21"
}

group = "com.maeteno.study"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // kotlin 支持
    implementation(kotlin("stdlib"))

    // log
    implementation("org.slf4j:slf4j-log4j12:1.7.32")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    // unit test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}