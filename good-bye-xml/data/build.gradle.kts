import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("com.google.devtools.ksp") version Dependencies.Jetbrains.Kotlin.KSP_VERSION
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "good.bye.xml.data"
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
    implementation(project(Dependencies.GoodByeXml.DOMAIN))
    implementation(project(Dependencies.GoodByeXml.NETWORK))

    implementation(Dependencies.AndroidX.CORE)
    implementation(Dependencies.AndroidX.AppCompat.APP_COMPAT)

    testImplementation(Dependencies.Junit.JUNIT)

    androidTestImplementation(Dependencies.AndroidX.Test.Ext.JUNIT)
    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.ESPRESSO_CORE)

    implementation(Dependencies.AndroidX.Paging.PAGING_COMPOSE)
    testImplementation(Dependencies.Kotest.ASSERTION)

    applyRoom()
    applyOkHttp3()
    applyMoshi()
    applyHilt()
    applyTest()
}