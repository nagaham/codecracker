package com.example.codecracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.codecracker.databinding.ActivityMainBinding

import java.lang.Math.random
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //それぞれの文字が何個あるのかをカウントする
        var countofcorrectans = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について正解を収納
        var countofyournumber = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について自分の答えを収納
        var numofac = 0
        var numofcorrect = 0 //文字列の中で正解の数字が何個あるか
        var numoftrials = 1 //挑戦回数をカウントする変数
        var outputtext = ""//出力用文字列
        var inputnumber = ""

        //初期設定で自分の答えを ____ にする
        var yourans = mutableListOf<String>("_","_","_","_")
        val randnumber = ((10000*random()).toInt()).toString().padStart(4,'0')

        for(n in 0 .. 9) countofcorrectans[n] = randnumber.count{ it == (n+48).toChar() }

        binding.button01.setOnClickListener {
            inputnumber = binding.inputnum01.text.toString()

        //正解するまで繰り返す
            if(inputnumber.length < 4 ){
                inputnumber = "____"
                outputtext = "入力が 4文字未満なので判定できませんでした\n"
                binding.textView01.text = outputtext
            }
            else if(inputnumber.length > 4){
                inputnumber = "____"
                outputtext = "入力が 4文字よりも多かったので判定できませんでした\n"
                binding.textView01.text = outputtext
            }
            else{
                outputtext = "\n"
                binding.textView01.text = outputtext
            }
            outputtext += "$numoftrials 回目のトライアルが終わりました\n"
            numoftrials++
            for(n in 0 .. 9) countofyournumber[n] = inputnumber.count{ it == (n+48).toChar() }
            for(n in 0 .. 9){
                if(min(countofyournumber[n],countofcorrectans[n]) != 0){
                    outputtext += "数字の $n は ${min(countofyournumber[n],countofcorrectans[n])} 個合っています\n"
                }
                numofcorrect += min(countofyournumber[n], countofcorrectans[n])
            }

            for (i in 0..3) {
                if (inputnumber[i] == randnumber[i]) {
                    yourans[i] = randnumber[i].toString()
                    numofac++
                }
            }
            outputtext += "場所も数字も合っているのは $numofac 個。\n場所が違うものも含めて数字が合っているのが$numofcorrect 個\n"
//        println(yourans.joinToString(separator = ""))
            numofac = 0
            numofcorrect = 0
        }
            outputtext += "Great!!!\n" + "$numoftrials 回目の挑戦で成功しました！\n"
            binding.textView02.text = outputtext
            outputtext = ""
    }
}