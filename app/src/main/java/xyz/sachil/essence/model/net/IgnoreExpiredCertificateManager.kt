package xyz.sachil.essence.model.net

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * 由于gank.io的证书不稳定，很容易过期，为了继续访问证书已经过期的gank.io，
 * 这里做了一个非常规的操作：绕过证书检查，这是一个危险的操作，正常情况下绝不能这样使用
 */
class IgnoreExpiredCertificateManager : X509TrustManager {

    fun getSSLContext(): SSLContext? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext!!.init(null, arrayOf(this), SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

        return sslContext
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }
}