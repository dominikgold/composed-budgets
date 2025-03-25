-keepattributes EnclosingMethod
-keepattributes InnerClasses

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keep class com.dominikgold.composedbudgets.android.**
-keepclassmembers enum com.dominikgold.composedbudgets.** {
    <fields>;
}
-keepclassmembernames enum com.dominikgold.composedbudgets.** {
    <fields>;
}
