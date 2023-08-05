package com.example.quanlysinhvien.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quanlysinhvien.entity.Student
import com.example.quanlysinhvien.databinding.ItemRecyclerviewBinding

class StudentAdapter : androidx.recyclerview.widget.ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StudentViewHolder(private val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.textViewName.text = student.name
            binding.textViewBirthday.text = student.dateOfBirth
            binding.textViewDescription.text = student.school
            binding.textViewAddress.text = student.address
            Glide.with(binding.imageViewRounded).load(student.avatarUrl).into(binding.imageViewRounded)
        }
    }

    class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }
}
