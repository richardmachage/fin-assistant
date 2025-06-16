plugins {
    alias(libs.plugins.financialassistant.feature.module)
}

android {
    namespace = "com.transsion.financialassistant.settings"
}

dependencies {

    //project dependencies
    //implementation(projects.core.presentation)
    implementation(projects.core.data)
    implementation(projects.core.presentation)
    implementation(projects.feature.feedback)
    implementation(projects.core.background)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)

    //paging
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.paging.compose)

    debugImplementation(libs.ui.tooling)

    //data store
    implementation(libs.datastore.preferences)

    //worker
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.work)
    implementation(libs.androidx.appcompat)
}