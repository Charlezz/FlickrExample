import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.project

private const val debugApi = "debugApi"
private const val debugImplementation = "debugImplementation"
private const val implementation = "implementation"
private const val testImplementation = "testImplementation"
private const val androidTestImplementation = "androidTestImplementation"
private const val ksp = "ksp"
private const val kapt = "kapt"
private const val compileOnly = "compileOnly"

object Dependencies {
    object KwonDaeWon{
        const val DOMAIN = ":kwon-dae-won:domain"
        const val DATA = ":kwon-dae-won:data"
    }

    object JavaX {
        object Inject{
            const val JAVAX_INJECT = "javax.inject:javax.inject:1"
        }

    }

    object Mockito {
        const val VERSION = "4.3.1"
        const val MOCKITO_CORE = "org.mockito:mockito-core:$VERSION"
        const val MOCKITO_INLINE = "org.mockito:mockito-inline:$VERSION"
    }

    object Junit {
        const val VERSION = "4.13.2"
        const val JUNIT = "junit:junit:$VERSION"
    }

    object Jetbrains {
        object Kotlin {
            const val VERSION = "1.7.20"
            const val KSP_VERSION = "1.7.20-1.0.8"
            const val GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
            const val STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib:$VERSION"
            const val STD_LIB_7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$VERSION"
            const val STD_LIB_8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$VERSION"
            const val REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$VERSION"
            const val SERIALIZATION = "org.jetbrains.kotlin:kotlin-serialization:$VERSION"
        }

        object KotlinX {
            const val VERSION = "1.6.2"
            const val COROUTINE_ANDROID =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION"
            const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION"
            const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"
            const val COROUTINES_RX2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$VERSION"
            const val SERIALIZATION_JSON =
                "org.jetbrains.kotlinx:kotlinx-serialization-json:$VERSION"
        }
    }

    object Google {

        object Code {
            object Gson {
                const val VERSION = "2.10"
                const val GSON = "com.google.code.gson:gson:$VERSION"
            }
        }

        /**
         * Jetpack Compose를 위한 유틸
         * https://github.com/google/accompanist
         */
        object Accompanist {
            const val VERSION = "0.27.0"
            const val INSETS = "com.google.accompanist:accompanist-insets:$VERSION"
            const val SYSTEM_UI_CONTROLLER =
                "com.google.accompanist:accompanist-systemuicontroller:$VERSION"
            const val FLOW_LAYOUT = "com.google.accompanist:accompanist-flowlayout:$VERSION"
            const val PAGER = "com.google.accompanist:accompanist-pager:$VERSION"
            const val SWIPE_REFRESH = "com.google.accompanist:accompanist-swiperefresh:$VERSION"
        }


        object Dagger {
            const val VERSION = "2.44"
            const val HILT_ANDROID_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$VERSION"
            const val HILT_ANDROID = "com.google.dagger:hilt-android:$VERSION"
            const val HILT_COMPILER = "com.google.dagger:hilt-compiler:$VERSION"
        }
    }

    object Android {
        object Tools{
            object Build {
                const val GRADLE = "com.android.tools.build:gradle:7.4.1"
            }
        }
    }

    object AndroidX {

        const val CORE = "androidx.core:core-ktx:1.8.0"
        const val DATASTORE_PREFERENCES = "androidx.datastore:datastore-preferences:1.0.0"
        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:1.0.0-beta02"

        object AppCompat {
            const val VERSION = "1.6.0-beta01"
            const val APP_COMPAT = "androidx.appcompat:appcompat:$VERSION"
        }

        object Paging {
            const val VERSION = "3.2.0-alpha01"
            const val PAGING_RUNTIME_KTX = "androidx.paging:paging-runtime-ktx:$VERSION"
            const val PAGING_COMMON = "androidx.paging:paging-common:$VERSION"
            const val PAGING_COMMON_KTX = "androidx.paging:paging-common-ktx:$VERSION"
            const val PAGING_COMPOSE = "androidx.paging:paging-compose:1.0.0-alpha17"
        }

