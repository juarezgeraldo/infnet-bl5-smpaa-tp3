package com.infnet.juarez.avaliacaolimpeza.ui.estatistica

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EstatisticaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is estatistica Fragment"
    }
    val text: LiveData<String> = _text
}