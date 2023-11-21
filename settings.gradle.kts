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

            val minecraftVersion = "1.19.2"
            version("minecraft", minecraftVersion)
            library("minecraft", "com.mojang", "minecraft").versionRef("minecraft")

            library("fabric-loader", "net.fabricmc", "fabric-loader").version("0.14.23")
            library("fabric-api", "net.fabricmc.fabric-api", "fabric-api").version("0.76.0+$minecraftVersion")

            library("forge", "net.minecraftforge", "forge").version("$minecraftVersion-43.2.0")

            version("ae2", "12.9.8")
            library("ae2-fabric", "appeng", "appliedenergistics2-fabric").versionRef("ae2")
            library("ae2-forge", "appeng", "appliedenergistics2-forge").versionRef("ae2")

            version("polymorph", "0.46.5")
            library("polymorph-fabric", "maven.modrinth", "polymorph").version("8zhVFKEL")
            library("polymorph-forge", "maven.modrinth", "polymorph").version("pJZr4yc3")

            version("kubejs", "1902.6.2-build.42")
            library("kubejs-fabric", "dev.latvian.mods", "kubejs-fabric").versionRef("kubejs")
            library("kubejs-forge", "dev.latvian.mods", "kubejs-forge").versionRef("kubejs")

            // fuck you
            library("mixinextras", "io.github.llamalad7", "mixinextras-forge").version("0.2.0")
            library("nightconfig", "com.electronwill.night-config", "core").version("3.6.4")
            library("cca", "dev.onyxstudios.cardinal-components-api", "cardinal-components-api").version("5.0.2")
        }
    }
}

include("common")

for (platform in providers.gradleProperty("enabledPlatforms").get().split(',')) {
    include(platform)
}

val modName: String by settings
rootProject.name = modName
