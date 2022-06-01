// https://docs.gradle.org/nightly/userguide/migrating_from_groovy_to_kotlin_dsl.html#applying_plugins
// https://blog.csdn.net/stven_king/article/details/118310535
plugins {
    java
    idea
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

allprojects {
    tasks.withType(JavaCompile::class.java) {
        options.encoding = "UTF-8"
    }
}

subprojects {

}

tasks.create("githubdoc") {
    group = "documentation"
    description = "github pages task"
    println("[phase:configuration] compile")
    doFirst {
        println("[phase:execution] compile :doFirst()")
    }
}