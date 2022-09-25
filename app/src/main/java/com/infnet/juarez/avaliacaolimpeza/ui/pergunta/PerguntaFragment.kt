package com.infnet.juarez.avaliacaolimpeza.ui.pergunta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infnet.juarez.avaliacaolimpeza.DAO.PerguntaDAO
import com.infnet.juarez.avaliacaolimpeza.ListaPerguntaAdapter
import com.infnet.juarez.avaliacaolimpeza.R
import com.infnet.juarez.avaliacaolimpeza.RecyclerViewItemListner
import com.infnet.juarez.avaliacaolimpeza.databinding.FragmentPerguntaBinding
import com.infnet.juarez.avaliacaolimpeza.modelo.Pergunta
import com.infnet.juarez.avaliacaolimpeza.modelo.PerguntaResposta

class PerguntaFragment : Fragment(), RecyclerViewItemListner {
    private val perguntaDAO = PerguntaDAO()

    private var _binding: FragmentPerguntaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val perguntaViewModel =
            ViewModelProvider(this).get(PerguntaViewModel::class.java)

        _binding = FragmentPerguntaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val edtTxtPergunta: TextView = binding.edtTxtPergunta
        val txtId: TextView = binding.txtId
        val btnSalvar: Button = binding.btnSalvar

        btnSalvar.setOnClickListener {
            if (edtTxtPergunta.text.toString().isEmpty()) {
                Toast.makeText(this.requireActivity(), "Informe a pergunta.", Toast.LENGTH_LONG)
                    .show()
            } else {
                if (txtId.text.toString().isEmpty()) {
                    val pergunta = Pergunta(
                        null,
                        edtTxtPergunta.text.toString()
                    )
                    atualizaPergunta(pergunta, "incluir")
                } else {
                    val pergunta = Pergunta(
                        txtId.text.toString(),
                        edtTxtPergunta.text.toString()
                    )
                    this.atualizaPergunta(pergunta, "alterar")
                }
                txtId.setText(null)
                edtTxtPergunta.setText(null)

                this.atualizaListaPerguntas()
            }
        }
        atualizaListaPerguntas()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        atualizaListaPerguntas()
    }

    //    override fun onStart() {
//        super.onStart()
//        atualizaListaPerguntas()
//    }
//
    private fun atualizaListaPerguntas() {
        val obj = perguntaDAO.listar()
        val perguntas: ArrayList<Pergunta> = ArrayList()
        obj.addOnSuccessListener {
            for (objeto in it) {
                val pergunta = objeto.toObject(Pergunta::class.java)
                perguntas.add(pergunta)
            }
            val lstPerguntas = this.requireActivity().findViewById<RecyclerView>(R.id.lstPerguntas)
            lstPerguntas.layoutManager = LinearLayoutManager(this.requireActivity())
            val adapter = ListaPerguntaAdapter(perguntas)
            adapter.setRecyclerViewItemListner(this)
            lstPerguntas.adapter = adapter
        }.addOnFailureListener {
            val a = "erro"
        }

    }

    private fun atualizaPergunta(pergunta: Pergunta, operacao: String) {
        when (operacao) {
            "incluir" -> {
                perguntaDAO.inserir(pergunta)
                Toast.makeText(
                    this.requireActivity(),
                    "Inclusão realizada com sucesso.",
                    Toast.LENGTH_LONG
                ).show()
            }
            "alterar" -> {
                perguntaDAO.alterar(pergunta)
                Toast.makeText(
                    this.requireActivity(),
                    "Alteração realizada com sucesso.",
                    Toast.LENGTH_LONG
                ).show()
            }
            "excluir" -> {
                perguntaDAO.excluir(pergunta.id!!)
                Toast.makeText(
                    this.requireActivity(),
                    "Exclusão realizada com sucesso.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        atualizaListaPerguntas()
    }

    override fun recyclerViewBotaoAlterarClicked(view: View, id: String) {
        var pergunta: Pergunta = Pergunta()
        val obj = perguntaDAO.obter(id)
        obj.addOnSuccessListener {
            pergunta = it.toObject(Pergunta::class.java)!!

            val txtId = this.requireActivity().findViewById<TextView>(R.id.txtId)
            val txtPergunta = this.requireActivity().findViewById<EditText>(R.id.edtTxtPergunta)

            txtId.setText(pergunta.id)
            txtPergunta.setText(pergunta.pergunta)
        }.addOnFailureListener {
        }
    }

    override fun recyclerViewBotaoExcluirClicked(view: View, id: String): Boolean {
        var pergunta: Pergunta = Pergunta()
        val obj = perguntaDAO.obter(id)
        obj.addOnSuccessListener {
            pergunta = it.toObject(Pergunta::class.java)!!

            atualizaPergunta(pergunta, "excluir")
        }.addOnFailureListener {
        }
        return true
    }

    override fun recyclerViewRadioButton(
        view: View,
        perguntaResposta: PerguntaResposta,
        resposta: Boolean
    ) {
        TODO("Not yet implemented")
    }
}