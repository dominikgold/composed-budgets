plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.dominikgold.composedbudgets"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dominikgold.composedbudgets"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ksp {
        arg("room.generateKotlin", "true")
    }
}

dependencies {
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation(platform("androidx.compose:compose-bom:2023.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    val roomVersion = "2.6.0"
    val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    val roomKtx = "androidx.room:room-ktx:$roomVersion"
    val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    val roomTesting = "androidx.room:room-testing:$roomVersion"
    implementation(roomRuntime)
    implementation(roomKtx)
    ksp(roomCompiler)
    androidTestImplementation(roomTesting)


    testImplementation("junit:junit:4.13.2")

    val androidXTestVersion = "1.3.0"
    val androidXTestCore = "androidx.test:core:$androidXTestVersion"
    val androidXTestRules = "androidx.test:rules:$androidXTestVersion"
    val androidXTestRunner = "androidx.test:runner:$androidXTestVersion"
    androidTestImplementation(androidXTestCore)
    androidTestImplementation(androidXTestRules)
    androidTestImplementation(androidXTestRunner)

    val androidArchCoreVersion = "2.1.0"
    val androidArchCoreTesting = "androidx.arch.core:core-testing:$androidArchCoreVersion"
    androidTestImplementation(androidArchCoreTesting)

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    val mockitoKotlinVersion = "2.2.0"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion"
    testImplementation(mockitoKotlin)

    val kluentVersion = "1.65"
    val kluent = "org.amshove.kluent:kluent:$kluentVersion"
    val kluentAndroid = "org.amshove.kluent:kluent-android:$kluentVersion"
    testImplementation(kluent)
    androidTestImplementation(kluentAndroid)
}
