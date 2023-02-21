plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.devtools.ksp") version Dependencies.Jetbrains.Kotlin.KSP_VERSION
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "good.bye.xml.local"
    compileSdk = Project.compileSdk

    defaultConfig {
        minSdk = Project.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(Dependencies.AndroidX.Paging.PAGING_COMMON)
    implementation(Dependencies.AndroidX.Room.ROOM_PAGING)

    applyRoom()
    applyHilt()
}