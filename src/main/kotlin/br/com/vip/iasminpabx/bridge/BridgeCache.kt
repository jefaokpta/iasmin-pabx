package br.com.vip.iasminpabx.bridge

import org.asteriskjava.manager.event.BridgeEnterEvent
import org.springframework.stereotype.Component

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 01/12/23
 */
@Component
class BridgeCache {
    // todo: limpar diariamente
    private val bridges = mutableMapOf<String, BridgeEnterEvent>()

    @Synchronized
    fun add(bridgeEnterEvent: BridgeEnterEvent) {
        bridges[bridgeEnterEvent.channel] = bridgeEnterEvent
    }

    fun get(channel: String) = bridges[channel]

    @Synchronized
    fun remove(channel: String) = bridges.remove(channel)
}