plugins {
    alias(libs.plugins.neogradle)
    alias(libs.plugins.spotless)
}

val modId = "polyeng"

base.archivesName = modId
version = System.getenv("POLYENG_VERSION") ?: "0.0.0"
group = "gripe.90"

java.toolchain.languageVersion = JavaLanguageVersion.of(17)

repositories {
    mavenLocal()
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

dependencies {
    implementation(libs.neoforge)
    implementation(libs.ae2)
    implementation(libs.polymorph)
}

runs {
    configureEach {
        workingDirectory(file("run"))
        systemProperty("forge.logging.console.level", "info")
        modSource(sourceSets.main.get())
    }

    create("client") {
        modSource(sourceSets.test.get())
    }

    create("server") {
        workingDirectory(file("run/server"))
    }
}

tasks {
    jar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_$modId" }
        }
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    processResources {
        exclude("**/.cache")

        val props = mapOf(
            "version" to version,
            "ae2Version" to libs.versions.ae2.get(),
            "polymorphVersion" to libs.versions.polymorph.get(),
        )

        inputs.properties(props)
        filesMatching("META-INF/mods.toml") {
            expand(props)
        }
    }
}

spotless {
    kotlinGradle {
        target("*.kts")
        diktat()
    }

    java {
        target("/src/**/java/**/*.java")
        endWithNewline()
        indentWithSpaces(4)
        removeUnusedImports()
        palantirJavaFormat()
        toggleOffOn()

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
        target("src/**/resources/**/*.json")
        biome()
        indentWithSpaces(2)
        endWithNewline()
    }
}
