package lt.todo.movieapi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import lt.todo.movieapi.BuildConfig
import lt.todo.movieapi.data.network.models.MovieApi
import lt.todo.movieapi.util.AuthInterceptor
import lt.todo.movieapi.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

//    @Singleton
//    @Provides
//    fun provideHttpClient(): OkHttpClient{
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//       return OkHttpClient.Builder()
//               .addInterceptor(AuthInterceptor())
//               .addInterceptor(HttpLoggingInterceptor())
//           .readTimeout(15, TimeUnit.MILLISECONDS)
//           .connectTimeout(15, TimeUnit.MILLISECONDS)
//           .build()
//    }

    @Singleton
    @Provides
    fun provideHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    } else OkHttpClient
        .Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): MovieApi{
        return retrofit.create(MovieApi::class.java)
    }

}