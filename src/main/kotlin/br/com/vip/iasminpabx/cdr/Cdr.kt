package br.com.vip.iasminpabx.cdr

import org.asteriskjava.manager.event.CdrEvent
import java.util.*

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/11/23
 */
data class Cdr(
    val peer: String,
    val startTimeAsDate: Date,
    val channel: String,
    val destination: String,
    val destinationContext: String?,
    val duration: Int,
    val userField: String?,
    val callerId: String?,
    val linkedId: String,
    val uniqueId: String?,
    val lastApplication: String?,
    val lastData: String?,
    val src: String,
    val billableSeconds: Int,
    val disposition: String?,
    val destinationChannel: String?,
    val answerTimeAsDate: Date?,
    val endTimeAsDate: Date,
    val accountCode: String,
    val company: Int,
    val dstFinal: String,
    val callRecords: List<String>
) {
    constructor(cdrEvent: CdrEvent, callRecords: List<String> ): this(
        peer = cdrEvent.dynamicProperties["peer"] ?: "",
        startTimeAsDate = cdrEvent.startTimeAsDate,
        channel = cdrEvent.channel,
        destination = cdrEvent.destination,
        destinationContext = cdrEvent.destinationContext,
        duration = cdrEvent.duration,
        userField = cdrEvent.userField,
        callerId = cdrEvent.callerId,
        linkedId = cdrEvent.dynamicProperties["linkedid"] ?: "",
        uniqueId = cdrEvent.uniqueId,
        lastApplication = cdrEvent.lastApplication,
        lastData = cdrEvent.lastData,
        src = cdrEvent.src,
        billableSeconds = cdrEvent.billableSeconds,
        disposition = cdrEvent.disposition,
        destinationChannel = cdrEvent.destinationChannel,
        answerTimeAsDate = cdrEvent.answerTimeAsDate,
        endTimeAsDate = cdrEvent.endTimeAsDate,
        accountCode = cdrEvent.accountCode,
        company = cdrEvent.dynamicProperties["company"]?.toInt() ?: 0,
        dstFinal = cdrEvent.dynamicProperties["dstfinal"] ?: "",
        callRecords = callRecords
    )

}