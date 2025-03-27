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
    implementation(projects.core.data)

    implementation(libs.bundles.default.dependencies)

    // Jetpack Compose BOM (Manages all Compose versions automatically)
    implementation(platform(libs.androidx.compose.bom))

    //splash screen
    implementation(libs.splash.screen)


    //worker
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.work)
    ksp(libs.hilt.ext.compiler)
    testImplementation(libs.work.testing)
    androidTestImplementation(libs.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
}
