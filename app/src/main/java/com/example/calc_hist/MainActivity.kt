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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_div.setOnClickListener(){ addSymbol("/")}
        btn_minus.setOnClickListener(){ addSymbol("-")}
        btn_mult.setOnClickListener(){ addSymbol("x")}
        btn_plus.setOnClickListener(){ addSymbol("+")}

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

