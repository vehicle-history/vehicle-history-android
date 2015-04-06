-keepattributes SourceFile,LineNumberTable

# Retrofit, OkHttp, Gson
-keepattributes *Annotation*
-keepattributes Signature
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Joda
-keep class org.joda.time.** { *; }
-keep class org.joda.convert.** { *; }
-keep interface org.joda.time.** { *; }
-keep interface org.joda.convert.** { *; }
-dontwarn org.joda.**

# classes the interact with gson
-keep class io.vehiclehistory.api.** { *; }
