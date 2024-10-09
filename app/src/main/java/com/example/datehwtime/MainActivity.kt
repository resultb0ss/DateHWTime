package com.example.datehwtime

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datehwtime.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val GALLERY_REQUEST = 300
    var photoUri: Uri? = null
    val person: Person? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Checking the validity of the fields
        binding.mainActivitySaveButtonBTN.setOnClickListener {

            if ((binding.mainActivityFirstNameEditTextET.text.isEmpty()) ||
                (binding.mainActivityLastNameEditTextET.text.isEmpty()) ||
                (binding.mainActivityDateOfBirthEditTextET.text.isEmpty()) ||
                (binding.mainActivityPhoneEditTextET.text.isEmpty())
            ) {

                Toast.makeText(
                    this, "Заполните обязательно все поля!!",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                val intent = Intent(this, SecondActivity::class.java)

                val firstName = binding.mainActivityFirstNameEditTextET.text.toString()
                val lastName = binding.mainActivityLastNameEditTextET.text.toString()
                val dateOfBirth = binding.mainActivityDateOfBirthEditTextET.text.toString()
                val phone = binding.mainActivityPhoneEditTextET.text.toString()
                val image = photoUri.toString()

                val person = Person(firstName, lastName, image, phone, dateOfBirth)

                intent.putExtra("person", person)
                startActivity(intent)
                editFieldsClear()

            }
        }

        //Call datetimepicker dialog and set Birthday
        binding.mainActivityDateOfBirthEditTextET.setOnClickListener {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" +
                            (monthOfYear + 1) + "-" + year)
                    binding.mainActivityDateOfBirthEditTextET.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }


        binding.mainActivityImageViewIV.setOnClickListener {
            val photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, GALLERY_REQUEST)
        }


        binding.mainActivityExitButtonBTN.setOnClickListener {
            this.finishAffinity()
        }

    }

    private fun editFieldsClear() {
        binding.mainActivityFirstNameEditTextET.text.clear()
        binding.mainActivityLastNameEditTextET.text.clear()
        binding.mainActivityPhoneEditTextET.text.clear()
        binding.mainActivityDateOfBirthEditTextET.text.clear()
        binding.mainActivityImageViewIV.setImageResource(R.drawable.baseline_person_4_24)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                photoUri = data?.data
                binding.mainActivityImageViewIV.setImageURI(photoUri)
            }
        }
    }


}