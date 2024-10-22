package br.com.vip.iasminpabx.asterisk.ami

import br.com.vip.iasminpabx.record.RecordWorker
import org.asteriskjava.manager.event.HangupEvent
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/22/24
 */
@Service
class HangupService(private val recordWorker: RecordWorker) {
    fun hangup(hangupEvent: HangupEvent) {
        recordWorker.addWork(hangupEvent.uniqueId)
    }
}