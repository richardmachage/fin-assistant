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

    implementation(libs.androidx.compose.material)

    debugImplementation(libs.ui.tooling)

}