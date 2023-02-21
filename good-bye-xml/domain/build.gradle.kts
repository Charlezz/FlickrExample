plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    testImplementation(Dependencies.Mockk.MOCKK)
    testImplementation(Dependencies.Jetbrains.KotlinX.COROUTINE_TEST)
    implementation(Dependencies.Jetbrains.KotlinX.COROUTINE_CORE)
    implementation(Dependencies.AndroidX.Paging.PAGING_COMMON)
    implementation("javax.inject:javax.inject:1")
    testImplementation(Dependencies.Kotest.ASSERTION)
    testImplementation(Dependencies.Junit.JUNIT)
}
