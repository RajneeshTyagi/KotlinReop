package com.araba.arabacustomer.network

import com.araba.arabacustomer.utilitys.UrlUtility
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by rajneeshkumar on 11/1/17.
 */
class CustomRetrofit {
    companion object {


        private var mHttpLoggingInterceptor: HttpLoggingInterceptor? = null
        private var mOkHttpClient: OkHttpClient? = null
        private var mRetrofit: Retrofit? = null
        private var mNetworkApiIInterface: NetworkApiIInterface? = null

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            if (mHttpLoggingInterceptor == null) {
                mHttpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Timber.tag("OkHttp").d(message)
                        System.out.println(message)
                    }
                })
                //            mHttpLoggingInterceptor = new HttpLoggingInterceptor();
                // set your desired log level
                mHttpLoggingInterceptor!!.setLevel(HttpLoggingInterceptor.Level.BODY)
                // If Release then
                //            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            return mHttpLoggingInterceptor!!
        }

        private fun getOkHttpClient(): OkHttpClient {
            if (mOkHttpClient == null) {
                mOkHttpClient = OkHttpClient.Builder()
                        .addInterceptor(getHttpLoggingInterceptor())
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .build()
            }
            return mOkHttpClient!!
        }

        fun getRetrofitInstance(): Retrofit? {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()


            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                        .baseUrl(UrlUtility.BASE_URL)
                        .client(getOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
            return mRetrofit
        }

        fun getApiInterfaceInstance(): NetworkApiIInterface {
            if (mNetworkApiIInterface == null) {
                mNetworkApiIInterface = getRetrofitInstance()?.create(NetworkApiIInterface::class.java)
            }
            return mNetworkApiIInterface!!
        }
    }
}