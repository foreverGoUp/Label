# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\myASsdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#6.0权限-MPermissions.jar
-dontwarn com.zhy.m.**
-keep class com.zhy.m.** {*;}
-keep interface com.zhy.m.** { *; }
-keep class **$$PermissionProxy { *; }

-keep class com.a.a.a.** { *; }
-keep class com.itsrts.pptviewer.** { *; }
-keep class com.olivephone.** { *; }
-keep class com.olivephone.office.a.b.e.p
-dontwarn com.a.a.a.**
-dontwarn com.itsrts.pptviewer.**
-dontwarn com.olivephone.**
-keepattributes EnclosingMethod

#不混淆输入的类文件
#-dontobfuscate
-keepattributes SourceFile,LineNumberTable

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** {*;}

-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.** {*;}
