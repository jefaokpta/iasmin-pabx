package br.com.vip.iasminpabx.record

import org.asteriskjava.fastagi.AgiChannel
import org.asteriskjava.fastagi.AgiRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 01/12/23
 */
@Service
class RecordService() {

    fun recordCall(channel: AgiChannel, request: AgiRequest): String {
        return buildRecordFilename(request)
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