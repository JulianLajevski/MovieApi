package lt.todo.movieapi.util

import lt.todo.movieapi.util.Constants.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }


}