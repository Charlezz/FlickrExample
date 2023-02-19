plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Dependencies.Jetbrains.KotlinX.COROUTINE_CORE)
    implementation(Dependencies.AndroidX.Paging.PAGING_COMMON_KTX)
    implementation("javax.inject:javax.inject:1")
}