plugins {
    alias(libs.plugins.financialassistant.feature.module)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.transsion.financialassistant.admin"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //project dependencies
    implementation(projects.core.presentation)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)


    debugImplementation(libs.ui.tooling)

    implementation(libs.splash.screen)

    //data store
    implementation(libs.datastore.preferences)

    //firestore
    implementation(libs.firebase.firestore)

    //coil
    implementation(libs.coil.compose)
    debugImplementation(libs.androidx.ui.test.manifest)

}