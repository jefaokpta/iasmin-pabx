package br.com.vip.iasminpabx.invasion

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.*
import java.time.LocalDateTime
import java.util.*

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 7/25/24
 */
@Service
class AttackerService(
    private val attackerCache: AttackerCache,
    private val blockedCache: BlockedCache
) {

    @Value("\${asterisk.files}")
    private lateinit var ASTERISK_FOLDER: String

    private val BLOCKED_FILE = "blocked-ips.serialized"
    private val BLOCKED_FILE_JSON = "blocked-ips.json"

    private val log = LoggerFactory.getLogger(javaClass)

    fun attackReport(attacker: Attacker){
        Optional.ofNullable(attackerCache.get(attacker.ip))
            .ifPresentOrElse(::blockOrAddIp) { attackerCache.add(attacker) }
    }

    fun blockedToFile(){
        if (File(BLOCKED_FILE).exists()) {
            val fis = FileInputStream(BLOCKED_FILE)
            val ois = ObjectInputStream(fis)
            val blockedFileList: List<String> = ois.readObject() as List<String>
            if (blockedFileList == blockedCache.getBlockedMap().keys.toList()) {
                log.info("Nenhum IP novo para bloquear, não vou salvar")
                return
            }
        }
        log.info("Bloqueando novos IPs")
        val fos = FileOutputStream(BLOCKED_FILE)
        val oos = ObjectOutputStream(fos)
        val blockedList = blockedCache.getBlockedMap().keys.toList()
        oos.writeObject(blockedList)
        oos.close()
        fos.close()
        log.info("IPs bloqueados $blockedList")
        File("$ASTERISK_FOLDER/$BLOCKED_FILE_JSON").writeText(jacksonObjectMapper().writeValueAsString(blockedList))
    }

    fun clear() {
        attackerCache.clear()
        blockedCache.clear()
    }

    private fun blockOrAddIp(attacker: Attacker){
        if (attacker.attempts > 5) {
            if (!blockedCache.has(attacker.ip) && LocalDateTime.now().isBefore(attacker.timestamp.plusMinutes(1))) {
                blockedCache.add(attacker).run { log.info("\uD83D\uDEAB Bloqueando $attacker") }
            } else attackerCache.remove(attacker.ip)
            return
        }
        attackerCache.add(attacker)
    }

}