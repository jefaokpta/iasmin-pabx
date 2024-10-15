package br.com.vip.iasminpabx.invasion

import org.springframework.stereotype.Component

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 7/25/24
 */
@Component
class AttackerCache {

    private val attackers = mutableMapOf<String, Attacker>()

    @Synchronized
    fun add(attacker: Attacker){
        attackers.getOrPut(attacker.ip){ attacker }.apply { attempts++ }
    }

    @Synchronized
    fun clear() = attackers.clear()

    @Synchronized
    fun remove(ip: String) = attackers.remove(ip)

    fun get(ip: String) = attackers[ip]

}