        object Activity {
            // https://developer.android.com/jetpack/androidx/releases/activity
            const val VERSION = "1.6.0"
            const val ACTIVITY_KTX = "androidx.activity:activity-ktx:$VERSION"

            // Integration with activities
            const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$VERSION"
        }

        object Lifecycle {
            const val VERSION = "2.5.1"
            const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
            const val VIEWMODEL_SAVEDSTATE =
                "androidx.lifecycle:lifecycle-viewmodel-savedstate:$VERSION"
            const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
            const val PROCESS = "androidx.lifecycle:lifecycle-process:$VERSION"
            const val COMMON_JAVA8 = "androidx.lifecycle:lifecycle-common-java8:$VERSION"

            // https://androidx.tech/artifacts/lifecycle/lifecycle-viewmodel-compose
            // Integration with ViewModels
            const val LIFECYCLE_VIEWMODEL_COMPOSE =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$VERSION"
            const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        }

        object ConstraintLayout {
            // https://developer.android.com/jetpack/androidx/releases/constraintlayout
            const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
            const val CONSTRAINT_LAYOUT_COMPOSE =
                "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        }

        object Compose {
            // https://developer.android.com/jetpack/androidx/releases/compose#declaring_dependencies
            const val VERSION = "1.3.2"
            const val COMPILER_VERSION = "1.3.2"

            object Foundation {
                // https://androidx.tech/artifacts/compose.foundation/*
                // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
                const val FOUNDATION = "androidx.compose.foundation:foundation:$VERSION"
                const val FOUNDATION_LAYOUT =
                    "androidx.compose.foundation:foundation-layout:$VERSION"
            }

            object UI {
                const val UI = "androidx.compose.ui:ui:$VERSION"
                const val UI_UTIL = "androidx.compose.ui:ui-util:$VERSION"

                // Tooling support (Previews, etc.)
                const val UI_TOOLING = "androidx.compose.ui:ui-tooling:$VERSION"
                const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$VERSION"
                const val UI_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4:$VERSION"
                const val UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:$VERSION"
            }

            object Material3 {
                const val VERSION = "1.0.0-alpha13"
                const val MATERIAL3 = "androidx.compose.material3:material3:$VERSION"
                const val MATERIAL3_WINDOW_SIZE_CLASS =
                    "androidx.compose.material3:material3-window-size-class:$VERSION"
            }

            object Runtime {
                // https://androidx.tech/artifacts/compose.runtime/runtime/
                const val RUNTIME = "androidx.compose.runtime:runtime:$VERSION"

                // Integration with observables
                const val LIVEDATA = "androidx.compose.runtime:runtime-livedata:$VERSION"
                const val RUNTIME_RXJAVA2 = "androidx.compose.runtime:runtime-rxjava2:$VERSION"
            }
        }

        object CustomView {
            // https://developer.android.com/jetpack/androidx/releases/customview
            const val CUSTOM_VIEW = "androidx.customview:customview:1.2.0-alpha01"
            const val CUSTOM_VIEW_POOLING_CONTAINER =
                "androidx.customview:customview-poolingcontainer:1.0.0-alpha01"
        }

        object Test {
            object Ext {
                const val VERSION = "1.1.5"
                const val JUNIT = "androidx.test.ext:junit:$VERSION"
                const val JUNIT_KTX = "androidx.test.ext:junit-ktx:$VERSION"
            }

            object Espresso {
                const val VERSION = "3.5.1"
                const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:$VERSION"
            }

            const val CORE_KTX = "androidx.test:core-ktx:1.7.0"
            const val CORE_TESTING = "androidx.arch.core:core-testing:2.1.0"
        }


        object Navigation {
            const val VERSION = "2.4.2"
            const val NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$VERSION"
            const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:$VERSION"
            const val FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$VERSION"
            const val UI = "androidx.navigation:navigation-ui-ktx:$VERSION"
            const val DYNAMIC_FEATURE_FRAGMENT =
                "androidx.navigation:navigation-dynamic-features-fragment:$VERSION"
        }

