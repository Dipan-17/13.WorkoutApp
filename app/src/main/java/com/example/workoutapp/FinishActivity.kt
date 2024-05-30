package com.example.workoutapp

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {

    private var binding:ActivityFinishBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarFinishActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            //onBackPressed()
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        //DAO to the database is required for anything to do in database
        val historyDAO=(application as WorkoutApp).db.historyDao()
        addDateToDatabase(historyDAO)
    }


    private fun addDateToDatabase(historyDAO: HistoryDAO){
        //prepare the date
        val c= Calendar.getInstance()
        val dateTime=c.time
        Log.e("Date",""+dateTime)

        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date=sdf.format(dateTime)
        Log.e("Formatted date",""+date)

        lifecycleScope.launch {
            historyDAO.insert(HistoryEntity(date))
            Log.e(
                "Date: ",
                "Added...."
            )
        }
        Toast.makeText(applicationContext,"Record added to database",Toast.LENGTH_SHORT).show()
    }
}