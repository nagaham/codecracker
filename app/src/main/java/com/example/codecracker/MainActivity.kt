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

var countofcorrectans = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について正解の数字がそれぞれ何個ずつあるのかを数えて収納
var countofyournumber = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について自分の答えた数字がそれぞれ何個ずつあるのかを数えて収納
var numofperf = 0 //correct の数が何個あるか
var numofcorrect = 0 //文字列の中のn文字目に正解の数字が何個あるか
var numoftrials = 1 //挑戦回数をカウントする変数

var inputnumber = "" //入力した文字列を収納する変数
var outputtext = ""//出力用文字列 textView01に表示

//初期設定で自分の答えを ____ にする
var yourans = mutableListOf<String>("_","_","_","_","_","_","_","_","_","_") //自分が答えた文字列を収納するlist
var digitlimit = "" //答えの桁数の上限
var digitofans = 0 //答えの桁数
var digittemp = 1L // randnumberを決める際の一時的な変数
var randnumber = "" //ランダムに選ばれた正解の文字列を収納する変数
var historynumber = "" //入力履歴と判定を残す変数
var discoverynum = "" //発見した文字列を収納する変数

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        determineDigit() //桁数を決定
        determineRand() // 正解の数をランダムに選択

        binding.giveup.setOnClickListener{
            binding.giveup.text = randnumber
        }
        binding.button01.setOnClickListener {

            if(binding.button01.text == "Restart") {
                initial()
            }//Restartの場合、初期化して乱数も取り直す

            else {
                inputnumber = binding.inputnum01.text.toString() //答えた文字列をinputnumberに収納
                historynumber += inputnumber //答えた文字列を履歴の変数historynumberにも収納
                numoftrials++ //挑戦回数をカウント

                countOfClose() //入力した文字列の中に場所は関係なく入力した数字の中に正解が何個あるのかをカウント
                longOrShort()//入力した文字列が正解の文字数よりも長い場合は削り、短い場合はxを付け足す

                discoverynum = discoverynum.split(' ').toSet().toString().drop(1).dropLast(3) //発見した文字を文字列型に収納
                for (i in 0..3) {
                    if (inputnumber[i] == randnumber[i]) {
                        yourans[i] = randnumber[i].toString()
                        numofperf ++
                    }
                }

                numofcorrect -= numofperf  //numofcorrect に　場所は合っていないが数字が合っている個数を入れる

                outputtext += "完璧 ${numofperf } 個\n場所違い$numofcorrect 個\n"
                historynumber += " Correct " + numofperf + "  Close " + numofcorrect + "\n"
                binding.textView01.text = outputtext
                binding.textViewfin.text = "発見した数\n$discoverynum"
                binding.history01.text = historynumber
                discoverynum = ""

                congratulations()
            }
        }
    }
    fun determineDigit(){
        digitlimit = "6" //桁数の上限を設定
    }

    // 入力した文字数が正しいかどうかを判断する

    // それぞれの変数を初期化
    fun initial(){
        countofcorrectans = listOf<Int>(0,0,0,0,0,0,0,0,0,0) as MutableList<Int> //0~9までの数字について正解を収納
        countofyournumber = listOf<Int>(0,0,0,0,0,0,0,0,0,0) as MutableList<Int> //0~9までの数字について自分の答えを収納
        numofperf = 0 //correct の数が何個あるか
        numofcorrect = 0 //文字列の中で正解の数字が何個あるか
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
        digitofans = max(3,(random()*digitlimit.toInt()+1).toInt())//digitofans にはランダムに選ばれた答えの桁数

        for (i in 1..digitofans) digittemp*=10L //digittempは桁数が正しくなっている仮置きの数で100..の形式
        //ランダムに選ばれた数を左から0埋めパディングしたものがrandnumber
        randnumber = ((digittemp* random()).toLong()).toString().padStart(digitofans,'0')

        //それぞれの文字が何個あるのかをカウントする
        for(n in 0 .. 9) countofcorrectans[n] = randnumber.count{ it == (n+48).toChar() }
    }
    fun countOfClose(){
        for(n in 0 .. 9) countofyournumber[n] = inputnumber.count{ it == (n+48).toChar() }
        for (n in 0..9) {
            if (min(countofyournumber[n], countofcorrectans[n]) != 0) {
                discoverynum += "$n "
            }
            numofcorrect += min(countofyournumber[n], countofcorrectans[n])//numofcorrect にはこの時点で場所は関係なく数字が合っている個数が入る
        }
    }    //入力した文字列の中に場所は関係なく入力した数字の中に正解が何個あるのかをカウント

    fun longOrShort(){
        if(inputnumber.length < randnumber.length) inputnumber = inputnumber.padEnd(digitofans,'x')
        else if(inputnumber.length > randnumber.length) inputnumber = inputnumber.dropLast(inputnumber.length - digitofans)
    }//入力した文字列が正解の文字数よりも長い場合は削り、短い場合はxを付け足す

    fun congratulations(){    //完全に正解した場合の挙動
        if(randnumber != inputnumber){
            numofperf = 0
            numofcorrect = 0
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