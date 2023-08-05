package com.example.quanlysinhvien

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quanlysinhvien.databinding.ActivityLoginBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener{
            val username = binding.usernameEdittext.text.toString()
            val password = binding.passwordEdittext.text.toString()
            if(checkCredentials(username,password))
            {
                showToast("Đăng nhập thành công")
                val intent = Intent(this, StudentListActivity::class.java)
                startActivity(intent)
                finish()
            } else
            {
                showToast("Đăng nhập thất bại")
            }
        }
    }
    private fun checkCredentials(username: String, password: String): Boolean {
        // Thay đổi các thông tin đăng nhập ở đây
        val fixedUsername = "admin"
        val fixedPassword = "12345"

        return username == fixedUsername && password == fixedPassword
    }
    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}