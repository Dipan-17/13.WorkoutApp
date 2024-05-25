package com.example.workoutapp

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
            makeToast("Start Clicked")
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