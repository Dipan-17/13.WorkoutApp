package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
                if(allCompletedDateList.isNotEmpty()){
                    val list=ArrayList(allCompletedDateList)
                    populateRecyclerView(list,historyDAO)
                }else{
                    binding?.rvHistory?.visibility=View.GONE
                    binding?.tvNoDataAvailable?.visibility=View.VISIBLE
                    binding?.tvHistory?.visibility=View.GONE
                    Toast.makeText(this@HistoryActivity,"No dates to display",Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.e("Fetching all dates","Fetching all dates completed")
    }

    private suspend fun populateRecyclerView(historyList:ArrayList<HistoryEntity>,historyDAO: HistoryDAO){
        val itemHistoryAdapter=HistoryAdapter(historyList)
        binding?.rvHistory?.layoutManager=LinearLayoutManager(this)
        binding?.rvHistory?.adapter=itemHistoryAdapter
        binding?.rvHistory?.visibility= View.VISIBLE
        binding?.tvNoDataAvailable?.visibility=View.GONE
        binding?.tvHistory?.visibility=View.VISIBLE
    }

    override fun onDestroy() {
        binding=null
        super.onDestroy()
    }
}