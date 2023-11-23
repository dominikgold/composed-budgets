import java.io.FileInputStream
import java.util.Properties

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

    signingConfigs {
        create("release") {}
    }

    setupSigningConfig(signingConfigs = signingConfigs, buildTypes = buildTypes)

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
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
        arg("room.schemaLocation", File(projectDir, "schemas").path)
    }
}

dependencies {
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation(platform("dev.chrisbanes.compose:compose-bom:2023.12.00-alpha02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
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

fun setupSigningConfig(
    signingConfigs: NamedDomainObjectContainer<com.android.build.gradle.internal.dsl.SigningConfig>,
    buildTypes: NamedDomainObjectContainer<com.android.build.gradle.internal.dsl.BuildType>,
) {
    val propsCcRelease = Properties()
    val propFileCcRelease = file("../release_signing.properties")
    if (propFileCcRelease.canRead()) {
        propsCcRelease.load(FileInputStream(propFileCcRelease))
        if (propsCcRelease != null &&
            propsCcRelease.containsKey("releaseKeyStore")
            && propsCcRelease.containsKey("releaseStorePassword")
            && propsCcRelease.containsKey("releaseKeyAlias")
            && propsCcRelease.containsKey("releaseKeyPassword")
        ) {
            signingConfigs.getByName("release").storeFile = propsCcRelease["releaseKeyStore"]?.let { file(it) }
            signingConfigs.getByName("release").storePassword = propsCcRelease["releaseStorePassword"] as String?
            signingConfigs.getByName("release").keyAlias = propsCcRelease["releaseKeyAlias"] as String?
            signingConfigs.getByName("release").keyPassword = propsCcRelease["releaseKeyPassword"] as String?
        } else {
            println("release_signing.properties found but some entries are missing")
            buildTypes.getByName("release").signingConfig = null
        }
    } else {
        println("release_signing.properties not found")
        buildTypes.getByName("release").signingConfig = null
    }
}
