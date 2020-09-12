package com.example.calc_hist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

val multPat = Regex("[0-9.]+[x][0-9.]+")
val divPat = Regex("[0-9.]+[/][0-9.]+")
val plusPat = Regex("[0-9.]+[+][0-9.]+")
val minPat = Regex("[0-9.]+[-][0-9.]+")
val simbPat = Regex("[+\\-/x]")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }




}

fun eval( op_txt: String): Double {

    var txt = op_txt

    while (multPat.find(txt) != null){
        val res = multPat.find(txt)?.value.toString()
        val result = getResult(res)
        txt = txt.replace(res, result.toString())
    }

    while (divPat.find(txt) != null){
        val res = divPat.find(txt)?.value.toString()
        val result = getResult(res)
        txt = txt.replace(res, result.toString())
    }

    while (plusPat.find(txt) != null){
        val res = plusPat.find(txt)?.value.toString()
        val result = getResult(res)
        txt = txt.replace(res, result.toString())
    }

    while (minPat.find(txt) != null){
        val res = minPat.find(txt)?.value.toString()
        val result = getResult(res)
        txt = txt.replace(res, result.toString())
    }

    return txt.toDouble()

}


fun getResult(txt:String):Double{

    val numbers = txt.split(simbPat).map { x -> x.toDouble() }
    val symbol = simbPat.find(txt)?.value

    when(symbol){
        "+" -> return numbers[0] + numbers[1]
        "-" -> return numbers[0] - numbers[1]
        "x" -> return numbers[0] * numbers[1]
        "/" -> return numbers[0] / numbers[1]
    }
    return 0.0
}

