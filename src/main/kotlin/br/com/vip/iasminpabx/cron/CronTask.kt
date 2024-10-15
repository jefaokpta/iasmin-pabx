package br.com.vip.iasminpabx.cron

import br.com.vip.iasminpabx.invasion.AttackerService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/11/23
 */
@Configuration
@EnableScheduling
class CronTask(
    private val attackerService: AttackerService,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelay = 300_000)
    fun verifyAttackersToBlock() {
        log.info("\uD83D\uDD0E Verificando atacantes para bloquear")
        attackerService.blockedToFile()
    }

    @Scheduled(cron = "0 0 3 * * *")
    fun clearAttackerCache() {
        log.info("\uD83D\uDD0E Limpando caches de atacantes")
        attackerService.clear()
    }
}