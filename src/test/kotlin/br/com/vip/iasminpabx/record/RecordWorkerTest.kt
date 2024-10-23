package br.com.vip.iasminpabx.record

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Disabled
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
//        val vt = Executors.newVirtualThreadPerTaskExecutor()

        log.info("Executando tarefas")
        vt.submit {
            log.info("Executando em uma VT")
//            throw RuntimeException("Erro")
            log.info("Executando tarefa demorada")
            TimeUnit.SECONDS.sleep(3)
        }
        vt.submit {
            log.info("Executando em outra VT")
            log.info("Executando em outra VT")
        }
        vt.submit {
            log.info("Executando em outra 3 VT")
            log.info("Executando em outra 3 VT")
        }

        TimeUnit.SECONDS.sleep(5)
    }
}