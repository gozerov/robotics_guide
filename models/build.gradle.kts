import java.util.UUID

plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "ru.gozerov.models"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    buildFeatures {
        buildConfig = false
    }
    sourceSets["main"].assets.srcDir("${layout.buildDirectory.asFile.get()}/generated/assets")
}

tasks.register("genUUID") {
    val uuid = UUID.randomUUID().toString()
    val odir = file("${layout.buildDirectory.asFile.get()}/generated/assets/model-ru")
    val ofile = file("$odir/uuid")
    doLast {
        mkdir(odir)
        ofile.appendText(uuid)
    }
}