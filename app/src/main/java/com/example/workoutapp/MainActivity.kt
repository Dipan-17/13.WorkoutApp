package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init the binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        //set the layout of activity to the root (i.e. the constraint layout)
        setContentView(binding?.root)

        //start button clicker
        binding?.flStart?.setOnClickListener {
            val intent=Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            val intent=Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener {
            val intent=Intent(this,HistoryActivity::class.java)
            startActivity(intent)

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }

    private fun makeToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}