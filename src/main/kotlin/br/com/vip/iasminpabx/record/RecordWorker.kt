package br.com.vip.iasminpabx.record

import br.com.vip.iasminpabx.cdr.Cdr
import br.com.vip.iasminpabx.http.IasminClientService
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/22/24
 */
@Service
class RecordWorker(
    private val recordConvertService: RecordConvertService,
    private val iasminClientService: IasminClientService
) {

    private val recordVirtualWorker = Executors.newSingleThreadExecutor(Thread.ofVirtual().factory())

    fun addWork(cdr: Cdr){
        recordVirtualWorker.submit {
            recordConvertService.convertToMp3(cdr)
            iasminClientService.sendRecordToBackend(cdr)
        }
    }
}