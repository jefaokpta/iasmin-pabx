package br.com.vip.iasminpabx.asterisk.agi.actions

import org.asteriskjava.fastagi.AgiChannel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 09/11/22
 */

@Service
class DialRouteService() {

    @Value("\${pabx.trunk}")
    private lateinit var trunk: String

    @Value("\${pabx.tech-prefix}")
    private lateinit var techPrefix: String

    fun dialTrunk(channel: AgiChannel, destination: String, controlNumber: String) {
        val tecnology = "PJSIP"
        channel.dial("$tecnology/$techPrefix$destination@$trunk", 60, "Tb(PRE_DIAL_ACTIONS^sipheader^1($controlNumber))")
        channel.verbose("${channel.name} >>> DIALSTATUS - ${channel.getVariable("DIALSTATUS")}", 0)
    }

}