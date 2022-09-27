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
        _binding = FragmentEstatisticaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val estatisticaViewModel =
            ViewModelProvider(this).get(EstatisticaViewModel::class.java)

        val lstEstatistica: ListView = binding.lstEstatistica

        atualizaRelatorioEstatistico()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun atualizaRelatorioEstatistico() {
        val relatorio: ArrayList<String> = ArrayList()
        val obj = perguntaPesquisaDAO.listarEstatistica()
        obj.addOnSuccessListener {
            var relBairro: String = ""
            var mediaSim: Int = 0
            var mediaNao: Int = 0
            var contador: Int = 0
            val linhaRel = ArrayList<String>()

            for (objeto in it) {
                val perguntaPesquisa = objeto.toObject(PerguntaPesquisa::class.java)
                if (relBairro != perguntaPesquisa.pesquisa?.estabelecimento?.bairro.toString() &&
                    relBairro != ""
                ) {
//                    mediaSim = mediaSim / contador
//                    mediaNao = mediaNao / contador
                    linhaRel.add("bairro: ${relBairro} - Sim: ${mediaSim} - Não: ${mediaNao}")

                    mediaSim = 0
                    mediaNao = 0
                    contador = 0
                }
                relBairro = perguntaPesquisa.pesquisa?.estabelecimento?.bairro.toString()
                for (perg in perguntaPesquisa.perguntaResposta) {
                    if (perg.resposta == true) {
                        mediaSim += 1
                    } else {
                        mediaNao += 1
                    }
                    contador += 1
                }
            }
//            mediaSim = mediaSim / contador
//            mediaNao = mediaNao / contador
            linhaRel.add("bairro: ${relBairro} - Sim: ${mediaSim} - Não: ${mediaNao}")

            val lstEstatistica = this.requireActivity().findViewById<ListView>(R.id.lstEstatistica)
            val adapterEstatistica = ArrayAdapter<String>(
                this.requireActivity(),
                android.R.layout.simple_list_item_1,
                linhaRel
            )
            lstEstatistica.adapter = adapterEstatistica

        }.addOnFailureListener()
        {
            val a = "erro"
        }
    }
}