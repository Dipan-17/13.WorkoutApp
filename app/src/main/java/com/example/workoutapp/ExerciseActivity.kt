package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExerciseBinding?=null

    //to set the rest timer
    private var restTimer:CountDownTimer ?=null
    private var restProgress:Int=0

    //to set the exercise timer
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init binding
        binding=ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //globally we disabled the action bar -> so here in this activity we are enabling the action bar
        //setup the action bar on the top of the screen
        setSupportActionBar(binding?.toolBarExercise)

        //shows us the back button
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        //functionality of the back button
        binding?.toolBarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }

        setupRestView()
    }

    private fun setupRestView(){
        Log.i("Activity Change","Rest view is being setup")

        //say we are going back and then coming back again -> we might clash with an existing timer
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }



        setRestProgressBar()
    }
    private fun setRestProgressBar(){
        binding?.ProgressBar?.progress=restProgress

        //Every 1000 milliseconds the code inside the CountDownTimer class is called
        //for a total of 10000 milliseconds
        //once time runs out onFinish is called

        Log.i("Timer","Rest timer started")

        restTimer=object:CountDownTimer(10000,1000){

            override fun onTick(p0: Long) {
                restProgress++
                binding?.ProgressBar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                Log.d("Layout change","Rest timer is finished, now replacing the frame with exercise")
                setUpExerciseView()
            }

        }.start()
    }


    private fun setUpExerciseView(){
        Log.i("Activity Change","Exercise view is being setup")

        binding?.flProgressBar?.visibility= View.INVISIBLE
        binding?.tvTitle?.text="Exercise Name"
        binding?.flExerciseView?.visibility=View.VISIBLE

        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        setExerciseProgressBar()
    }
    private fun setExerciseProgressBar(){
        binding?.ProgressBarExercise?.progress=exerciseProgress

        //Every 1000 milliseconds the code inside the CountDownTimer class is called
        //for a total of 10000 milliseconds
        //once time runs out onFinish is called

        Log.i("Timer","Exercise timer started")
        exerciseTimer=object:CountDownTimer(30000,1000){

            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.ProgressBarExercise?.progress=30-exerciseProgress
                binding?.tvTimerExercise?.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {
                makeToast("30 Seconds are over -> Go to Rest")
            }

        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()

        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }


        binding=null
    }

    private fun makeToast(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

}