package xyz.sachil.essence.model.net

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * 用于自定义Glide配置
 */
@GlideModule
class NetGlideModule : AppGlideModule() {
    companion object {
        private const val TAG = "NetGlideModule"
        private const val TIME_OUT = 30 * 1000L
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val trustManager = IgnoreExpiredCertificateManager()
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .callTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .sslSocketFactory(trustManager.getSSLContext()!!.socketFactory, trustManager)
            .build()
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(httpClient)
        )
    }

    override fun isManifestParsingEnabled(): Boolean = false
}