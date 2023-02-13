plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.devtools.ksp") version Dependencies.Jetbrains.Kotlin.KSP_VERSION
}

android {
    namespace = "com.sum3years.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "FLICKER_API_KEY",
            "\"${com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("key_flicker")}\""
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(Dependencies.SquareUp.Retrofit2.CORE)
    implementation(Dependencies.SquareUp.OkHttp3.CORE)
    implementation(Dependencies.SquareUp.OkHttp3.LOGGING_INTERCEPTOR)
    implementation(Dependencies.SquareUp.Retrofit2.MOSHI)

    applyHilt()
    applyMoshi()
    applyRoom()

    // Sandwich
    implementation ("com.github.skydoves:sandwich:1.3.3")
}