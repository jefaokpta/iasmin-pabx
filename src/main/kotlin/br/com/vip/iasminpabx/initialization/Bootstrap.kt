package br.com.vip.iasminpabx.initialization

import br.com.vip.iasminpabx.asterisk.agi.AgiServer
import br.com.vip.iasminpabx.asterisk.ami.AmiCache
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 10/10/23
 */
@Component
class Bootstrap(
    private val agiServer: AgiServer,
    private val amiCache: AmiCache,
) {

    @EventListener(ApplicationReadyEvent::class)
    fun run() {

        amiCache.amiInitialize()
        agiServer.start()

        println("CURRENT SIZE OF HEAP: ${Runtime.getRuntime().totalMemory() / 1024 / 1024} MB")
        println("MAX SIZE OF HEAP: ${Runtime.getRuntime().maxMemory() / 1024 / 1024} MB")
        // Get amount of free memory within the heap in bytes. This size will increase
        // after garbage collection and decrease as new objects are created.
        println("FREE SIZE OF HEAP: ${Runtime.getRuntime().freeMemory() / 1024 / 1024} MB")
    }
}