package com.example.quanlysinhvien.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quanlysinhvien.database.StudentDatabase
import com.example.quanlysinhvien.databinding.FragmentAddstudentBinding
import com.example.quanlysinhvien.entity.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class AddStudentFragment: Fragment() {
    private lateinit var database: StudentDatabase
    private lateinit var binding: FragmentAddstudentBinding
    private var selectedImageBitmap: Bitmap? = null
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            handleGalleryResult(data)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddstudentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSelectImage.setOnClickListener{
            pickImageFromGallery()
        }
        binding.btnBack.setOnClickListener{
            goBack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                goBack()
            }

        })
        super.onViewCreated(view, savedInstanceState)
        database = StudentDatabase.getInstance(requireContext())
        binding.btnAdd.setOnClickListener {
            val name = binding.addNameEditText.text.toString()
            val dateOfBirth = binding.addBirthDayEditText.text.toString()
            val school = binding.addSchoolEditText.text.toString()
            val address = binding.addAddressEditText.text.toString()
            // Lưu thông tin Sinh viên và ảnh đã chọn vào cơ sở dữ liệu
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    val student = Student(name = name,dateOfBirth = dateOfBirth,school = school,address = address, avatarUrl =  selectedImageBitmap)
                    database.StudentDao().insert(student)
                    showToast("Thêm sinh viên thành công")
                    requireActivity().onBackPressed()
                }
            }
        }
    }
    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)

    }
    private fun handleGalleryResult(data:Intent?){
        if(data==null) return
        val selectedImageUri = data.data
        selectedImageUri?.let {
            val inputStream: InputStream? = context?.contentResolver?.openInputStream(it)
            selectedImageBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            // Hiển thị ảnh đã chọn lên ImageView
            selectedImageBitmap?.let { bitmap -> binding.imageView.setImageBitmap(bitmap) }
        }
    }
    private fun showToast(message: String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }
    private fun goBack() {
        // Quay lại màn hình trước đó
        parentFragmentManager.popBackStack()
    }
}