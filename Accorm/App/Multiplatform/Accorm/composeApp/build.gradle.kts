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

    id("dev.hydraulic.conveyor") version "1.10"
}

group = "accorm"
version = "2.0.2"

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

            implementation(libs.gson)

            implementation(libs.ktor.client.android)
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
        minSdk = 21
        targetSdk = 34
        versionCode = 17
        versionName = "2.0.2"
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
            packageVersion = "2.0.2"
            description = "Accorm Desktop App"
            copyright = "Copyright © 2023-2024 Accorm"
            windows {
                iconFile.set(project.projectDir.resolve("src/commonMain/composeResources/drawable/ic_win.ico"))
            }
            macOS {
                iconFile.set(project.projectDir.resolve("src/commonMain/composeResources/drawable/ic_mac.icns"))
            }
            linux {
                iconFile.set(project.projectDir.resolve("src/commonMain/composeResources/drawable/ic.png"))
            }
        }
    }
}
