plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("com.google.devtools.ksp") version Dependencies.Jetbrains.Kotlin.KSP_VERSION
}

android {
    namespace = "hello.com.pose.shared.remote"
    compileSdk = Project.compileSdk

    defaultConfig {
        minSdk = Project.minSdk
        targetSdk = Project.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/AL2.0")
            excludes.add("/META-INF/LGPL2.1")
        }
    }
}

dependencies {
    implementation(project(":hello-compose-app:shared:data"))
    implementation(Dependencies.SquareUp.Retrofit2.CORE)
    implementation(Dependencies.SquareUp.OkHttp3.CORE)
    implementation(Dependencies.SquareUp.OkHttp3.LOGGING_INTERCEPTOR)
    implementation(Dependencies.SquareUp.Retrofit2.MOSHI)
    applyMoshi()
    applyHilt()
}