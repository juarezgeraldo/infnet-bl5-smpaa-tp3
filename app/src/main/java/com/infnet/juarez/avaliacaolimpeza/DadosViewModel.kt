package com.infnet.juarez.avaliacaolimpeza

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.model.MutableDocument
import com.infnet.juarez.avaliacaolimpeza.modelo.Usuario

class DadosViewModel : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: MutableLiveData<FirebaseUser?> = _user





    fun registraUsusario(user: FirebaseUser ){
        _user.value = user
    }

    fun recuperaUsusario(): FirebaseUser? {
        return user.value
    }


}