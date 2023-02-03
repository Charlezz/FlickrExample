plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "kwon.dae.won.data"
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
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(Dependencies.KwonDaeWon.DOMAIN))
    implementation(Dependencies.AndroidX.CORE)
    implementation(Dependencies.AndroidX.AppCompat.APP_COMPAT)
    testImplementation(Dependencies.Junit.JUNIT)
    androidTestImplementation(Dependencies.AndroidX.Test.Ext.JUNIT)
    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.ESPRESSO_CORE)
}