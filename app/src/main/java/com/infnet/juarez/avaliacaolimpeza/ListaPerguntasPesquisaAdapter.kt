package com.infnet.juarez.avaliacaolimpeza

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infnet.juarez.avaliacaolimpeza.modelo.Pergunta

class ListaPerguntasPesquisaAdapter(private val listaPerguntasPesquisa: ArrayList<Pergunta>) :
    RecyclerView.Adapter<ListaPerguntasPesquisaAdapter.ViewHolder>() {

    lateinit var itemListner: RecyclerViewItemListner

    fun setRecyclerViewItemListner(listner: RecyclerViewItemListner) {
        itemListner = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.lista_perguntas_pesquisas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listaPerguntasPesquisa[position], itemListner, position)
    }

    override fun getItemCount(): Int {
        return listaPerguntasPesquisa.size
    }

    //Classe interna = relação Tdo - Parte
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(pergunta: Pergunta, itemListner: RecyclerViewItemListner, position: Int) {
            val txtIdPerguntaPesquisa = itemView.findViewById<TextView>(R.id.txtIdPerguntaPesquisa)
            txtIdPerguntaPesquisa.setText(pergunta.id.toString())
            val txtPerguntaPesquisa = itemView.findViewById<TextView>(R.id.txtPerguntaPesquisa)
            txtPerguntaPesquisa.setText(pergunta.pergunta)
            val rdbSim = itemView.findViewById<ImageButton>(R.id.rdbSim)
            val rdbNao = itemView.findViewById<ImageButton>(R.id.rdbNao)

            rdbSim.setOnClickListener() {
                itemListner.recyclerViewRadioButton(it, true)
            }
            rdbNao.setOnClickListener() {
                itemListner.recyclerViewRadioButton(it, false)
            }
        }
    }
}
