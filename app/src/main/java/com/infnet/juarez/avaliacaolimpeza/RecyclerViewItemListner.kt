package com.infnet.juarez.avaliacaolimpeza

import android.view.View

interface RecyclerViewItemListner {
    fun recyclerViewBotaoAlterarClicked(view: View, id: String)
    fun recyclerViewBotaoExcluirClicked(view: View, id: String): Boolean

    fun recyclerViewRadioButton(view: View, id: Boolean)
}

