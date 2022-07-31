package com.avisual.spaceapp.buildsrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.2"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.42.0"
    const val playServicesLocation = "com.google.android.gms:play-services-location:17.1.0"
    const val okhttp3MockServer = "com.squareup.okhttp3:mockwebserver:4.9.0"
    const val dexter = "com.karumi:dexter:6.0.2"

    object Kotlin {
        private const val version = "1.6.0"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"

        object Coroutines {
            private const val version = "1.6.0"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidDx {

        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val material = "com.google.android.material:material:1.3.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"

        object Lifecycle {
            private const val version = "2.3.1"
            private const val versionExt = "2.2.0"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$versionExt"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val livedataKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Activity {
            private const val version = "1.2.2"
            const val ktx = "androidx.activity:activity-ktx:$version"
        }

        object Fragment {
            private const val version = "1.3.2"
            const val ktx = "androidx.fragment:fragment-ktx:$version"
        }

        object Navigation {
            private const val version = "2.4.1"
            const val gradlePlugin =
                "androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
        }

        object Room {
            private const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Test {
            private const val version = "1.4.0"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"
            const val coreTesting = "androidx.arch.core:core-testing:2.1.0"

            object Ext {
                private const val version = "1.1.3"
                const val junitKtx = "androidx.test.ext:junit-ktx:$version"
                const val junit = "androidx.test.ext:junit:$version"

            }
            object Espresso{
                private const val version="3.4.0"
                const val contrib = "androidx.test.espresso:espresso-contrib:$version"
                const val core = "androidx.test.espresso:espresso-core:$version"
            }
        }

    }

    object Glide {
        private const val version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
        const val logginInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object Mockito {
        const val kotlin = "org.mockito.kotlin:mockito-kotlin:4.0.0"
        const val inline = "org.mockito:mockito-inline:4.4.0"
        const val ioMock = "io.mockk:mockk:1.12.2"
    }

    object Koin {
        private const val version = "3.1.5"
        const val android = "io.insert-koin:koin-android:$version"
        const val test = "io.insert-koin:koin-test:$version"
    }
}