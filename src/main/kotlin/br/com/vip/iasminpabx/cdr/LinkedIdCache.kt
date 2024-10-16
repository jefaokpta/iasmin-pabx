package br.com.vip.iasminpabx.cdr

import org.springframework.stereotype.Component

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 29/11/23
 */
@Component
class LinkedIdCache {
    //todo: limpar diariamente
    private val linkedRecords = mutableMapOf<String, MutableList<String>>()

    @Synchronized
    fun add(linkedId: String, record: String) {
        linkedRecords.computeIfAbsent(linkedId) { mutableListOf() }.add(record)
    }

    @Synchronized
    fun remove(linkedId: String) = linkedRecords.remove(linkedId)
}