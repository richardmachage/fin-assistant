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
    //room
    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    //data store
    implementation(libs.datastore.preferences)

}