        object Room {
            const val VERSION = "2.4.3"
            const val RUNTIME = "androidx.room:room-runtime:$VERSION"
            const val COMPILER = "androidx.room:room-compiler:$VERSION"
            const val KTX = "androidx.room:room-ktx:$VERSION"
            const val ROOM_TESTING = "androidx.room:room-testing:$VERSION"
            const val ROOM_PAGING = "androidx.room:room-paging:$VERSION"
        }
    }

    object SquareUp {
        object OkHttp3 {
            const val VERSION = "4.9.3"
            const val CORE = "com.squareup.okhttp3:okhttp:$VERSION"
            const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$VERSION"
        }

        object Retrofit2 {
            const val VERSION = "2.9.0"
            const val CORE = "com.squareup.retrofit2:retrofit:$VERSION"
            const val GSON = "com.squareup.retrofit2:converter-gson:$VERSION"
            const val MOSHI = "com.squareup.retrofit2:converter-moshi:$VERSION"
            const val RXJAVA2 = "com.squareup.retrofit2:adapter-rxjava2:2.8.1"
        }

        object Moshi {
            const val VERSION = "1.13.0"
            const val MOSHI = "com.squareup.moshi:moshi:$VERSION"
            const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:$VERSION"
            const val MOSHI_ADAPTERS = "com.squareup.moshi:moshi-adapters:$VERSION"
            const val MOSHI_KOTLIN_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:$VERSION"
        }

        object LeakCanary {
            const val VERSION = "2.8.1"
            const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:$VERSION"
        }

    }


    object JakeWharton {
        const val TIMBER = "com.jakewharton.timber:timber:5.0.1"
    }


    object Io {
        object Coil {
            // https://coil-kt.github.io/coil/getting_started/#artifacts
            const val VERSION = "2.2.2"
            const val COIL = "io.coil-kt:coil:$VERSION"
            const val COIL_BASE = "io.coil-kt:coil-base:$VERSION"
            const val COIL_COMPOSE = "io.coil-kt:coil-compose:$VERSION"
            const val COIL_COMPOSE_BASE = "io.coil-kt:coil-compose-base:$VERSION"
            const val COIL_GIF = "io.coil-kt:coil-gif:$VERSION"
            const val COIL_SVG = "io.coil-kt:coil-svg:$VERSION"
            const val COIL_VIDEO = "io.coil-kt:coil-video:$VERSION"
        }
    }

    object Orbit {
        const val VERSION = "4.5.0"
        const val CORE = "org.orbit-mvi:orbit-core:$VERSION"
        const val VIEWMODEL = "org.orbit-mvi:orbit-viewmodel:$VERSION"
        const val COMPOSE = "org.orbit-mvi:orbit-compose:$VERSION"
        const val TEST = "org.orbit-mvi:orbit-test:$VERSION"
    }

}


fun DependencyHandlerScope.applyCompose() {
    implementation(Dependencies.AndroidX.Compose.Runtime.RUNTIME)
    implementation(Dependencies.AndroidX.Compose.UI.UI)
    implementation(Dependencies.AndroidX.Compose.UI.UI_UTIL)
    implementation(Dependencies.AndroidX.Compose.Foundation.FOUNDATION)
    implementation(Dependencies.AndroidX.Compose.Foundation.FOUNDATION_LAYOUT)
    implementation(Dependencies.AndroidX.Compose.Material3.MATERIAL3)
    implementation(Dependencies.AndroidX.Compose.Material3.MATERIAL3_WINDOW_SIZE_CLASS)
    implementation(Dependencies.AndroidX.Compose.Runtime.LIVEDATA)
    implementation(Dependencies.AndroidX.Compose.UI.UI_TOOLING)
    implementation(Dependencies.AndroidX.Paging.PAGING_COMPOSE)
    implementation(Dependencies.AndroidX.ConstraintLayout.CONSTRAINT_LAYOUT_COMPOSE)
    implementation(Dependencies.AndroidX.Navigation.NAVIGATION_COMPOSE)
    implementation(Dependencies.AndroidX.Lifecycle.LIFECYCLE_VIEWMODEL_COMPOSE)
    implementation(Dependencies.AndroidX.Activity.ACTIVITY_COMPOSE)
    androidTestImplementation(Dependencies.AndroidX.Compose.UI.UI_TEST_JUNIT4)
    debugImplementation(Dependencies.AndroidX.Compose.UI.UI_TEST_MANIFEST)
    implementation(Dependencies.Google.Accompanist.INSETS)
    implementation(Dependencies.Google.Accompanist.SYSTEM_UI_CONTROLLER)
    implementation(Dependencies.Google.Accompanist.PAGER)
    implementation(Dependencies.Google.Accompanist.SWIPE_REFRESH)
    implementation(Dependencies.Google.Accompanist.FLOW_LAYOUT)
    debugApi(Dependencies.AndroidX.CustomView.CUSTOM_VIEW)
    debugApi(Dependencies.AndroidX.CustomView.CUSTOM_VIEW_POOLING_CONTAINER)
}


fun DependencyHandlerScope.applyTest() {

    testImplementation(Dependencies.Junit.JUNIT)
    androidTestImplementation(Dependencies.AndroidX.Test.Ext.JUNIT_KTX)

    testImplementation(Dependencies.Jetbrains.KotlinX.COROUTINE_TEST)
    androidTestImplementation(Dependencies.Jetbrains.KotlinX.COROUTINE_TEST)

    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.ESPRESSO_CORE)

