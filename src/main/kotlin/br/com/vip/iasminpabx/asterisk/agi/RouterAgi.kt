package br.com.vip.iasminpabx.asterisk.agi

import org.asteriskjava.fastagi.AgiChannel
import org.asteriskjava.fastagi.AgiRequest
import org.asteriskjava.fastagi.BaseAgiScript
import org.springframework.stereotype.Service

@Service
class RouterAgi(): BaseAgiScript() {

    override fun service(request: AgiRequest, channel: AgiChannel) {
        val callType = request.getParameter("callType")?: return
        val companyId = request.getParameter("companyId")?: return
        val peer = request.getParameter("peer")?: return

        channel.verbose("callType: $callType, companyId: $companyId", 1)
    }
    /*
         discar agente
         0800 pre pago
         retorno imediato fila
         retorno imediato ura
         pesquisa de satisfação
     */

}
