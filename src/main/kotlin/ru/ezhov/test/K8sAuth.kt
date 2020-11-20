package ru.ezhov.test

import java.io.BufferedInputStream
import java.net.URL
import java.security.cert.X509Certificate
import javax.net.ssl.*

fun main() {
    val URL = ""
    val HOST = ""

    val connection = URL(URL).openConnection() as HttpsURLConnection

    trustAllCerts(connection)

    connection.apply {
        addRequestProperty("Host", HOST)
        addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0")
        addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        addRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
        addRequestProperty("Accept-Encoding", "gzip, deflate, br")
        addRequestProperty("DNT", "1")
        addRequestProperty("Connection", "keep-alive")
        addRequestProperty("Referer", "https://kubectl.k8s-dev.gksm.local/")
        addRequestProperty("Upgrade-Insecure-Requests", "1")

        requestMethod = "GET"

        println(responseCode)
        headerFields.forEach {
            println("${it.key} : ${it.value}")
        }

        BufferedInputStream(inputStream).use { stream ->
            println(String(stream.readBytes()))
        }
    }
}

private fun trustAllCerts(connection: HttpsURLConnection) {
    val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate>? = null
            }
    )

    val sc: SSLContext = SSLContext.getInstance("SSL")
    sc.init(null, trustAllCerts, java.security.SecureRandom())

    val allHostsValid = HostnameVerifier { _, _ -> true }

    connection.sslSocketFactory = sc.socketFactory
    connection.hostnameVerifier = allHostsValid
}