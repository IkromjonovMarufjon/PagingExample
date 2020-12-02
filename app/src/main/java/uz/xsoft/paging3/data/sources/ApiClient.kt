package uz.xsoft.paging3.data.sources

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.xsoft.paging3.app.App
import uz.xsoft.paging3.utils.isAvailableNetwork
import uz.xsoft.paging3.utils.timberLog
import java.util.concurrent.TimeUnit

object ApiClient {
    private val cacheSize : Long = 100 * 1024 * 1024 // 100MB
    private val cache = Cache(App.instance.cacheDir, cacheSize)
    internal val MAX_STALE = 60 *60 * 24 * 30 // 30day


    @JvmStatic
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmStatic
    val retrofitNotCache: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClientNotCache())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmStatic
    private fun getHttpClientNotCache(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addLogging(App.instance)
        return httpClient.build()
    }

    @JvmStatic
    private fun getHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addInterceptor(provideOfflineCacheInterceptor())
        httpClient.addLogging(App.instance)
        return httpClient.build()
    }

}

private fun provideOfflineCacheInterceptor() = Interceptor { chain ->
    var request = chain.request()

    if (!App.instance.isAvailableNetwork()) {
        val cacheControl = CacheControl.Builder()
            .maxStale(ApiClient.MAX_STALE, TimeUnit.SECONDS)
            .build()

        request = request.newBuilder()
            .removeHeader("Cache-Control")
            .cacheControl(cacheControl)
            .build()
    }

    chain.proceed(request)
}


fun OkHttpClient.Builder.addLogging(app: Application): OkHttpClient.Builder {
    val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            timberLog(message,"DDD")
        }

    })
    logging.level = HttpLoggingInterceptor.Level.HEADERS
    if (LOGGING){
        addNetworkInterceptor(logging)
        addNetworkInterceptor(ChuckInterceptor(app))
    }
    return this
}