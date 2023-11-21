dependencies {
    modImplementation(libs.fabric.loader)
    modApi(libs.fabric.api)

    modImplementation(libs.ae2.fabric)
    modImplementation(libs.polymorph.fabric)

    modRuntimeOnly(libs.kubejs.fabric)

    modRuntimeOnly(libs.cca)
    runtimeOnly(libs.nightconfig)
}

tasks {
    processResources {
        filesMatching("fabric.mod.json") {
            val commonProps: Map<String, *> by extra
            expand(commonProps)
        }
    }
}
