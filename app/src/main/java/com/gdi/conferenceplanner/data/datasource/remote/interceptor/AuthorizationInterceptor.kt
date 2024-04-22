package com.gdi.conferenceplanner.data.datasource.remote.interceptor

import com.gdi.conferenceplanner.util.handlers.DataStoreHandler
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            DataStoreHandler.read()
        }
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}