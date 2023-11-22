import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.task.RemapJarTask

plugins {
    java
    alias(libs.plugins.architectury)
    alias(libs.plugins.archLoom) apply false
    alias(libs.plugins.shadow)
    alias(libs.plugins.spotless)
}

val modId: String by project
val modVersion = (System.getenv("POLYENG_VERSION") ?: "0.0.0").substringBefore('-')
val minecraftVersion: String = libs.versions.minecraft.get()

val platforms by extra {
    property("enabledPlatforms").toString().split(',')
}

tasks {
    val releaseInfo by registering {
        doLast {
            val outputFile = File(System.getenv("GITHUB_OUTPUT"))
            outputFile.appendText("MOD_VERSION=$modVersion\n")
            outputFile.appendText("MINECRAFT_VERSION=$minecraftVersion\n")
        }
    }

    withType<Jar> {
        enabled = false
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = rootProject.libs.plugins.architectury.get().pluginId)
    apply(plugin = rootProject.libs.plugins.archLoom.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

    base.archivesName.set("$modId-${project.name}")
    version = "$modVersion-$minecraftVersion"
    group = "${property("mavenGroup")}.$modId"

    val javaVersion: String by project

    java {
        sourceCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
        targetCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
    }

    architectury {
        minecraft = minecraftVersion
        injectInjectables = false
    }

    configure<LoomGradleExtensionAPI> {
        silentMojangMappingsLicense()

        @Suppress("UnstableApiUsage")
        mixin.defaultRefmapName.set("$modId.refmap.json")
    }

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
            name = "Architectury"
            url = uri("https://maven.architectury.dev")
            content {
                includeGroup("dev.architectury")
            }
        }

        maven {
            name = "saps.dev"
            url = uri("https://maven.saps.dev/releases")
            content {
                includeGroup("dev.latvian.mods")
            }
        }

        maven {
            name = "Ladysnake Mods"
            url = uri("https://maven.ladysnake.org/releases")
            content {
                includeGroup("dev.onyxstudios.cardinal-components-api")
            }
        }

        maven {
            name = "Modrinth Maven"
            url = uri("https://api.modrinth.com/maven")
            content {
                includeGroup("maven.modrinth")
            }
        }
    }

    dependencies {
        "minecraft"(rootProject.libs.minecraft)
        "mappings"(project.extensions.getByName<LoomGradleExtensionAPI>("loom").officialMojangMappings())
    }

    tasks {
        jar {
            from("LICENSE") {
                rename { "${it}_${base.archivesName}"}
            }
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(javaVersion.toInt())
        }
    }

    spotless {
        java {
            target("src/**/java/**/*.java")
            palantirJavaFormat()
            endWithNewline()
            indentWithSpaces()
            removeUnusedImports()
            toggleOffOn()
            trimTrailingWhitespace()

            // courtesy of diffplug/spotless#240
            // https://github.com/diffplug/spotless/issues/240#issuecomment-385206606
            custom("noWildcardImports") {
                if (it.contains("*;\n")) {
                    throw Error("No wildcard imports allowed")
                }
                it
            }
            bumpThisNumberIfACustomStepChanges(1)
        }

        json {
            target("src/*/resources/**/*.json")
            targetExclude("src/generated/resources/**")
            prettier().config(mapOf("parser" to "json"))
        }
    }
}

for (platform in platforms) {
    project(":$platform") {
        apply(plugin = rootProject.libs.plugins.shadow.get().pluginId)

        architectury {
            platformSetupLoomIde()
            loader(platform)
        }

        val common: Configuration by configurations.creating
        val shadowCommon: Configuration by configurations.creating

        fun capitalise(str: String): String {
            return str.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
        }

        configurations {
            compileClasspath.get().extendsFrom(common)
            runtimeClasspath.get().extendsFrom(common)
            named("development${capitalise(platform)}").get().extendsFrom(common)
        }

        dependencies {
            common(project(path = ":common", configuration = "namedElements")) {
                isTransitive = false
            }

            shadowCommon(project(path = ":common", configuration = "transformProduction${capitalise(platform)}")) {
                isTransitive = false
            }
        }

        tasks {
            processResources {
                val commonProps by extra { mapOf(
                        "version"           to project.version,
                        "minecraftVersion"  to minecraftVersion,
                        "ae2Version"        to rootProject.libs.versions.ae2.get(),
                        "polymorphVersion"  to rootProject.libs.versions.polymorph.get())
                }

                inputs.properties(commonProps)
            }

            shadowJar {
                exclude("architectury.common.json")

                configurations = listOf(shadowCommon)
                archiveClassifier.set("dev-shadow")
            }

            withType<RemapJarTask> {
                inputFile.set(shadowJar.get().archiveFile)
                dependsOn(shadowJar)
                archiveClassifier.set(null as String?)
            }

            jar {
                archiveClassifier.set("dev")
            }
        }

        val javaComponent = components["java"] as AdhocComponentWithVariants
        javaComponent.withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
            skip()
        }
    }
}
