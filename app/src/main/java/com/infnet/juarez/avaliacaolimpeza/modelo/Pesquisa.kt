package com.infnet.juarez.avaliacaolimpeza.modelo

data class Pesquisa(
    var id: String? = null,
    var user: Usuario? = null,
    var estabelecimento: Estabelecimento? = null,
    var perguntas: ArrayList<Pergunta>? = null,
)
