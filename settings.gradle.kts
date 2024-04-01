pluginManagement {
    repositories {
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://maven.architectury.dev/") }
        maven { url = uri("https://maven.minecraftforge.net/") }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            plugin("archLoom", "dev.architectury.loom").version("1.4-SNAPSHOT")
            plugin("architectury", "architectury-plugin").version("3.4-SNAPSHOT")
            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")
            plugin("spotless", "com.diffplug.spotless").version("6.22.0")

            val minecraftVersion = "1.20.1"
            version("minecraft", minecraftVersion)
            library("minecraft", "com.mojang", "minecraft").versionRef("minecraft")

            library("fabric-loader", "net.fabricmc", "fabric-loader").version("0.14.23")
            library("fabric-api", "net.fabricmc.fabric-api", "fabric-api").version("0.83.1+$minecraftVersion")

            library("forge", "net.minecraftforge", "forge").version("$minecraftVersion-47.1.3")

            version("ae2", "15.0.15")
            library("ae2-fabric", "appeng", "appliedenergistics2-fabric").versionRef("ae2")
            library("ae2-forge", "appeng", "appliedenergistics2-forge").versionRef("ae2")

            version("polymorph", "0.49.1")
            library("polymorph-fabric", "curse.maven", "polymorph-388800").version("4813983-sources")
            library("polymorph-forge", "curse.maven", "polymorph-388800").version("4813985-sources")

            version("kubejs", "2001.6.4-build.95")
            library("kubejs-fabric", "dev.latvian.mods", "kubejs-fabric").versionRef("kubejs")
            library("kubejs-forge", "dev.latvian.mods", "kubejs-forge").versionRef("kubejs")

            // fuck you
            library("mixinextras", "io.github.llamalad7", "mixinextras-forge").version("0.2.0")
            library("nightconfig", "com.electronwill.night-config", "core").version("3.6.4")
            library("cca", "dev.onyxstudios.cardinal-components-api", "cardinal-components-api").version("5.2.1")
        }
    }
}

include("common")

for (platform in providers.gradleProperty("enabledPlatforms").get().split(',')) {
    include(platform)
}

val modName: String by settings
rootProject.name = modName
