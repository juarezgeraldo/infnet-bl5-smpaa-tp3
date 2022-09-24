package com.infnet.juarez.avaliacaolimpeza.ui.cadastra_pesquisa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infnet.juarez.avaliacaolimpeza.DAO.EstabelecimentoDAO
import com.infnet.juarez.avaliacaolimpeza.DAO.PerguntaDAO
import com.infnet.juarez.avaliacaolimpeza.DAO.PesquisaDAO
import com.infnet.juarez.avaliacaolimpeza.ListaPerguntasPesquisaAdapter
import com.infnet.juarez.avaliacaolimpeza.R
import com.infnet.juarez.avaliacaolimpeza.RecyclerViewItemListner
import com.infnet.juarez.avaliacaolimpeza.databinding.FragmentCadastraPesquisaBinding
import com.infnet.juarez.avaliacaolimpeza.modelo.Estabelecimento
import com.infnet.juarez.avaliacaolimpeza.modelo.Pergunta
import com.infnet.juarez.avaliacaolimpeza.modelo.Pesquisa

class CadastraPesquisaFragment : Fragment(), RecyclerViewItemListner {
    private val pesquisaDAO = PesquisaDAO()
    private val perguntaDAO = PerguntaDAO()
    private val estabelecimentoDAO = EstabelecimentoDAO()

    private lateinit var listaIdEstabelecimentos: ArrayList<String>
    private var _binding: FragmentCadastraPesquisaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cadastraPesquisaViewModel =
            ViewModelProvider(this).get(CadastraPesquisaViewModel::class.java)

        _binding = FragmentCadastraPesquisaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val txtId: TextView = binding.txtId
        val txtIdUser: TextView = binding.txtIdUser
        val spnEstabelecimento: Spinner = binding.spnEstabelecimento
        val btnSalvar: Button = binding.btnSalvar

//        spnEstabelecimento.is.setOnClickListener(){
            atualizaListaPerguntas()
//        }

        btnSalvar.setOnClickListener {
            if (!spnEstabelecimento.isSelected) {
                Toast.makeText(
                    this.requireActivity(),
                    "Informe qual estabelecimento.",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                var estabelecimento: Estabelecimento = Estabelecimento()
                val obj = estabelecimentoDAO.obter(listaIdEstabelecimentos[spnEstabelecimento.selectedItemPosition])
                obj.addOnSuccessListener {
                    estabelecimento = it.toObject(estabelecimento::class.java)!!
                }.addOnFailureListener {
                }

                val pesquisa = Pesquisa(null, null, estabelecimento, null)

//                        atualizaPesquisa(pesquisa, "incluir")
//                    txtId.setText(null)
//                    edtTxtPesquisa.setText(null)
//
//                    this.atualizaListaPesquisas()
            }
        }
        atualizaListaPerguntas()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun atualizaListaPerguntas() {
        val objPerguntas = perguntaDAO.listar()
        val perguntas: ArrayList<Pergunta> = ArrayList()
        objPerguntas.addOnSuccessListener {
            for (objeto in it) {
                val pergunta = objeto.toObject(Pergunta::class.java)
                perguntas.add(pergunta)
            }
            val lstPerguntasPesquisas = this.requireActivity().findViewById<RecyclerView>(R.id.lstPerguntasPesquisas)
            lstPerguntasPesquisas.layoutManager = LinearLayoutManager(this.requireActivity())
            val adapter = ListaPerguntasPesquisaAdapter(perguntas)
            adapter.setRecyclerViewItemListner(this)
            lstPerguntasPesquisas.adapter = adapter
        }.addOnFailureListener {
            val a = "erro"
        }

        val objEstabelecimento = estabelecimentoDAO.listar()
        val nomesEstabelecimento = ArrayList<String>()
        objEstabelecimento.addOnSuccessListener {
            for (objeto in it) {
                val estabelecimento = objeto.toObject(Estabelecimento::class.java)
                nomesEstabelecimento.add(estabelecimento.nome!!)
                listaIdEstabelecimentos.add(estabelecimento.id!!)
            }
            val spnEstabelecimento = this.requireActivity().findViewById<Spinner>(R.id.spnEstabelecimento)
            val adapterEstabelecimento =
                ArrayAdapter(this.requireActivity(), android.R.layout.simple_spinner_item, nomesEstabelecimento)
            spnEstabelecimento.adapter = adapterEstabelecimento
        }.addOnFailureListener {
            val a = "erro"
        }
    }

    override fun recyclerViewBotaoAlterarClicked(view: View, id: String) {
    }

    override fun recyclerViewBotaoExcluirClicked(view: View, id: String): Boolean {
        return false
    }

    override fun recyclerViewRadioButton(view: View, id: Boolean) {
        TODO("Not yet implemented")
    }
}