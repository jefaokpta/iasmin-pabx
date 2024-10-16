package br.com.vip.iasminpabx.bridge

import br.com.vip.iasminpabx.asterisk.ami.AmiCache
import br.com.vip.iasminpabx.cdr.LinkedIdCache
import org.asteriskjava.manager.action.MixMonitorAction
import org.asteriskjava.manager.event.BridgeEnterEvent
import org.asteriskjava.manager.event.BridgeEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Date

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 01/12/23
 */
@Service
class BridgeService(
    private val bridgeCache: BridgeCache,
    private val linkedIdCache: LinkedIdCache
) {

    @Value("\${audios.records}")
    private lateinit var RECORD_FOLDER: String

    fun add(bridgeEnterEvent: BridgeEnterEvent) {
        println("BRIDGE CACHE: $bridgeEnterEvent") //todo: remover
        bridgeCache.add(bridgeEnterEvent)
    }

    fun get(channel: String) = bridgeCache.get(channel)

    fun remove(channel: String) = bridgeCache.remove(channel)

    fun recordCall(bridgeEvent: BridgeEvent, amiCache: AmiCache) {
        if (bridgeEvent.bridgeState != "Link") return
        if (bridgeEvent.channel2.contains("TRANSFERING")) return
        println("Iniciando Gravacao: $bridgeEvent") //todo: remover
        val peer1 = bridgeCache.get(bridgeEvent.channel1) ?: throw RuntimeException("🧨 Não foi possível encontrar channel1 em BridgeCache")
        val fileName = buildRecordFilename(peer1)
        println("Gravando em: $fileName") //todo: remover
        amiCache.sendActionAsync(MixMonitorAction(bridgeEvent.channel2, fileName))
        linkedIdCache.add(peer1.linkedId, fileName.split("/").last())
    }

    private fun buildRecordFilename(peer: BridgeEnterEvent): String {
        val localDateTime: LocalDateTime =
            peer.dateReceived.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
        val recordFileName: StringBuilder = StringBuilder(RECORD_FOLDER)
        recordFileName.append(localDateTime.year)
        recordFileName.append(addZero(localDateTime.monthValue))
        recordFileName.append(addZero(localDateTime.dayOfMonth))
        recordFileName.append("_")
        recordFileName.append(addZero(localDateTime.hour))
        recordFileName.append(addZero(localDateTime.minute))
        recordFileName.append(addZero(localDateTime.second))
        recordFileName.append("_")
        recordFileName.append(peer.callerIdNum)
        recordFileName.append("_")
        recordFileName.append(peer.connectedLineNum)
        recordFileName.append("_")
        recordFileName.append(peer.linkedId.replace(".", "-"))
        recordFileName.append("_")
        recordFileName.append(Date().time)
        recordFileName.append(".wav")

        return recordFileName.toString()
    }

    private fun addZero(value: Int): String {
        return if (value < 10) "0$value" else value.toString()
    }
}