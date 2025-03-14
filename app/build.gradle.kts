plugins {
    alias(libs.plugins.financialassistant.android.application)
    //alias(libs.plugins.ksp) //for hilt
    //alias(libs.plugins.hilt.android)
}

dependencies {
    //feature dependencies
    implementation(projects.feature.onboarding)
    implementation(projects.core.presentation)
    implementation(projects.core.background)

    implementation(libs.bundles.default.dependencies)

    // Jetpack Compose BOM (Manages all Compose versions automatically)
    implementation(platform(libs.androidx.compose.bom))


    //hilt
    //ksp(libs.hilt.compiler)
    //implementation(libs.bundles.hilt)
}
