package com.example.calc_hist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

val multPat = Regex("[0-9.]+[x][0-9.]+")
val divPat = Regex("[0-9.]+[/][0-9.]+")
val plusPat = Regex("[0-9.]+[+][0-9.]+")
val minPat = Regex("[0-9.]+[-][0-9.]+")
val simbPat = Regex("[+\\-/x]")
val lastnumPat = Regex("[0-9.]+$")


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_div.setOnClickListener(){ addSymbol("/")}
        btn_minus.setOnClickListener(){ addSymbol("-")}
        btn_mult.setOnClickListener(){ addSymbol("x")}
        btn_plus.setOnClickListener(){ addSymbol("+")}

        btn_0.setOnClickListener(){addNum("0")}
        btn_1.setOnClickListener(){addNum("1")}
        btn_2.setOnClickListener(){addNum("2")}
        btn_3.setOnClickListener(){addNum("3")}
        btn_4.setOnClickListener(){addNum("4")}
        btn_5.setOnClickListener(){addNum("5")}
        btn_6.setOnClickListener(){addNum("6")}
        btn_7.setOnClickListener(){addNum("7")}
        btn_8.setOnClickListener(){addNum("8")}
        btn_9.setOnClickListener(){addNum("9")}

        btn_result.setOnClickListener(){
            val txt = txt_prev.text.toString()
            var result = eval(txt)
            if(result % result.toInt() == 0.0){
                txt_prev.text = result.toInt().toString()
            }else{
                txt_prev.text = result.toString()
            }

            txt_complete.text = txt

        }

        btn_ce.setOnClickListener(){txt_prev.text = ""}
        btn_c.setOnClickListener(){txt_prev.text = ""}

    }

    fun addNum(num:String){
        var txt = txt_prev.text.toString()
        txt += num
        var lastNum = lastnumPat.find(txt)?.value.toString()
        if( Regex("[.]").find(lastNum) != null){
            txt = txt.replace(lastNum, lastNum?.toDouble().toString())
        }else{
            txt = txt.replace(lastNum, lastNum?.toInt().toString())
        }

        txt_prev.text = txt
    }

    fun addSymbol(symbol: String) {
        var txt = txt_prev.text.toString()

        if (txt.isEmpty()) {
            if (symbol == "-") txt += "-"
        } else {
            val lastChar = txt.drop(txt.lastIndex)
            txt = txt.substring(0..(txt.lastIndex - 1))
            if (simbPat.find(lastChar) == null) {
                txt += lastChar
            }
            txt += symbol
        }
        if (txt.length == 1)
            if ((txt != "-" && simbPat.find(txt) != null))
                txt = ""

        txt_prev.text = txt
    }

    fun eval(op_txt: String): Double {

        var txt = op_txt

        while (multPat.find(txt) != null) {
            val res = multPat.find(txt)?.value.toString()
            val result = getResult(res)
            txt = txt.replace(res, result.toString())
        }

        while (divPat.find(txt) != null) {
            val res = divPat.find(txt)?.value.toString()
            val result = getResult(res)
            txt = txt.replace(res, result.toString())
        }

        while (plusPat.find(txt) != null) {
            val res = plusPat.find(txt)?.value.toString()
            val result = getResult(res)
            txt = txt.replace(res, result.toString())
        }

        while (minPat.find(txt) != null) {
            val res = minPat.find(txt)?.value.toString()
            val result = getResult(res)
            txt = txt.replace(res, result.toString())
        }

        return txt.toDouble()
    }

    fun getResult(txt: String): Double {

        val numbers = txt.split(simbPat).map { x -> x.toDouble() }
        val symbol = simbPat.find(txt)?.value

        when (symbol) {
            "+" -> return numbers[0] + numbers[1]
            "-" -> return numbers[0] - numbers[1]
            "x" -> return numbers[0] * numbers[1]
            "/" -> return numbers[0] / numbers[1]
        }
        return 0.0
    }

}

