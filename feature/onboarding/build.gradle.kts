plugins {

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

    debugImplementation(libs.ui.tooling)

    //data store
    implementation(libs.datastore.preferences)

}