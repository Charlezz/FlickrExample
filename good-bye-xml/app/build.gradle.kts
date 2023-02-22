plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "good.bye.xml"
    compileSdk = Project.compileSdk

    defaultConfig {
        applicationId = "good.bye.xml"
        minSdk = Project.minSdk
        targetSdk = Project.targetSdk
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.COMPILER_VERSION
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/AL2.0")
            excludes.add("/META-INF/LGPL2.1")
        }
    }
}

dependencies {

    /* Module implementations */
    implementation(project(Dependencies.GoodByeXml.DOMAIN))
    implementation(project(Dependencies.GoodByeXml.DATA))

    /* Implementations */
    implementation(Dependencies.AndroidX.CORE)
    implementation(Dependencies.AndroidX.Lifecycle.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.AndroidX.Lifecycle.LIFECYCLE_VIEWMODEL_COMPOSE)
    implementation(Dependencies.AndroidX.Activity.ACTIVITY_COMPOSE)
    implementation(Dependencies.AndroidX.Compose.UI.UI)
    implementation(Dependencies.AndroidX.Compose.UI.UI_TOOLING_PREVIEW)
    implementation(Dependencies.AndroidX.Compose.Material3.MATERIAL3)
    implementation(Dependencies.Io.Coil.COIL_COMPOSE)
    implementation(Dependencies.AndroidX.Paging.PAGING_COMPOSE)
    implementation(Dependencies.AndroidX.Paging.PAGING_RUNTIME_KTX)

    implementation(Dependencies.SkyDove.Orbital)


    /* Test implementations */
    testImplementation(Dependencies.Junit.JUNIT)
    testImplementation(Dependencies.Mockk.MOCKK_ANDROID)
    testImplementation(Dependencies.Mockk.MOCKK_AGENT)


    /* Android Test implementations */
    androidTestImplementation(Dependencies.AndroidX.Test.Ext.JUNIT)
    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.ESPRESSO_CORE)
    androidTestImplementation(Dependencies.AndroidX.Compose.UI.UI_TEST_JUNIT4)


    /* Debug implementations */
    debugImplementation(Dependencies.AndroidX.Compose.UI.UI_TOOLING)
    debugImplementation(Dependencies.AndroidX.Compose.UI.UI_TEST_MANIFEST)

    applyHilt()
    applyTest()
}