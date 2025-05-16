plugins {
    alias(libs.plugins.financialassistant.feature.module)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.transsion.financialassistant.feedback"
}

dependencies {
    //project dependencies
    implementation(projects.core.presentation)
    implementation(projects.core.data)



    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)
    implementation(libs.firebase.firestore)



    debugImplementation(libs.ui.tooling)


    //data store
    implementation(libs.datastore.preferences)

    //cloudinary
    implementation(libs.cloudinary.android)

    //coil
    implementation(libs.coil.compose)

}