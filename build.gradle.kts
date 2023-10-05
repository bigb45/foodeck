// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
//    alias(libs.plugins.androidApplication) apply false
//    alias(libs.plugins.kotlinAndroid) apply false
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}
//buildscript {
//    repositories {
//        // other repositories...
//        mavenCentral()
//    }
//    dependencies {
//        // other plugins...
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
//    }
//}
true // Needed to make the Suppress annotation work for the plugins block