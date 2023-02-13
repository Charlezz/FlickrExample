import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.devtools.ksp") version Dependencies.Jetbrains.Kotlin.KSP_VERSION
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "good.bye.xml.network"
    compileSdk = Project.compileSdk

    defaultConfig {
        minSdk = Project.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "FLICKR_API_KEY", getApiKey("FLICKR_API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {

    implementation(Dependencies.SquareUp.Retrofit2.CORE)
    implementation(Dependencies.SquareUp.Retrofit2.MOSHI)
    applyMoshi()
    applyOkHttp3()
    applyHilt()
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}