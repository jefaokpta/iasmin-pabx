package br.com.vip.iasminpabx.asterisk.agi.actions

import br.com.vip.iasminpabx.record.RecordService
import org.asteriskjava.fastagi.AgiChannel
import org.asteriskjava.fastagi.AgiRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 09/11/22
 */

@Service
class DialRouteService(private val recordService: RecordService) {

    @Value("\${pabx.trunk}")
    private lateinit var trunk: String

    @Value("\${pabx.tech-prefix}")
    private lateinit var techPrefix: String

    fun dialTrunk(channel: AgiChannel, request: AgiRequest, destination: String, controlNumber: String) {
        val fileName = recordService.recordCall(channel, request)
        channel.setVariable("CDR(callrecord)", fileName)
        val tecnology = "PJSIP"
        channel.dial("$tecnology/$techPrefix$destination@$trunk", 60,
            "Tb(PRE_DIAL_ACTIONS_GS^s^1($controlNumber))" +
                    "U(CALL_RECORDING_GS^$fileName))")
        channel.verbose("${channel.name} >>> DIALSTATUS - ${channel.getVariable("DIALSTATUS")}", 0)
    }

}