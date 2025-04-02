plugins {
    /*alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)*/
    alias(libs.plugins.financialassistant.feature.module)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.transsion.financialassistant.onboarding"
    /*compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }*/
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

}

dependencies {
    //project dependencies
    implementation(projects.core.presentation)
    implementation(projects.core.data)
    implementation(projects.core.security)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Security
    implementation (libs.androidx.security.crypto)
    implementation(libs.splash.screen)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Biometric
    implementation(libs.androidx.biometric)

    debugImplementation(libs.ui.tooling)

    //data store
    implementation(libs.datastore.preferences)
    debugImplementation(libs.androidx.ui.test.manifest)

    /* testImplementation(projects.core.data)
     testImplementation(projects.core.security)*/
    // testImplementation(libs.datastore.preferences)

}