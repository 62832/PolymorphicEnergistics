loom {
    forge {
        mixinConfig("${property("modId")}.mixins.json")
    }
}

dependencies {
    forge(libs.forge)

    modImplementation(libs.ae2.forge)
    modImplementation(libs.polymorph.forge)

    modRuntimeOnly(libs.kubejs.forge)
    runtimeOnly(libs.mixinextras)
}

tasks.processResources {
    filesMatching("META-INF/mods.toml") {
        val commonProps: Map<String, *> by extra
        expand(commonProps)
    }
}
