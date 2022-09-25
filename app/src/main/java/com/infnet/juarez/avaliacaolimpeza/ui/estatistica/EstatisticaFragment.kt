package com.infnet.juarez.avaliacaolimpeza.ui.estatistica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.infnet.juarez.avaliacaolimpeza.DAO.PerguntaPesquisaDAO
import com.infnet.juarez.avaliacaolimpeza.R
import com.infnet.juarez.avaliacaolimpeza.databinding.FragmentEstatisticaBinding
import com.infnet.juarez.avaliacaolimpeza.modelo.PerguntaPesquisa

class EstatisticaFragment : Fragment() {
    private val perguntaPesquisaDAO = PerguntaPesquisaDAO()

    private var _binding: FragmentEstatisticaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val estatisticaViewModel =
            ViewModelProvider(this).get(EstatisticaViewModel::class.java)

        _binding = FragmentEstatisticaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val lstEstatistica: ListView = binding.lstEstatistica

        atualizaRelatorioEstatistico()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun atualizaRelatorioEstatistico(){
        val relatorio: ArrayList<String> = ArrayList()
        val obj = perguntaPesquisaDAO.listarEstatistica()
        obj.addOnSuccessListener {
            var relBairro: String = ""
            var relPergunta: String = ""
            var mediaSim: Int = 0
            var mediaNao: Int = 0
            var idx: Int = 0
            val linhaRel = ArrayList<String>()

            for (objeto in it) {
                val perguntaPesquisa = objeto.toObject(PerguntaPesquisa::class.java)
                if (relBairro != perguntaPesquisa.pesquisa?.estabelecimento?.bairro.toString() &&
                        relBairro != ""){
                    linhaRel.add("bairro: ${relBairro} - pergunta: ${relPergunta} - Sim: ${mediaSim} - Não: ${mediaNao}")

                    mediaSim = 0
                    mediaNao = 0
                    idx = 0

                    relBairro = perguntaPesquisa.pesquisa?.estabelecimento?.bairro.toString()
                    relPergunta = perguntaPesquisa.perguntaResposta[idx].pergunta.toString()

                }else{
                    if (relPergunta != perguntaPesquisa.perguntaResposta[idx].pergunta.toString() &&
                            relPergunta != ""){
                        linhaRel.add("bairro: ${relBairro} - pergunta: ${relPergunta} - Sim: ${mediaSim} - Não: ${mediaNao}")

                        mediaSim = 0
                        mediaNao = 0
                        idx += 1

                        relBairro = perguntaPesquisa.pesquisa?.estabelecimento?.bairro.toString()
                        relPergunta = perguntaPesquisa.perguntaResposta[0].pergunta.toString()
                    }
                }
            }
            linhaRel.add("bairro: ${relBairro} - pergunta: ${relPergunta} - Sim: ${mediaSim} - Não: ${mediaNao}")

            val lstEstatistica = this.requireActivity().findViewById<ListView>(R.id.lstEstatistica)
            val adapterEstatistica = ArrayAdapter<String>(
                this.requireActivity(),
                android.R.layout.simple_list_item_1,
                linhaRel
            )
            lstEstatistica.adapter = adapterEstatistica

        }.addOnFailureListener {
            val a = "erro"
        }
    }
}