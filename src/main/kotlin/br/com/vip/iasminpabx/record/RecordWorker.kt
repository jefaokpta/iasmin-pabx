package br.com.vip.iasminpabx.record

import org.springframework.stereotype.Service
import java.util.concurrent.Executors

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/22/24
 */
@Service
class RecordWorker(private val recordConvertService: RecordConvertService) {

    private val recordVirtualWorker = Executors.newSingleThreadExecutor(Thread.ofVirtual().factory())

    fun addWork(uniqueId: String){
        recordVirtualWorker.submit { //todo: teste quebrar VT e ver se ela continua recebendo trabalhos
            recordConvertService.convertToMp3(uniqueId)
        }
    }
}