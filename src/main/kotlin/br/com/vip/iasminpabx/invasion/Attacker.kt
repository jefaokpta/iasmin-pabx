package br.com.vip.iasminpabx.invasion

import org.asteriskjava.manager.event.InvalidAccountId
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 7/25/24
 */
data class Attacker(
    val ip: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    var attempts: Int = 0
): Serializable {
    constructor(invalidAccountId: InvalidAccountId) : this(
        ip = invalidAccountId.remoteAddress.split("/")[2],
    )
}