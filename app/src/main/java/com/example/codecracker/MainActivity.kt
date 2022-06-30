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

// git push　できなかった
//それぞれの文字が何個あるのかをカウントする
var countofcorrectans = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について正解を収納
var countofyournumber = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について自分の答えを収納
var perfectnum = 0 //correct の数が何個あるか
var closenum = 0 //文字列の中で正解の数字が何個あるか
var numoftrials = 1 //挑戦回数をカウントする変数
var outputtext = ""//出力用文字列
var discoverynum = "" //発見した文字列を収納する変数
var inputnumber = "" //入力した文字列を収納する変数
var historynumber = "" //入力履歴と判定を残す変数

//初期設定で自分の答えを ____ にする
var yourans = mutableListOf<String>("_","_","_","_") //自分が答えた文字列を収納するlist
var randnumber = "" //ランダムに選ばれた正解の文字列を収納する変数

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        determineRand() // 正解の数をランダムに選択

        binding.button01.setOnClickListener {

            if(binding.button01.text == "Restart") initial()//Restartの場合、初期化して乱数も取り直す

            else {

                inputnumber = binding.inputnum01.text.toString() //答えた文字列をinputnumberに収納
                historynumber += inputnumber //答えた文字列を履歴の変数historynumberにも収納

                judgeNumOfStrings() //文字数のみをジャッジ

                outputtext += "⭐️$numoftrials 回目\n"
                numoftrials++

                countOfNumCorrectClose() //correctとcloseの数字がそれぞれ何個あるのかをカウント

                for (n in 0..9) {
                    if (min(countofyournumber[n], countofcorrectans[n]) != 0) {
                        discoverynum += "$n "
                    }
                    closenum += min(countofyournumber[n], countofcorrectans[n])//closenum にはこの時点で場所は関係なく数字が合っている個数が入る
                }

                discoverynum = discoverynum.split(' ').toSet().toString().drop(1).dropLast(3) //発見した文字を文字列型に収納
                for (i in 0..3) {
                    if (inputnumber[i] == randnumber[i]) {
                        yourans[i] = randnumber[i].toString()
                        perfectnum++
                    }
                }

                closenum -= perfectnum //closenum に　場所は合っていないが数字が合っている個数を入れる

                outputtext += "完璧 $perfectnum 個\n場所違い$closenum 個\n"
                historynumber += " Correct " + perfectnum + "  Close " + closenum + "\n"
                binding.textView01.text = outputtext
                binding.textViewfin.text = "発見した数\n$discoverynum"
                binding.history01.text = historynumber
                discoverynum = ""

                congratulations()
            }
        }
    }

    // 入力した文字数が正しいかどうかを判断する
    fun judgeNumOfStrings(){
        if(inputnumber.length < 4 ){
            inputnumber = "____"
            outputtext = "4文字未満で判定できず\n"
            binding.inputnum01.text = null
        }
        else if(inputnumber.length > 4){
            inputnumber = "____"
            outputtext = "4文字以上で判定できず\n"
            binding.inputnum01.text = null
        }
        else{
            outputtext = ""
        }
    }
    // それぞれの変数を初期化
    fun initial(){
        countofcorrectans = listOf<Int>(0,0,0,0,0,0,0,0,0,0) as MutableList<Int> //0~9までの数字について正解を収納
        countofyournumber = listOf<Int>(0,0,0,0,0,0,0,0,0,0) as MutableList<Int> //0~9までの数字について自分の答えを収納
        perfectnum = 0 //correct の数が何個あるか
        closenum = 0 //文字列の中で正解の数字が何個あるか
        numoftrials = 1 //挑戦回数をカウントする変数
        outputtext = ""//出力用文字列
        discoverynum = "" // 発見した文字列を収納する文字列変数
        inputnumber = ""
        historynumber = ""

        binding.textViewfin.text = null //完璧に正解したときの絵文字を初期化
        binding.history01.text = null //履歴の表示欄をリセット
        binding.inputnum01.text = null //入力した文字列を消去
        binding.textView01.text = null //判定時に出るコメントをリセット

        //初期設定で自分の答えを ____ にする
        yourans = mutableListOf<String>("_","_","_","_")
        randnumber = ""
        determineRand()
        if(binding.button01.text == "Restart")binding.button01.text = "Check"
    }

    // 正解の数をランダムに選択し、正解の中にどの数が何個あるのかをカウント
    fun determineRand(){
        randnumber = ((10000*random()).toInt()).toString().padStart(4,'0')
        for(n in 0 .. 9) countofcorrectans[n] = randnumber.count{ it == (n+48).toChar() }
    }
    //入力した文字列の中にcorrectとcloseの数字がそれぞれ何個あるのかをカウント
    fun countOfNumCorrectClose(){
        for(n in 0 .. 9) countofyournumber[n] = inputnumber.count{ it == (n+48).toChar() }
    }
    //完全に正解した場合の挙動
    fun congratulations(){
        if(randnumber != inputnumber){
            perfectnum = 0
            closenum = 0
            outputtext = ""
            binding.inputnum01.text = null
        }
        else{
            outputtext += "Great!!!\n" + "${numoftrials - 1} 回目で成功！\n"
            binding.textView01.text = outputtext
            outputtext = ""

            award() //回数に応じて表示するコメントを変化させる

            binding.button01.text = "Restart"
        }

    }
    //　完全正解するのに必要な回数ごとに表示を決める
    fun award(){
        if( numoftrials-1 == 1) binding.textViewfin.text = "😎🏆"
        else if( numoftrials-1 == 2) binding.textViewfin.text = "😃"
        else if( numoftrials-1 == 3) binding.textViewfin.text = "☺️"
        else if( numoftrials-1 == 4) binding.textViewfin.text = "😄"
        else if( numoftrials-1 == 5) binding.textViewfin.text = "😃"
        else if( numoftrials-1 == 6) binding.textViewfin.text = "🌟"
        else if( numoftrials-1 == 7) binding.textViewfin.text = "✨"
        else if( numoftrials-1 == 8) binding.textViewfin.text = "⚡️"
        else if( numoftrials-1 == 9) binding.textViewfin.text = "💫"
        else if( numoftrials-1 == 10) binding.textViewfin.text = "🌈"
        else  binding.textViewfin.text = "😵‍"

    }
}