package com.uas.halolaw.utils

import com.uas.halolaw.utils.Constants.OPEN_GOOGLE
import com.uas.halolaw.utils.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {

            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("hitung") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Yahhh, Lawbot tidak mengetahui hasilnya."
                }
            }

            //Halo
            message.contains("halo") || message.contains("hai") -> {
                when (random) {
                    0 -> "Haloo!"
                    1 -> "Haii"
                    else -> "error" }
            }

            //Apa kabar?
            message.contains("apa") && message.contains("kabar")->{
                when (random) {
                    0 -> "Lawbot sangat baik!"
                    1 -> "Lawbot sedang lapar..."
                    2 -> "Sangat baik, bagaimana dengan mu?"
                    else -> "error"
                }
            }



            //Waktu sekarang?
            message.contains("jam") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //tanggal sekarang?
            message.contains("tanggal") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("buka") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("cari")-> {
                OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "Maaf, Lawbot tidak mengerti..."
                    1 -> "Coba tanyakan hal lain kepada Lawbot"
                    else -> "error"
                }
            }
        }
    }
}