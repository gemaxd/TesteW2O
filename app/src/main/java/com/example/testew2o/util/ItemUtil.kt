package com.example.testew2o.util

import com.example.testew2o.model.Item

class ItemUtil {

    val dateUtil = DateUtils()

    /**
     * Cria um corpo de mensagem com base nos atributos de item
     * */
    fun createItemBodyMessageMail(item : Item) : String {
        var message = StringBuilder()
        message.append("Olá, estou te enviando os dados do produto [${item.nomItem}].")
        message.append("\n\n\n")
        message.append("Nome do produto: ${item.nomItem}\n")
        message.append("Categoria: ${item.catItem}\n")
        message.append("Data: ${dateUtil.formatDate(item)}\n")
        message.append("Preço: R$ ${item.valorItem}\n")
        message.append("Descrição: ${item.descItem}\n\n")
        message.append("Em caso de dúvidas me responda por aqui.\n\nObrigado.")

        return message.toString()
    }

    /**
     * Cria um corpo de mensagem com base nos atributos de item
     * */
    fun createItemBodyMessageEvent(item : Item) : String {
        var message = StringBuilder()
        message.append("Evento associado ao produto ${item.nomItem}.")
        message.append("\n\n\n")
        message.append("Nome do produto: ${item.nomItem}\n")
        message.append("Categoria: ${item.catItem}\n")
        message.append("Data: ${dateUtil.formatDate(item)}\n")
        message.append("Preço: R$ ${item.valorItem}\n")
        message.append("Descrição: ${item.descItem}\n\n")
        message.append("Definir próximas tratativas relacionadas a esse produto em breve.")

        return message.toString()
    }
}