plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

android {
    val androidConfig = rootProject.ext["android"] as Map<*, *>
    val compileSdkVersion = androidConfig["compileSdkVersion"].toString().toInt()
    val minSdkVersion = androidConfig["minSdkVersion"].toString().toInt()
    val targetSdkVersion = androidConfig["targetSdkVersion"].toString().toInt()
    val versionCode = androidConfig["versionCode"].toString().toInt()
    val versionName = androidConfig["versionName"].toString()

    signingConfigs {
        create("release") {
            keyAlias = "ktmp"
            keyPassword = "ktmp123456"
            storeFile = file("../ktmp.jks")
            storePassword = "ktmp123456"
        }
    }
    compileSdkVersion(compileSdkVersion)
    defaultConfig {
        applicationId = "com.hazz.kotlinmvp"
        minSdkVersion(minSdkVersion)
        targetSdkVersion(targetSdkVersion)
        multiDexEnabled = true
        this.versionCode = versionCode
        this.versionName = versionName

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath = true
            }
        }
        // 实现毛玻璃那种透明的效果需要添加的库
        renderscriptTargetApi = 19
        // Enable RS support
        renderscriptSupportModeEnabled = true

        ndk {
            //APP的build.gradle设置支持的SO库架构
            abiFilters("armeabi", "armeabi-v7a", "x86")
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isZipAlignEnabled = true
            signingConfig = signingConfigs.getByName("release")
        }

    }
    //    自定义输出配置
    //    android.applicationVariants.all { variant ->
    //        variant.outputs.all {
    //            //            name("kotlinmvp_v${variant.versionName}_${variant.name}.apk")
    //        }
    //        true
    //    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    productFlavors {

    }

    dexOptions {
        jumboMode = true
    }

    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.1") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
    // Support库
    implementation("com.android.support:support-v4:28.0.0")
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support:cardview-v7:28.0.0")
    implementation("com.android.support:design:28.0.0")
    implementation("com.android.support:support-annotations:28.0.0")
    // 网络请求库
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.1")
    // RxJava2
    implementation("io.reactivex.rxjava2:rxjava:2.2.8")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    // APT dependencies(Kotlin内置的注解处理器)
    kapt("com.github.bumptech.glide:compiler:4.9.0")

    // 底部菜单
    implementation("com.flyco.tablayout:FlycoTabLayout_Lib:2.1.0@aar") {
        exclude(group = "com.android.support", module = "support-v4")
    }
    //kotlin 支持库
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.30")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.30")
    //GlideOkHttp
    implementation("com.github.bumptech.glide:okhttp3-integration:4.9.0") {
        exclude(group = "glide-parent")
    }
    //smartRefreshLayout 下拉刷新
    implementation("com.scwang.smartrefresh:SmartRefreshLayout:1.0.3")
    implementation("com.scwang.smartrefresh:SmartRefreshHeader:1.0.3")
    //Banne")
    implementation("cn.bingoogolapple:bga-banner:2.2.4@aar")
    // 视屏播放")
    implementation("com.shuyu:GSYVideoPlayer:2.1.1")
    //Logge")
    implementation("com.orhanobut:logger:2.2.0")
    //Google开源的一个布局控件
    implementation("com.google.android:flexbox:0.3.1")
    implementation(project(":multiple-status-view"))
    //模糊透明 View
    implementation("com.github.mmin18:realtimeblurview:1.1.0")
    //leakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:1.5.4")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-no-op:1.5.4")
    //腾讯 bugly
    implementation("com.tencent.bugly:crashreport:2.6.6.1")
    //运行时权限
    implementation("pub.devrel:easypermissions:1.2.0")
    implementation("com.android.support:multidex:1.0.3")

}
