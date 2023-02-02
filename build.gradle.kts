allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Android.Tools.Build.GRADLE)
        classpath(Dependencies.Jetbrains.Kotlin.GRADLE_PLUGIN)
    }
}