import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "kwon.dae.won.data"
    compileSdk = Project.compileSdk

    defaultConfig {
        minSdk = Project.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "API_KEY",
            "\"${gradleLocalProperties(rootDir).getProperty("api.key")?:"local.properties 에 api.key 를 추가 하세요"}\""
        )

        buildConfigField(
            "String",
            "SECRET_KEY",
            "\"${gradleLocalProperties(rootDir).getProperty("secret.key")?:"local.properties 에 secret.key 를 추가 하세요"}\""
        )
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

    implementation(Dependencies.SquareUp.Retrofit2.CORE)
    implementation(Dependencies.SquareUp.Retrofit2.MOSHI)
    implementation(Dependencies.AndroidX.Room.ROOM_PAGING)

    applyHilt()
    applyRoom()
}
