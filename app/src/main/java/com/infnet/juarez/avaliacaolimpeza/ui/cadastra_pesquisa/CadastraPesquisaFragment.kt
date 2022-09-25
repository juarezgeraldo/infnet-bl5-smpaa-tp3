package com.infnet.juarez.avaliacaolimpeza.ui.cadastra_pesquisa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.infnet.juarez.avaliacaolimpeza.DAO.EstabelecimentoDAO
import com.infnet.juarez.avaliacaolimpeza.DAO.PerguntaDAO
import com.infnet.juarez.avaliacaolimpeza.DAO.PerguntaPesquisaDAO
import com.infnet.juarez.avaliacaolimpeza.DAO.PesquisaDAO
import com.infnet.juarez.avaliacaolimpeza.DadosViewModel
import com.infnet.juarez.avaliacaolimpeza.ListaPerguntasRespostasAdapter
import com.infnet.juarez.avaliacaolimpeza.R
import com.infnet.juarez.avaliacaolimpeza.RecyclerViewItemListner
import com.infnet.juarez.avaliacaolimpeza.databinding.FragmentCadastraPesquisaBinding
import com.infnet.juarez.avaliacaolimpeza.modelo.*

class CadastraPesquisaFragment : Fragment(), RecyclerViewItemListner {
    private val pesquisaDAO = PesquisaDAO()
    private val perguntaDAO = PerguntaDAO()
    private val perguntaPesquisaDAO = PerguntaPesquisaDAO()
    private val estabelecimentoDAO = EstabelecimentoDAO()

    private val sharedViewModel: DadosViewModel by activityViewModels()
    private var mUser: FirebaseUser? = null

    private var perguntasRespostas: ArrayList<PerguntaResposta> = ArrayList()
    private lateinit var listaPerguntaPesquisa: ArrayList<PerguntaPesquisa>
    private var pesquisa: Pesquisa = Pesquisa()
    private var listaIdEstabelecimentos: ArrayList<String> = ArrayList()
    private var _binding: FragmentCadastraPesquisaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        atualizaListaPerguntas()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mUser = sharedViewModel.recuperaUsusario()
        atualizaListaPerguntas()
        val cadastraPesquisaViewModel =
            ViewModelProvider(this).get(CadastraPesquisaViewModel::class.java)

        _binding = FragmentCadastraPesquisaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val txtId: TextView = binding.txtId
        val txtIdUser: TextView = binding.txtIdUser
        val spnEstabelecimento: Spinner = binding.spnEstabelecimento
        val btnSalvar: Button = binding.btnSalvar

//        txtIdUser.setText(mUser?.email!!)

        atualizaListaEstabelecimentos()

        btnSalvar.setOnClickListener {
            if (spnEstabelecimento.selectedItemPosition == 0) {
                Toast.makeText(
                    this.requireActivity(),
                    "Informe qual estabelecimento.",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                var estabelecimento: Estabelecimento = Estabelecimento()
                val obj =
                    estabelecimentoDAO.obter(listaIdEstabelecimentos[spnEstabelecimento.selectedItemPosition])
                obj.addOnSuccessListener {
                    estabelecimento = it.toObject(estabelecimento::class.java)!!
                    pesquisa = Pesquisa(null, null, estabelecimento)
                    pesquisa = pesquisaDAO.inserir(pesquisa)

                    val perguntaPesquisa = PerguntaPesquisa()
                    perguntaPesquisa.pesquisa = pesquisa
                    perguntaPesquisa.perguntaResposta = perguntasRespostas

                    perguntaPesquisaDAO.inserir(perguntaPesquisa)

                    txtId.setText(null)
                    spnEstabelecimento.setSelection(0)

                    Toast.makeText(
                        this.requireActivity(),
                        "Pesquisa incluída com sucesso.",
                        Toast.LENGTH_LONG
                    ).show()

                    atualizaListaPerguntas()

                }.addOnFailureListener {
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun atualizaListaPerguntas() {
        val objPerguntas = perguntaDAO.listar()
        perguntasRespostas = ArrayList()
        objPerguntas.addOnSuccessListener {
            for (objeto in it) {
                val pergunta = objeto.toObject(Pergunta::class.java)
                val perguntaResposta = PerguntaResposta()
                perguntaResposta.id = pergunta.id
                perguntaResposta.pergunta = pergunta.pergunta
                perguntaResposta.resposta = null
                perguntasRespostas.add(perguntaResposta)
            }
            val lstPerguntasRespostas =
                this.requireActivity().findViewById<RecyclerView>(R.id.lstPerguntasRespostas)
            lstPerguntasRespostas.layoutManager = LinearLayoutManager(this.requireActivity())
            val adapter = ListaPerguntasRespostasAdapter(perguntasRespostas)
            adapter.setRecyclerViewItemListner(this)
            lstPerguntasRespostas.adapter = adapter
        }.addOnFailureListener {
            val a = "erro"
        }
    }

    private fun atualizaListaEstabelecimentos() {
        val objEstabelecimento = estabelecimentoDAO.listar()
        val nomesEstabelecimento = ArrayList<String>()
        objEstabelecimento.addOnSuccessListener {
            for (objeto in it) {
                val estabelecimento = objeto.toObject(Estabelecimento::class.java)
                nomesEstabelecimento.add(estabelecimento.nome!!)
                listaIdEstabelecimentos.add(estabelecimento.id!!)
            }
            val spnEstabelecimento =
                this.requireActivity().findViewById<Spinner>(R.id.spnEstabelecimento)
            val adapterEstabelecimento =
                ArrayAdapter(
                    this.requireActivity(),
                    android.R.layout.simple_spinner_item,
                    nomesEstabelecimento
                )
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

    override fun recyclerViewRadioButton(
        view: View,
        perguntaResposta: PerguntaResposta,
        resposta: Boolean
    ) {
        for (pos in perguntasRespostas.indices) {
            if (perguntasRespostas[pos].id == perguntaResposta.id) {
                perguntasRespostas[pos].resposta = resposta
            }
        }
    }
}