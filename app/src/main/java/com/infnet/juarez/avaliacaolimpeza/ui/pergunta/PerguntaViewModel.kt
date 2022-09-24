package com.infnet.juarez.avaliacaolimpeza.ui.pergunta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PerguntaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pergunta Fragment"
    }
    val text: LiveData<String> = _text
}