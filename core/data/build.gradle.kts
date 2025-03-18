plugins {
    alias(libs.plugins.financialassistant.core.module)
}

android {
    namespace = "com.transsion.financialassistant.data"
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    //project dependencies
    implementation(projects.core.security)

    //room
    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    //data store
    implementation(libs.datastore.preferences)

    //testing
    testImplementation(libs.junit)

}