    androidTestImplementation(Dependencies.AndroidX.Test.CORE_TESTING)
}

fun DependencyHandlerScope.applyOkHttp3() {
    implementation(Dependencies.SquareUp.OkHttp3.CORE)
    implementation(Dependencies.SquareUp.OkHttp3.LOGGING_INTERCEPTOR)
}

fun DependencyHandlerScope.applyRoom() {
    ksp(Dependencies.AndroidX.Room.COMPILER)
    implementation(Dependencies.AndroidX.Room.RUNTIME)
    implementation(Dependencies.AndroidX.Room.KTX)
    implementation(Dependencies.AndroidX.Room.ROOM_TESTING)
}

fun DependencyHandlerScope.applyLifecycle() {
    implementation(Dependencies.AndroidX.Lifecycle.VIEWMODEL)
    implementation(Dependencies.AndroidX.Lifecycle.VIEWMODEL_SAVEDSTATE)
    implementation(Dependencies.AndroidX.Lifecycle.COMMON_JAVA8)
    implementation(Dependencies.AndroidX.Lifecycle.PROCESS)
    implementation(Dependencies.AndroidX.Lifecycle.RUNTIME)
}

fun DependencyHandlerScope.applyNavigation() {
    implementation(Dependencies.AndroidX.Navigation.FRAGMENT)
    implementation(Dependencies.AndroidX.Navigation.UI)
    implementation(Dependencies.AndroidX.Navigation.DYNAMIC_FEATURE_FRAGMENT)
}

fun DependencyHandlerScope.applyAndroidX() {
    implementation(Dependencies.AndroidX.AppCompat.APP_COMPAT)
    implementation(Dependencies.AndroidX.CORE)
    implementation(Dependencies.AndroidX.Activity.ACTIVITY_KTX)
}

fun DependencyHandlerScope.applyHilt() {
    implementation(Dependencies.Google.Dagger.HILT_ANDROID)
    kapt(Dependencies.Google.Dagger.HILT_COMPILER)
}

fun DependencyHandlerScope.applyMoshi() {
    implementation(Dependencies.SquareUp.Moshi.MOSHI)
    implementation(Dependencies.SquareUp.Moshi.MOSHI_KOTLIN)
    implementation(Dependencies.SquareUp.Moshi.MOSHI_ADAPTERS)
    ksp(Dependencies.SquareUp.Moshi.MOSHI_KOTLIN_CODEGEN)
}

fun DependencyHandlerScope.applyOrbit() {
    implementation(Dependencies.Orbit.CORE)
    implementation(Dependencies.Orbit.VIEWMODEL)
    implementation(Dependencies.Orbit.COMPOSE)
    testImplementation(Dependencies.Orbit.TEST)
}