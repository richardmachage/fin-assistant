import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("dagger.hilt.android.plugin")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")


            extensions.configure<ApplicationExtension>{
                namespace = "com.transsion.financialassistant"
                compileSdk = 35

                defaultConfig {
                    applicationId = "com.transsion.financialassistant"
                    minSdk = 26
                    targetSdk = 35
                    versionCode = 1
                    versionName = "1.0.0-demo"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {

                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                flavorDimensions += "appType"
                productFlavors {
                    create("default") {
                        dimension = "appType"
                    }

                    create("admin") {
                        dimension = "appType"
                        applicationIdSuffix = ".admin"
                        versionNameSuffix = "-admin"
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }


                buildFeatures {
                    compose = true
                }


            }

            dependencies{

                add("ksp", versionCatalogLibs.findLibrary("hilt-compiler").get())
                add("implementation", versionCatalogLibs.findBundle("hilt").get())
                add(
                    "implementation",
                    versionCatalogLibs.findLibrary("kotlinx-serialization-core").get()
                )
            }

            extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension> {
                jvmToolchain(17)
            }
        }
    }

}