plugins {
    /*alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)*/
    alias(libs.plugins.financialassistant.feature.module)
}

android {
    namespace = "com.transsion.financialassistant.onboarding"

}

dependencies {
    //project dependencies
    implementation(projects.core.presentation)
    implementation(projects.core.data)
    implementation(projects.core.security)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.biometric)

    debugImplementation(libs.ui.tooling)

    //data store
    implementation(libs.datastore.preferences)

    /* testImplementation(projects.core.data)
     testImplementation(projects.core.security)*/
    // testImplementation(libs.datastore.preferences)

}