plugins {
    alias(libs.plugins.financialassistant.android.application)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services)
}


dependencies {
    //feature dependencies
    implementation(projects.feature.onboarding)
    implementation(projects.feature.insights)
    implementation(projects.feature.home)
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
    implementation(libs.androidx.appcompat)
    ksp(libs.hilt.ext.compiler)
    testImplementation(libs.work.testing)
    androidTestImplementation(libs.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
}
