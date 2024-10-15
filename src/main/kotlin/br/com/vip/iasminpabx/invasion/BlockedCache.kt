package br.com.vip.iasminpabx.invasion

import org.springframework.stereotype.Component

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 7/25/24
 */
@Component
class BlockedCache {

    private val blocked = mutableMapOf<String, Attacker>()

    @Synchronized
    fun add(attacker: Attacker){
        blocked[attacker.ip] = attacker
    }

    fun has(ip: String) = blocked.containsKey(ip)

    fun getBlockedMap() = blocked.toMap()

    @Synchronized
    fun clear() = blocked.clear()

}