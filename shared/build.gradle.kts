plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.10"
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val ktorVersion = "1.6.8"
        val coroutineVersion = "1.4.2"
        val commonMain by getting{
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation(
                    "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
                )
                implementation(
                    "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
                )
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(
                    "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation(
                    "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
                )
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val androidMain by getting{
            dependencies {
                implementation("androidx.core:core-ktx:1.7.0")
                implementation(
                    "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation ("io.ktor:ktor-client-okhttp:1.5.2")

            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                //implementation ("io.ktor:ktor-client-ios:2.0.0-eap-256")
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 32
    }
}