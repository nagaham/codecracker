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
        var perfectnum = 0
        var closenum = 0 //文字列の中で正解の数字が何個あるか
        var numoftrials = 1 //挑戦回数をカウントする変数
        var outputtext = ""//出力用文字列
        var inputnumber = ""
        var historynumber = ""

        //初期設定で自分の答えを ____ にする
        var yourans = mutableListOf<String>("_","_","_","_")
        val randnumber = ((10000*random()).toInt()).toString().padStart(4,'0')

        for(n in 0 .. 9) countofcorrectans[n] = randnumber.count{ it == (n+48).toChar() }

        binding.button01.setOnClickListener {
            inputnumber = binding.inputnum01.text.toString()
            historynumber += inputnumber
            outputtext = ""//出力用文字列を初期化

        //正解するまで繰り返す
            if(inputnumber.length < 4 ){
                inputnumber = "____"
                outputtext = "入力が 4文字未満なので判定できませんでした\n"
            }
            else if(inputnumber.length > 4){
                inputnumber = "____"
                outputtext = "入力が 4文字よりも多かったので判定できませんでした\n"
            }
            else{
                outputtext = ""
            }
            outputtext += "$numoftrials 回目のトライアルが終わりました\n"
            numoftrials++
            for(n in 0 .. 9) countofyournumber[n] = inputnumber.count{ it == (n+48).toChar() }
            for(n in 0 .. 9){
                if(min(countofyournumber[n],countofcorrectans[n]) != 0){
                    outputtext += "数字の $n は ${min(countofyournumber[n],countofcorrectans[n])} 個見つかりました\n"
                }
                closenum += min(countofyournumber[n], countofcorrectans[n])//closenum にはこの時点で場所は関係なく数字が合っている個数が入る
            }

            for (i in 0..3) {
                if (inputnumber[i] == randnumber[i]) {
                    yourans[i] = randnumber[i].toString()
                    perfectnum++
                }
            }

            closenum -= perfectnum //closenum に　場所は合っていないが数字が合っている個数を入れる

            outputtext += "場所も数字も合っているのは $perfectnum 個。\n数字が合っているのが合計$closenum 個\n"
            historynumber += " Correct " + perfectnum + "  Close " + closenum + "\n"
            binding.textView01.text = outputtext
            binding.history01.text = historynumber

            if(randnumber != inputnumber){
                perfectnum = 0
                closenum = 0
                outputtext = ""
            }
            else{
                outputtext += "Great!!!\n" + "${numoftrials - 1} 回目の挑戦で成功しました！\n"
                binding.textView01.text = outputtext
                outputtext = ""
                binding.textViewfin.text = "😎🏆"
            }
//        println(yourans.joinToString(separator = ""))
        }
    }
}