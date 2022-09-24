package com.infnet.juarez.avaliacaolimpeza

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class DadosViewModel : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser> = _user




}