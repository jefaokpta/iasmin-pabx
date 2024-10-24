package br.com.vip.iasminpabx.cdr

import org.asteriskjava.manager.event.CdrEvent

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/11/23
 */
data class Cdr(
    val peer: String,
    val startTime: String,
    val destination: String,
    val duration: Int,
    val callerId: String?,
    val uniqueId: String?,
    val src: String,
    val billableSeconds: Int,
    val disposition: String?,
    val accountCode: String,
    val company: Int,
    val callRecord: String?
) {
    constructor(cdrEvent: CdrEvent ): this(
        peer = cdrEvent.dynamicProperties["peer"] ?: "",
        startTime = cdrEvent.startTime,
        destination = cdrEvent.destination,
        duration = cdrEvent.duration,
        callerId = cdrEvent.callerId,
        uniqueId = cdrEvent.uniqueId,
        src = cdrEvent.src,
        billableSeconds = cdrEvent.billableSeconds,
        disposition = cdrEvent.disposition,
        accountCode = cdrEvent.accountCode,
        company = cdrEvent.dynamicProperties["company"]?.toInt() ?: 0,
        callRecord = cdrEvent.dynamicProperties["callrecord"]
    )

}