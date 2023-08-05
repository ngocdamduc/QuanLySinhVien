package com.example.quanlysinhvien.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quanlysinhvien.dao.StudentDao
import com.example.quanlysinhvien.entity.Student
import com.example.quanlysinhvien.typeconverter.BitmapTypeConverter

@Database(entities = [Student::class], version = 1)
@TypeConverters(BitmapTypeConverter::class)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun StudentDao(): StudentDao
    companion object {
        private var instance: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                ).build()
            }
            return instance as StudentDatabase
        }
    }
}