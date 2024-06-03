pluginManagement {
    repositories {
        maven { url = uri("https://maven.neoforged.net/") }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            plugin("neogradle", "net.neoforged.gradle.userdev").version("7.0.97")
            plugin("spotless", "com.diffplug.spotless").version("6.23.3")

            library("neoforge", "net.neoforged", "neoforge").version("20.4.209")

            version("ae2", "17.12.1-beta")
            library("ae2", "appeng", "appliedenergistics2-neoforge").versionRef("ae2")

            version("polymorph", "0.51.0")
            library("polymorph", "curse.maven", "polymorph-388800").version("5378177-sources-5378178")
        }
    }
}

rootProject.name = "PolymorphicEnergistics"
