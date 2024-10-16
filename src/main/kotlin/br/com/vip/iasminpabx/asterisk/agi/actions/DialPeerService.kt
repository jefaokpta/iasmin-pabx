package br.com.vip.iasminpabx.asterisk.agi.actions

import org.asteriskjava.fastagi.AgiChannel
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 26/10/22
 */
@Service
class DialPeerService() {

    fun dialPeer(channel: AgiChannel, destination: String, companyId: String, timeout: Int, options: String) {
        val interfaces = channel.getVariable("PJSIP_DIAL_CONTACTS($destination)")
        if (interfaces.isNullOrBlank()) {
            channel.verbose(">>>> ERRO: Ramal $destination não está registrado!", 0)
            return
        }
        channel.dial(interfaces, timeout, options)
    }
}