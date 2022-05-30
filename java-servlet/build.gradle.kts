version = "1.0.0"

plugins {
    java
    war
}

repositories {
    flatDir { dir("lib") }
    mavenCentral()
}

val moreLibs = configurations.create("moreLibs")

dependencies {
    // https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    // https://mvnrepository.com/artifact/org.apache.tomcat.embed/tomcat-embed-core
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.0-M14")

    // https://mvnrepository.com/artifact/org.apache.tomcat.embed/tomcat-embed-jasper
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:10.1.0-M14")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.war {
    from("src/rootContent") // adds a file-set to the root of the archive
    webInf { from("src/additionalWebInf") } // adds a file-set to the WEB-INF dir.
    classpath(fileTree("additionalLibs")) // adds a file-set to the WEB-INF/lib dir.
    classpath(moreLibs) // adds a configuration to the WEB-INF/lib dir.
    webXml = file("src/main/resources/web.xml") // copies a file to WEB-INF/web.xml
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}