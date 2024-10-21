package br.com.vip.iasminpabx.asterisk.agi

import br.com.vip.iasminpabx.asterisk.agi.actions.DialPeerService
import br.com.vip.iasminpabx.asterisk.agi.actions.DialRouteService
import org.asteriskjava.fastagi.AgiChannel
import org.asteriskjava.fastagi.AgiRequest
import org.asteriskjava.fastagi.BaseAgiScript
import org.springframework.stereotype.Service

@Service
class RouterAgi(
    private val dialRouteService: DialRouteService,
    private val dialPeerService: DialPeerService
): BaseAgiScript() {

    override fun service(request: AgiRequest, channel: AgiChannel) {
        val callType = request.getParameter("callType")?: return
        val controlNumber = request.getParameter("companyId")?: return

        if (request.extension.length > 7){
            dialRouteService.dialTrunk(channel, request, request.extension, controlNumber)
            return
        }
        dialPeerService.dialPeer(channel, "careca", controlNumber, 60, "T")
    }

}
