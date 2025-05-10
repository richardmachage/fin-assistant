plugins {
    alias(libs.plugins.financialassistant.feature.module)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.transsion.financialassistant.admin"
}

dependencies {

    //project dependencies
    implementation(projects.core.presentation)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)


    debugImplementation(libs.ui.tooling)


    //data store
    implementation(libs.datastore.preferences)

    //firestore
    implementation(libs.firebase.firestore)

    //coil
    implementation(libs.coil.compose)

}