package com.infnet.juarez.avaliacaolimpeza.modelo

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
class Usuario {
    var id: String? = null
    var email: String? = null
    var dataCriacao: Long? = null
    var dataUltimoAcesso: Long? = null


    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    constructor(
        id: String,
        email: String,
        dataCriacao: Long,
        dataUltimoAcesso: Long,
    ){
        this.id = id
        this.email = id
        this.dataCriacao = dataCriacao
        this.dataUltimoAcesso = dataUltimoAcesso
    }

    constructor()

    fun getid(): String? {
        return this.id
    }

    fun getemail(): String?{
        return this.email
    }

    fun getDataCriacao(): String?{
        return dateFormat.format(this.dataCriacao)
    }

    fun getDataUltimoAcesso(): String?{
        return dateFormat.format(this.dataUltimoAcesso)
    }
}
