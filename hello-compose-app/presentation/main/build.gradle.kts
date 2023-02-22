plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "hello.com.pose.presentation.main"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.COMPILER_VERSION
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":hello-compose-app:ui:image"))
    implementation(project(":hello-compose-app:ui:system"))
    implementation(project(":hello-compose-app:shared:domain"))
    implementation(project(":hello-compose-app:shared:core-mvi"))
    implementation(Dependencies.Google.Accompanist.NAVIGATION_ANIMATION)
    implementation(Dependencies.AndroidX.Lifecycle.LIFECYCLE_VIEWMODEL_COMPOSE)
    implementation(Dependencies.AndroidX.Compose.UI.UI_TOOLING_PREVIEW)
    implementation(Dependencies.AndroidX.Compose.Material3.MATERIAL3)
    implementation(Dependencies.AndroidX.Paging.PAGING_COMPOSE)
    implementation(Dependencies.AndroidX.Hilt.Navigation.COMPOSE)
    applyHilt()
}