pluginManagement {
    repositories {
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven {
            name = "Maven for PR #1"  // https://github.com/neoforged/ModDevGradle/pull/1
            url = uri("https://prmaven.neoforged.net/ModDevGradle/pr1")
            content {
                includeModule("net.neoforged.moddev", "net.neoforged.moddev.gradle.plugin")
                includeModule("net.neoforged.moddev.junit", "net.neoforged.moddev.junit.gradle.plugin")
                includeModule("net.neoforged", "moddev-gradle")
            }
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            plugin("moddev", "net.neoforged.moddev").version("0.1.50-pr-1-pr-publish")
            plugin("spotless", "com.diffplug.spotless").version("6.23.3")

            version("neoforge", "20.6.91-beta-pr-959-features-gradle-metadata")

            version("ae2", "18.1.3-alpha")
            library("ae2", "appeng", "appliedenergistics2-neoforge").versionRef("ae2")

            version("polymorph", "0.52.1")
            library("polymorph", "curse.maven", "polymorph-388800").version("5393930-sources-5393931")
        }
    }
}

rootProject.name = "PolymorphicEnergistics"
