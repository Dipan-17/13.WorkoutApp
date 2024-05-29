package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.BmiActivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding:BmiActivityBinding?=null

    //for radio button
    companion object{
        //will let us know which is active
        private const val METRIC_UNITS_VIEW="METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW="US_UNIT_VIEW"
    }
    //In Kotlin, a companion object is a special object which is bound to (i.e. is a part of) a class definition

    private var currentVisibleView:String= METRIC_UNITS_VIEW //holds value to make selected view visible

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=BmiActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        //shows us the back button
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="Calculate BMI"
        }
        //functionality of the back button
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        //by default set the metric units visible
        makeVisibleMetricUnitsView()
        //radio group on click listener
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId:Int ->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun calculateUnits(){
        if(currentVisibleView== METRIC_UNITS_VIEW){
            if(validateMetricUnit()){
                calculateBMIMetric()
            }else{
                makeToast("Please enter values first")
            }
        }else{
            if(validateUsUnits()){
                calculateBMIUS()
            }else{
                makeToast("Please enter values first")
            }
        }
    }



    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW // Current View is updated here.
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE // METRIC  Height UNITS VIEW is Visible
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE // METRIC  Weight UNITS VIEW is Visible
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE // make weight view Gone.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE // make height feet view Gone.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE // make height inch view Gone.

        binding?.etMetricUnitHeight?.text!!.clear() // height value is cleared if it is added.
        binding?.etMetricUnitWeight?.text!!.clear() // weight value is cleared if it is added.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE // METRIC  Height UNITS VIEW is InVisible
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE // METRIC  Weight UNITS VIEW is InVisible
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE // make weight view visible.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE // make height feet view visible.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE // make height inch view visible.

        binding?.etUsMetricUnitWeight?.text!!.clear() // weight value is cleared.
        binding?.etUsMetricUnitHeightFeet?.text!!.clear() // height feet value is cleared.
        binding?.etUsMetricUnitHeightInch?.text!!.clear() // height inch is cleared.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun calculateBMIMetric(){
        val heightValue:Float=binding?.etMetricUnitHeight?.text.toString().toFloat()/100
        val weightValue:Float=binding?.etMetricUnitWeight?.text.toString().toFloat()

        val bmi:Float=weightValue/(heightValue*heightValue)
        displayBMIMessage(bmi)

    }
    private fun calculateBMIUS(){
        val heightFeet:String=binding?.etUsMetricUnitHeightFeet?.text.toString()
        val heightInch:String=binding?.etUsMetricUnitHeightInch?.text.toString()
        val weightPounds:Float=binding?.etUsMetricUnitWeight?.text.toString().toFloat()

        val height:Float=heightInch.toFloat()+heightFeet.toFloat()*12

        val bmi=703*(weightPounds/(height*height))
        displayBMIMessage(bmi)
    }
    private fun displayBMIMessage(bmi:Float){
        binding?.llDiplayBMIResult?.visibility= View.VISIBLE

        val bmiLabel:String
        val bmiDescription:String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue // Value is set to TextView
        binding?.tvBMIType?.text = bmiLabel // Label is set to TextView
        binding?.tvBMIDescription?.text = bmiDescription // Description is set to TextView
    }

    //whether user has entered data or not
    //for metric units
    private fun validateMetricUnit():Boolean{
        var isValid=true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid=false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid=false
        }

        return isValid
    }

    //for us units
    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

    private fun makeToast(message:String){
        Toast.makeText(this@BMIActivity,message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}