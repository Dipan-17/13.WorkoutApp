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


    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

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

        exerciseList=Constants.defaultExerciseList()

        setupRestView()
    }

    private fun setupRestView(){
        Log.i("Activity Change","Rest view is being setup")

        //show the rest time stuff
        binding?.flRestView?.visibility= View.VISIBLE
        binding?.tvTitle?.visibility=View.VISIBLE
        binding?.tvUpcomingLabel?.visibility=View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.VISIBLE

        //hide the exercise stuff
        binding?.tvExerciseName?.visibility=View.INVISIBLE
        binding?.flExerciseView?.visibility=View.INVISIBLE
        binding?.ivImage?.visibility=View.INVISIBLE

        //set the next exercise name
        binding?.tvUpcomingExerciseName?.text=exerciseList!![currentExercisePosition+1].getName().toString()

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

        restTimer=object:CountDownTimer(1000,1000){

            override fun onTick(p0: Long) {
                restProgress++
                binding?.ProgressBar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                Log.d("Layout change","Rest timer is finished, now replacing the frame with exercise")

                //once rest is finished -> Goto next exercise
                currentExercisePosition++

                setUpExerciseView()
            }

        }.start()
    }


    private fun setUpExerciseView(){
        Log.i("Activity Change","Exercise view is being setup")

        //hide the rest time stuff
        binding?.flRestView?.visibility= View.INVISIBLE
        binding?.tvTitle?.visibility=View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility=View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.INVISIBLE

        //display the exercise stuff
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE

        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text=exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }
    private fun setExerciseProgressBar(){
        binding?.ProgressBarExercise?.progress=exerciseProgress

        //Every 1000 milliseconds the code inside the CountDownTimer class is called
        //for a total of 10000 milliseconds
        //once time runs out onFinish is called

        Log.i("Timer","Exercise timer started")
        exerciseTimer=object:CountDownTimer(3000,1000){

            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.ProgressBarExercise?.progress=30-exerciseProgress
                binding?.tvTimerExercise?.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {
                //makeToast("30 Seconds are over -> Go to Rest")
                if(currentExercisePosition<exerciseList?.size!!-1){
                    setupRestView()
                }else{
                    makeToast("Exercises Completed")
                }
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