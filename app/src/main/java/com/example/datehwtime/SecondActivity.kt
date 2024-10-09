package com.example.datehwtime

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.datehwtime.databinding.ActivitySecondBinding
import java.time.LocalDate
import java.time.Period


class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val person: Person = intent.extras?.getSerializable("person") as Person

        binding.secondActivityLastNameTV.text = "Фамилия: " + person.lastName
        binding.secondActivityFirstNameTV.text = "Имя: " + person.firstName
        binding.secondActivityPhoneTV.text = "Номер телефона: " + person.phone
        binding.secondActivityDateTV.text = person.data
        binding.secondActivityImageViewIV.setImageURI(Uri.parse(person.image))

        //Count the age
        val dateTriple = reformateDateBirth(person.data)
        val selectedBirthDate = LocalDate.of(dateTriple.third, dateTriple.second, dateTriple.first)
        val age = Period.between(selectedBirthDate, LocalDate.now()).years.toString()

        binding.secondActivityAgeTV.text = "Возраст: " + age


        //Count the date before the birthday
        val futureBirthday = if (LocalDate.now().monthValue > dateTriple.second) {
            LocalDate.of(LocalDate.now().year + 1, dateTriple.second, dateTriple.first)
        } else {
            LocalDate.of(LocalDate.now().year, dateTriple.second, dateTriple.first)
        }
        LocalDate.of(dateTriple.third, dateTriple.second, dateTriple.first)
        val toBirthDayMonth = Period.between(LocalDate.now(), futureBirthday).months
        val toBirthDayDays = Period.between(LocalDate.now(), futureBirthday).days

        binding.secondActivityDateTV.text =
            "До дня рождения $toBirthDayMonth месяц(ев) $toBirthDayDays дней"


        //Exit from app
        binding.secondActivityExitButtonBTN.setOnClickListener {
            this.finishAffinity()
        }
    }

    //Convert the date of birth from a string to integers
    fun reformateDateBirth(date: String): Triple<Int, Int, Int> {
        val dateBirth = date.split("-")
        val day = dateBirth[0].toInt()
        val month = dateBirth[1].toInt()
        val year = dateBirth[2].toInt()
        val trip: Triple<Int, Int, Int> = Triple(day, month, year)
        return trip
    }


}