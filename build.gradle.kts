plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.resourceFactoryBukkit)
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.kotlinx.serialization)
}

val supportSpigot = properties["supportSpigot"].toString().toBooleanStrictOrNull()?:false

version = "develop"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

if(supportSpigot) {
    tasks.assemble {
        dependsOn(tasks.reobfJar)
    }
} else {
    paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
}

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle(properties["bukkitVersion"].toString())
}

tasks {
    compileJava {
        options.release = 21
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveClassifier = null
        archiveFileName = "${project.name}.jar"
    }
}

kotlin {
    jvmToolchain(21)
}

bukkitPluginYaml {
    main = properties["main"].toString()
    apiVersion = properties["apiVersion"].toString()
}