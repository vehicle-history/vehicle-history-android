-keepattributes SourceFile,LineNumberTable

# Retrofit, OkHttp, Gson
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# RxJava rules
# RxAndroid will soon ship with rules so this may not be needed in the future
# https://github.com/ReactiveX/RxAndroid/issues/219
-dontwarn sun.misc.Unsafe
-dontwarn rx.**
-keep class rx.internal.util.** { *; }
-keep class rx.internal.util.atomic.** { *; }
-keep class rx.Subscription
-keep class rx.Subscriber

-dontwarn retrofit2.**
-keep class okio.** { *; }
-keep class retrofit2.** { *; }

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
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
-keep class com.google.gson.** { *; }
-keep class io.vehiclehistory.api.model.** { *; }

-keepclassmembers class io.vehiclehistory.data.api.auth.AuthApiService$** {
    !static !private <fields>;
}
-keepclassmembers class io.vehiclehistory.data.api.VehicleHistoryApiInterface$** {
    !static !private <fields>;
}