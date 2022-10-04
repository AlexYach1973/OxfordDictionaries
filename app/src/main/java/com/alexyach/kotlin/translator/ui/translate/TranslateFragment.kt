package com.alexyach.kotlin.translator.ui.translate

import android.os.Bundle
import android.provider.UserDictionary.Words.APP_ID
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.alexyach.kotlin.translator.databinding.FragmentTranslateBinding
import com.alexyach.kotlin.translator.retrofit.APP_KEY
import com.alexyach.kotlin.translator.retrofit.RetrofitImpl
import com.alexyach.kotlin.translator.retrofit.modelDto.Translation
import com.alexyach.kotlin.translator.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.ui.base.BaseFragment
import okhttp3.*
import java.io.IOException

class TranslateFragment : BaseFragment<FragmentTranslateBinding,
        TranslateViewModel>() {

    override val viewModel: TranslateViewModel by lazy {
        ViewModelProvider(this)[TranslateViewModel::class.java]
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTranslateBinding.inflate(inflater, container, false)

    private val retrofit = RetrofitImpl()
    private var responseDto: WordTranslate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTranslate.setOnClickListener {

            responseDto = retrofit
                .getTranslateWord(binding.etInitialWord.text.toString())

            val translateWord: List<Translation>? = responseDto?.results?.get(0)?.
            lexicalEntries?.get(0)?.entries?.get(0)?.senses?.get(0)?.translations

            binding.tvTranslatedWord.text = translateWord.toString()


//            responseOkhhtp()
        }

    }


    private fun responseOkhhtp() {

        val client = OkHttpClient()

        val request = Request.Builder()
//            .url("https://od-api.oxforddictionaries.com/api/v2/entries/en-us/word")
            .url("https://od-api.oxforddictionaries.com/api/v2/translations/en/ru/word")
            .addHeader("app_id", APP_ID)
            .addHeader("app_key", APP_KEY)
            .build()

        val response= client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d("myLogs", "onResponse ${response.code()}")
                Log.d("myLogs", "onResponse ${response.body()?.string()}")
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("myLogs", "Error ${e.message}")
            }
        })

        Log.d("myLogs", "${response}")
    }

    companion object {
        fun newInstance() = TranslateFragment()
    }
}