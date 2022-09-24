package com.infnet.juarez.avaliacaolimpeza.DAO

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.infnet.juarez.avaliacaolimpeza.modelo.Pesquisa

class PesquisaDAO {
    private val collection = "pesquisas_collection"
    val db = Firebase.firestore

    fun inserir(pesquisa: Pesquisa): Pesquisa {
        if (pesquisa.id == null) {
            val ref: DocumentReference = db.collection(collection).document()
            pesquisa.id = ref.id
            ref.set(pesquisa).addOnSuccessListener {

            }.addOnFailureListener {

            }
        }
        return pesquisa
    }

    fun alterar(pesquisa: Pesquisa): Pesquisa {
        val ref: DocumentReference = db.collection(collection).document()
        ref.set(pesquisa).addOnSuccessListener() {
        }.addOnFailureListener {
        }
        return pesquisa
    }

    fun obter(id: String): Task<DocumentSnapshot>{
        return db.collection(collection).document(id).get()
    }


    fun listar(): Task<QuerySnapshot> {
        return db.collection(collection).get()
    }

}