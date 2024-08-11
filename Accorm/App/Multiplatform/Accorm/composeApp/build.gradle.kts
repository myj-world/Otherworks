import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.0.0"

    id("com.google.firebase.crashlytics")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.google.firebase.analytics)
            implementation(libs.firebase.crashlytics)

            implementation(libs.bouquet)

//            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.navigation.compose)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.tab.navigator)

            implementation(libs.play.services.base)
            implementation (libs.grpc.okhttp)
            implementation(libs.composeIcons.fontAwesome)

            implementation(libs.kamel.image)

            implementation(libs.kotlinx.serialization.json.v171)

//            implementation(libs.coil.compose.v300alpha06)
//            implementation(libs.coil.network.ktor.v300alpha06)

            implementation(libs.ktor.client.android)

//            implementation(libs.ktor.client.core)
//            implementation(libs.coil.compose.core)
//            implementation(libs.coil.compose)
//            implementation(libs.coil.mp)
//            implementation(libs.kotlinx.coroutines.android)
//            implementation(libs.kotlinx.coroutines.core)
//            implementation(libs.coil.network.ktor)
//
//            implementation(libs.connectivity.core)
//            implementation(libs.connectivity.http)
//            implementation(libs.connectivity.compose)
//            implementation(libs.connectivity.compose.http)
//
//            implementation(libs.resources)
//            implementation(libs.resources.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation(libs.pdfbox)
        }
    }
}

android {
    namespace = "com.yousufjamil.accorm"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.yousufjamil.accorm"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
dependencies {
    implementation(libs.play.services.measurement.api)
    implementation(libs.kamel.image)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Accorm"
            packageVersion = "1.0.0"
            description = "Accorm Desktop App"
            copyright = "Copyright © 2024 Accorm"
            windows {
                iconFile.set(project.projectDir.resolve("src/commonMain/composeResources/drawable/ic.ico"))
            }
            macOS {
                iconFile.set(project.projectDir.resolve("src/commonMain/composeResources/drawable/ic.icns"))
            }
            linux {
                iconFile.set(project.projectDir.resolve("src/commonMain/composeResources/drawable/ic.png"))
            }
        }
    }
}
