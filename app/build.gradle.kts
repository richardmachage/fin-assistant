plugins {
    //alias(libs.plugins.android.application)
    //alias(libs.plugins.kotlin.android)
    alias(libs.plugins.financialassistant.android.application)

    alias(libs.plugins.kotlin.compose)
}

dependencies {
    //feature dependencies
    implementation(projects.feature.onboarding)
    implementation(projects.core.presentation)

    implementation(libs.bundles.default.dependencies)

    // Jetpack Compose BOM (Manages all Compose versions automatically)
    implementation(platform(libs.androidx.compose.bom))

}
