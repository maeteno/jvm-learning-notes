// https://docs.gradle.org/nightly/userguide/migrating_from_groovy_to_kotlin_dsl.html#applying_plugins
// https://blog.csdn.net/stven_king/article/details/118310535
plugins {
    java
    idea
}

allprojects {

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