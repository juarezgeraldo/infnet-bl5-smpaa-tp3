package com.infnet.juarez.avaliacaolimpeza.DAO

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.infnet.juarez.avaliacaolimpeza.modelo.PerguntaPesquisa

class PerguntaPesquisaDAO {
    private val collection = "perguntas_pesquisa_collection"
    val db = Firebase.firestore

    fun inserir(perguntaPesquisa: PerguntaPesquisa): PerguntaPesquisa {
            val ref: DocumentReference = db.collection(collection).document()
            ref.set(perguntaPesquisa).addOnSuccessListener {
            }.addOnFailureListener { }
        return perguntaPesquisa
    }

    fun obter(id: String): Task<DocumentSnapshot>{
        return db.collection(collection).document(id).get()
    }

    fun listarEstatistica(): Task<QuerySnapshot> {
        return db.collection(collection)
//            .orderBy("pesquisa.estabelecimento.bairro")
//            .orderBy("perguntaResposta.pergunta")
            .get()
    }

}