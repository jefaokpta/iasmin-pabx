package br.com.vip.iasminpabx.record

import br.com.vip.iasminpabx.asterisk.ami.AmiCache
import org.asteriskjava.fastagi.AgiChannel
import org.asteriskjava.fastagi.AgiRequest
import org.asteriskjava.manager.action.MixMonitorAction
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 01/12/23
 */
@Service
class RecordService(private val amiCache: AmiCache) {

    @Value("\${audios.records}")
    private lateinit var RECORD_FOLDER: String

    fun recordCall(channel: AgiChannel, request: AgiRequest): String {
        val fileName = buildRecordFilename(request)
        val recordFilePath = RECORD_FOLDER.plus("/").plus(fileName)
        println("Gravando em: $fileName") //todo: remover
        amiCache.sendActionAsync(MixMonitorAction(channel.name, recordFilePath.plus(".wav"),
            "br(${recordFilePath.plus("_a.wav")})t(${recordFilePath.plus("_b.wav")})"))
        return fileName
    }

    private fun buildRecordFilename(request: AgiRequest): String {
        val localDateTime = LocalDateTime.now()
        val recordFileName: StringBuilder = StringBuilder()
        recordFileName.append(addZero(localDateTime.year))
        recordFileName.append(addZero(localDateTime.monthValue))
        recordFileName.append(addZero(localDateTime.dayOfMonth))
        recordFileName.append("_")
        recordFileName.append(addZero(localDateTime.hour))
        recordFileName.append(addZero(localDateTime.minute))
        recordFileName.append(addZero(localDateTime.second))
        recordFileName.append("_")
        recordFileName.append(request.callerIdNumber)
        recordFileName.append("_")
        recordFileName.append(request.extension)
        recordFileName.append("_")
        recordFileName.append(request.uniqueId.replace(".", "-"))

        return recordFileName.toString()
    }

    private fun addZero(value: Int): String {
        return if (value < 10) "0$value" else value.toString()
    }
}