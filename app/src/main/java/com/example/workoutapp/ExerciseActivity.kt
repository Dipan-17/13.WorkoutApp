package com.example.workoutapp

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import android.speech.tts.TextToSpeech
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding?=null

    //to set the rest timer
    private var restTimer:CountDownTimer ?=null
    private var restProgress:Int=0

    //to set the exercise timer
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress:Int=0

    //to iterate through the list of available exercises
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    //for text to speech
    private var tts:TextToSpeech?=null

    //sound player to announce exercise end
    private var  player:MediaPlayer?=null

    //recycler view for status
    private var exerciseStatusAdapter:ExerciseStatusAdapter?=null

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

        //get the exercise list
        exerciseList=Constants.defaultExerciseList()
        //init the text to speech
        tts=TextToSpeech(this,this)

        setupRestView()
        setUpExerciseStatusRecyclerView()
    }

    private fun setUpExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseStatusAdapter=ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseStatusAdapter
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

        speakOut("Now Rest for 10 seconds")

        try{
            val soundURI= Uri.parse("android.resource://com.example.workoutapp/"+ R.raw.press_start)
            player=MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }




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

        restTimer=object:CountDownTimer(5000,1000){

            override fun onTick(p0: Long) {
                restProgress++
                binding?.ProgressBar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                Log.d("Layout change","Rest timer is finished, now replacing the frame with exercise")

                //once rest is finished -> Goto next exercise
                currentExercisePosition++

                //to mark the next circle as white
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()

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

        speakOut(exerciseList!![currentExercisePosition].getName().toString())

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
                    //to mark the next circle as white
                    exerciseList!![currentExercisePosition].setCompleted(true)
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseStatusAdapter!!.notifyDataSetChanged()

                    setupRestView()
                }else{
                    makeToast("Exercises Completed")
                }
            }

        }.start()
    }



    //for tts
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
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
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        if(player!=null){
            player!!.stop()
        }


        binding=null
    }

    private fun makeToast(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

}