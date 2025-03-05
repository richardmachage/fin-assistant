import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.transsion.financialassistant.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin{
    compilerOptions{
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies{
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin{
    plugins{
        register("androidApplication"){
            id = "financialassistant.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("featureModule"){
            id = "financialassistant.feature.module"
            implementationClass = "FeatureModuleConventionPlugin"
        }

        register("coreModule"){
            id = "financialassistant.core.module"
            implementationClass = "CoreModuleConventionPlugin"
        }
    }

}