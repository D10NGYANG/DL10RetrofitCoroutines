# DL10RetrofitCoroutines
Retrofit2+Moshi+Kotlin
[![](https://jitpack.io/v/D10NGYANG/DL10RetrofitCoroutines.svg)](https://jitpack.io/#D10NGYANG/DL10RetrofitCoroutines)

# 使用方法
1. Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency:
```gradle
dependencies {
        implementation 'com.github.D10NGYANG:DL10RetrofitCoroutines:1.2'
}
```
3. 在Retrofit中使用
```kotlin
// 初始化网络请求工具
retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        // 增加回复处理器
        .addCallAdapterFactory(DLResponseAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
```
4. 在接口中使用
```kotlin
interface MainApi {

    @GET("searchUser")
    suspend fun searchUser(
        @Query("username") phone: String
    ): DLResponse<SearchUserResult>
}
```
5. 网络请求
```kotlin
GlobalScope.launch {
    val result = mainApi.searchUser("1234567")
    // 请求结果
    result.body
    // 请求结果码，请求发不出去，值为-1
    request.code
    // 请求错误信息
    request.errorBody
    // 如果request.code==-1表示请求出错
    Log.e("结果", result.toString())
}
```
# 混淆规则
```kotlin
-keep class com.dlong.dl10retrofitcoroutineslib.** {*;}
-dontwarn com.dlong.dl10retrofitcoroutineslib.**
```
