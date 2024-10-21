buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("com.google.gms:google-services:4.3.10")
    }
}

allprojects {
    // Aquí ya no es necesario agregar repositorios porque ya están definidos arriba.
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}

