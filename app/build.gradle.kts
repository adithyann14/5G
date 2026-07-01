plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace  = "com.example.fiveg"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fiveg"
        minSdk        = 29   // Quick Settings Tile requires API 29
        targetSdk     = 36
        versionCode   = 1
        versionName   = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled   = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // No signingConfig set here on purpose — this produces an
            // unsigned release APK for now. See .github/workflows/build.yml
            // for a ready-to-uncomment signed-release job once you have a
            // keystore to wire up.
        }
        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
}
