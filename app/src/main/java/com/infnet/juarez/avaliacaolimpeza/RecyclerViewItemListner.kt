package com.infnet.juarez.avaliacaolimpeza

import android.view.View
import com.infnet.juarez.avaliacaolimpeza.modelo.PerguntaResposta

interface RecyclerViewItemListner {
    fun recyclerViewBotaoAlterarClicked(view: View, id: String)
    fun recyclerViewBotaoExcluirClicked(view: View, id: String): Boolean

    fun recyclerViewRadioButton(view: View, perguntaResposta: PerguntaResposta, resposta: Boolean)
}

