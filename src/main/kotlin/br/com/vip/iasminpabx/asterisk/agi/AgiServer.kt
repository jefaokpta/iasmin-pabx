package br.com.vip.iasminpabx.asterisk.agi

import org.asteriskjava.fastagi.DefaultAgiServer
import org.asteriskjava.fastagi.SimpleMappingStrategy
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 22/08/22
 */
@Service
@EnableAsync
class AgiServer(private val routerAgi: RouterAgi) {

    @Async
    fun start() {

        println("INICIANDO AGI SERVER...")
        val server = DefaultAgiServer()
        println("Poolsize ${server.poolSize}") // indicado 1 pra cada ligacao simultanea, padrao 10
        println("Max Poolsize ${server.maximumPoolSize}")

        val agis = mapOf("router" to routerAgi) // mapeamento agi request -> classe
        val agiMapping = SimpleMappingStrategy()
        agiMapping.setMappings(agis)
        server.setMappingStrategy(agiMapping)

        server.startup()
        println("AGI SERVER INICIADO! 🚀")
    }
}