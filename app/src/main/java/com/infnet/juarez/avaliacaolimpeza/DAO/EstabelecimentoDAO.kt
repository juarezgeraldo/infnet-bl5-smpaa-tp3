package com.infnet.juarez.avaliacaolimpeza.DAO

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.infnet.juarez.avaliacaolimpeza.modelo.Estabelecimento

class EstabelecimentoDAO {
    private val collection = "estabelecimentos"
    val db = Firebase.firestore

    fun inserir(estabelecimento: Estabelecimento): Estabelecimento {
        if (estabelecimento.id == null) {
            val ref: DocumentReference = db.collection(collection).document()
            estabelecimento.id = ref.id
            ref.set(estabelecimento).addOnSuccessListener {

            }.addOnFailureListener {

            }
        }
        return estabelecimento
    }
    fun alterar(estabelecimento: Estabelecimento): Estabelecimento{
        val ref: DocumentReference = db.collection(collection).document()
        ref.set(estabelecimento).addOnSuccessListener() {

        }.addOnFailureListener{

        }
        return estabelecimento
    }

    fun obter(id: String): Task<DocumentSnapshot>{
        return db.collection(collection).document(id).get()
    }


    fun listar(): Task<QuerySnapshot> {
        return  db.collection(collection).get()
    }
}

