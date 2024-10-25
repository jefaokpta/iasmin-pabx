package br.com.vip.iasminpabx.http

import br.com.vip.iasminpabx.cdr.Cdr
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/24/24
 */

@Service
class IasminClientService {

    @Value("\${iasmin.url}")
    private lateinit var IASMIN_BACKEND_URL: String

    private val log = LoggerFactory.getLogger(this::class.java)
    private val CONTENT_TYPE = "Content-Type"
    private val JSON_HEADER = "application/json"
    private val HTTP_REQUEST_TIMEOUT = 8L
    private val HTTP_CONNECTION_TIMEOUT = 4L

    fun sendRecordToBackend(cdr: Cdr){
        val request = HttpRequest.newBuilder(URI("${IASMIN_BACKEND_URL}/cdr"))
            .POST(HttpRequest.BodyPublishers.ofString(jacksonObjectMapper().writeValueAsString(cdr)))
            .header(CONTENT_TYPE, JSON_HEADER)
            .timeout(Duration.ofSeconds(HTTP_REQUEST_TIMEOUT))
            .build()
        try {
            HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(HTTP_CONNECTION_TIMEOUT))
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString())
        } catch (ex: Exception) {
            log.error("🧨: DEU RUIM AO enviar CDR $cdr - ${ex.localizedMessage}")
        }
    }
}