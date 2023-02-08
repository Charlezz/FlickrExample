
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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
        classpath(Dependencies.Google.Dagger.HILT_ANDROID_GRADLE_PLUGIN)
    }
}