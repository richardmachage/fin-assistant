plugins {
    alias(libs.plugins.financialassistant.feature.module)

}

android {
    namespace = "com.transsion.financialassistant.home"
}

dependencies {
    //project dependencies
    implementation(projects.core.presentation)
    implementation(projects.core.data)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)

    //paging
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.paging.compose)


    debugImplementation(libs.ui.tooling)

    //data store
    implementation(libs.datastore.preferences)

}