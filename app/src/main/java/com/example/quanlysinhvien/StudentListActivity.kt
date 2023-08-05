package com.example.quanlysinhvien

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlysinhvien.adapter.StudentAdapter
import com.example.quanlysinhvien.database.StudentDatabase
import com.example.quanlysinhvien.databinding.ActivityListsBinding
import com.example.quanlysinhvien.fragments.AddStudentFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
class StudentListActivity: AppCompatActivity() {
    private lateinit var database: StudentDatabase
    private lateinit var binding: ActivityListsBinding
    private lateinit var adapter: StudentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StudentAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.floatingActionButton.setOnClickListener{
            openAddStudentFragment()
        }
        database = StudentDatabase.getInstance(applicationContext)
        loadStudents()

    }
    private fun loadStudents(){
        GlobalScope.launch {
            val students = withContext(Dispatchers.IO){
               database.StudentDao().getAllStudents()
            }
            withContext(Dispatchers.Main){
                adapter.submitList(students)
            }
        }
    }
    private fun openAddStudentFragment(){
        val fragment = AddStudentFragment()
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container, fragment)
        }
    }
}