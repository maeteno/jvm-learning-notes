plugins {
    id("java")
}

group = "com.maeteno"

repositories {
    mavenCentral()
}

dependencies {
    // log
    implementation("org.slf4j:slf4j-log4j12:1.7.32")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}