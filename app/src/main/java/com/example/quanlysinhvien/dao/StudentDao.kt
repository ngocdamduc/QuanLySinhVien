package com.example.quanlysinhvien.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quanlysinhvien.entity.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert
    fun insert(student: Student): Unit

    @Query("SELECT * FROM STUDENT_TABLE")
    fun getAllStudents(): List<Student>
}