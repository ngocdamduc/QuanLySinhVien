package com.example.quanlysinhvien.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val dateOfBirth: String,
    val school: String,
    val address: String,
    val avatarUrl: Bitmap?
)