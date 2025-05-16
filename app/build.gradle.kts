plugins {
    alias(libs.plugins.financialassistant.android.application)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services)
}

dependencies {
    adminImplementation(libs.androidx.appcompat)
    adminImplementation(libs.androidx.work.runtime.ktx)
    adminImplementation(libs.hilt.work)
    //feature default app dependencies
    implementation(projects.feature.insights)
    implementation(projects.feature.home)
    implementation(projects.feature.feedback)
    //defaultImplementation(projects.core.presentation)
    implementation(projects.core.background)

    //both
    implementation(projects.core.data)
    implementation(projects.feature.onboarding)
    implementation(projects.core.presentation)


    //admin app
    adminImplementation(projects.feature.admin)

    implementation(libs.bundles.default.dependencies)

    // Jetpack Compose BOM (Manages all Compose versions automatically)
    implementation(platform(libs.androidx.compose.bom))

    //splash screen
    implementation(libs.splash.screen)

    //worker
    defaultImplementation(libs.androidx.work.ktx)
    defaultImplementation(libs.hilt.work)
    defaultImplementation(libs.androidx.appcompat)

    ksp(libs.hilt.ext.compiler)
    testDefaultImplementation(libs.work.testing)
    androidTestDefaultImplementation(libs.work.testing)
    androidTestDefaultImplementation(libs.hilt.android.testing)
    androidTestDefaultImplementation(libs.junit)
}
