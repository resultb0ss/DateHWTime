package com.example.datehwtime

import android.net.Uri
import java.io.Serializable

class Person (
    val firstName: String?,
    val lastName: String?,
    val image: String?,
    val phone: String?,
    val data: String
): Serializable {
}