package br.com.thiengo.pdfviwertest.data

import android.content.Context
import br.com.thiengo.pdfviwertest.domain.Doc
import vostore.approvado.R

class Database {
    companion object{
        fun getDocs() = listOf(
                Doc("ApostiladePortugues.pdf", R.drawable.apostila1, "Apostila de Português", 207),
                Doc("ApostilaMatematica.pdf", R.drawable.apostila2, "Apostila de Matemática", 123),
                Doc("ApostilaInformatica.pdf", R.drawable.apostila3, "Apostila Noções de Informática", 32),
                Doc("ApostiladeEspecifico.pdf", R.drawable.apostila4, "Apostila de Conhecimentos Específicos", 195),
                Doc("ApostilaWord.pdf", R.drawable.apostila5, "Apostila de Microsoft Word", 78),
                Doc("ApostilaPower.pdf", R.drawable.apostila6, "Apostila de Microsoft Power Point", 54),
                Doc("ApostilaExcel.pdf", R.drawable.apostila7, "Apostila de Microsoft Excel", 16)
            )

        fun saveActualPageSP( context: Context, key: String, page: Int ){
            context
                .getSharedPreferences("PREF", Context.MODE_PRIVATE)
                .edit()
                .putInt("$key-page", page)
                .apply()
        }

        fun getActualPageSP( context: Context, key: String )
            = context
                    .getSharedPreferences("PREF", Context.MODE_PRIVATE)
                    .getInt("$key-page", 0)

    }
}

