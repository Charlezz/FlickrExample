plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "hello.com.pose.shared.local"
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
    implementation(Dependencies.AndroidX.Paging.PAGING_COMMON_KTX)
    implementation(Dependencies.AndroidX.DATASTORE_PREFERENCES)
    implementation(Dependencies.AndroidX.Room.RUNTIME)
    implementation(Dependencies.AndroidX.Room.KTX)
    implementation(Dependencies.AndroidX.Room.ROOM_PAGING)
    kapt(Dependencies.AndroidX.Room.COMPILER)

    applyHilt()
}