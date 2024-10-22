package br.com.vip.iasminpabx.record

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/22/24
 */
@Disabled
class RecordWorkerTest {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Test
    fun runInOneVT() {
        val vt = Executors.newSingleThreadExecutor(Thread.ofVirtual().factory())

        vt.submit {
            log.info("Executando em uma VT")
            throw RuntimeException("Erro")
            log.info("Executando em uma VT")
        }
        vt.submit {
            log.info("Executando em outra VT")
            log.info("Executando em outra VT")
        }


    }
}