package br.com.vip.iasminpabx.cdr

import br.com.vip.iasminpabx.record.RecordWorker
import org.asteriskjava.manager.event.CdrEvent
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/24/24
 */
@Service
class CdrService(private val recordWorker: RecordWorker) {

    fun saveCdr(cdrEvent: CdrEvent){
        recordWorker.addWork(Cdr(cdrEvent))
    }
}