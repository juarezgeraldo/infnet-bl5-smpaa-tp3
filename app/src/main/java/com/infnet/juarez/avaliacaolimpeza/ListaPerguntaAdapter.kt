package com.infnet.juarez.avaliacaolimpeza

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infnet.juarez.avaliacaolimpeza.modelo.Pergunta

class ListaPerguntaAdapter(private val listaPerguntas: ArrayList<Pergunta>) :
    RecyclerView.Adapter<ListaPerguntaAdapter.ViewHolder>() {

    //    var listaPerguntas = ArrayList<Pergunta>()
//    set(value){
//        field = value
//        this.notifyDataSetChanged()
//    }
    lateinit var itemListner: RecyclerViewItemListner

    fun setRecyclerViewItemListner(listner: RecyclerViewItemListner) {
        itemListner = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.lista_perguntas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listaPerguntas[position], itemListner, position)
    }

    override fun getItemCount(): Int {
        return listaPerguntas.size
    }

    //Classe interna = relação Tdo - Parte
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(pergunta: Pergunta, itemListner: RecyclerViewItemListner, position: Int) {
            val txtListaIdPergunta = itemView.findViewById<TextView>(R.id.txtIdPergunta)
            txtListaIdPergunta.setText(pergunta.id.toString())
            val txtListaPergunta = itemView.findViewById<TextView>(R.id.txtPergunta)
            txtListaPergunta.setText(pergunta.pergunta)
            val btnExcluirPergunta = itemView.findViewById<ImageButton>(R.id.btnExcluirPergunta)
            val btnAlterarPergunta = itemView.findViewById<ImageButton>(R.id.btnAlterarPergunta)

            btnExcluirPergunta.setOnClickListener() {
                itemListner.recyclerViewBotaoExcluirClicked(it, txtListaIdPergunta.text.toString())
            }
            btnAlterarPergunta.setOnClickListener() {
                itemListner.recyclerViewBotaoAlterarClicked(it, txtListaIdPergunta.text.toString())
            }
        }
    }
}
