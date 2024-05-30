package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.HistoryActivityBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding:HistoryActivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= HistoryActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="HISTORY OF PREVIOUS WORKOUTS"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val historyDAO=(application as WorkoutApp).db.historyDao()
        getAllDateFromStorage(historyDAO)
    }


    private fun getAllDateFromStorage(historyDAO: HistoryDAO){
        Log.e("Fetching all dates","Fetching all dates from the storage in history activity")
        lifecycleScope.launch {
            historyDAO.fetchAllDates().collect(){allCompletedDateList ->
                for(i in allCompletedDateList){
                    Log.e("Date individual",""+i.date.toString())
                }

                //convert list to array list to pass
                //val list=ArrayList(allCompletedDateList)

            }
        }
        Log.e("Fetching all dates","Fetching all dates completed")
    }

    override fun onDestroy() {
        binding=null
        super.onDestroy()
    }
}