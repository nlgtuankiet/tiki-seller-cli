package tiki.seller.cli

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val okHttpClient: OkHttpClient by lazy {
  val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
      println(message)
    }
  }.apply {

  }).apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
  }
  OkHttpClient.Builder()
    .addNetworkInterceptor(TokenInterceptor)
    .addNetworkInterceptor(logging)
    .build()
}

internal val gson: Gson by lazy {
  GsonBuilder()
    .setPrettyPrinting()
    .create()
}

internal object TokenInterceptor : Interceptor {
  var token: String? = null
  override fun intercept(chain: Interceptor.Chain): Response {
    return if (token == null) {
       chain.proceed(chain.request())
    } else {
      chain.proceed(
        chain.request().newBuilder()
          .addHeader("Authorization", "Bearer $token")
          .build()
      )
    }
  }

}

internal val sellerService: SellerCenterService by lazy {
  Retrofit.Builder()
    .baseUrl("https://sandbox-sellercenter.tiki.vn/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
    .create(SellerCenterService::class.java)
}

internal val tikiService: TikiService by lazy {
  Retrofit.Builder()
    .baseUrl("https://api.tala.xyz/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
    .create(TikiService::class.java)
}

internal val realTimeService: RealTimeService by lazy {
  Retrofit.Builder()
    .baseUrl("https://streaming-realtime.tala.xyz/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
    .create(RealTimeService::class.java)
}

