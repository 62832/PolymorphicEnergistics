pluginManagement {
    plugins {
        id("net.neoforged.moddev") version "0.1.112"
        id("net.neoforged.moddev.repositories") version "0.1.112"
        id("com.diffplug.spotless") version "6.25.0"
    }
}

plugins {
    id("net.neoforged.moddev.repositories")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    rulesMode = RulesMode.FAIL_ON_PROJECT_RULES

    repositories {
        mavenCentral()

        maven {
            name = "ModMaven (K4U-NL)"
            url = uri("https://modmaven.dev/")
            content {
                includeGroup("appeng")
            }
        }

        maven {
            name = "Curse Maven"
            url = uri("https://cursemaven.com")
            content {
                includeGroup("curse.maven")
            }
        }
    }

    versionCatalogs {
        create("libs") {
            version("neoforge", "21.0.13-beta")

            version("ae2", "19.0.5-alpha")
            library("ae2", "appeng", "appliedenergistics2").versionRef("ae2")

            version("polymorph", "1.0.0")
            library("polymorph", "curse.maven", "polymorph-388800").version("5474908-sources-5474909")
        }
    }
}
