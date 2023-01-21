package com.arturodev.imc_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentWeight: Int = 50
    private var currentHeight : Int = 120
    private var currentAge: Int = 18


    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubstrac: FloatingActionButton
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var  btnAddAge : FloatingActionButton
    private lateinit var btnSubstracAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate : Button

    companion object{
        const val IMC_KEY = "IMC_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()
        initUI()
    }


    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubstrac = findViewById(R.id.btnSubstract)
        btnAdd = findViewById(R.id.btnAdd)
        tvWeight = findViewById(R.id.tvWeight)
        btnAddAge = findViewById(R.id.btnAddAge)
        btnSubstracAge = findViewById(R.id.btnSubstractAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)

    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            changeGender()
            setGenderColor()

        }
        viewFemale.setOnClickListener {
            changeGender()
            setGenderColor()

        }
        rsHeight.addOnChangeListener { _, value, _ ->

            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }

        btnAdd.setOnClickListener {
            currentWeight += 1
            setWeight()
        }
        btnSubstrac.setOnClickListener {
            currentWeight -= 1
            setWeight()
        }
        btnAddAge.setOnClickListener {
            currentAge += 1
            setAge()
        }
        btnSubstracAge.setOnClickListener {
            currentAge -= 1
            setAge()
        }
        btnCalculate.setOnClickListener {
            val result = calculateImc()
            navigateToResult(result)
        }
    }

    private fun navigateToResult(result: Double) {
        val intent = Intent(this,ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun calculateImc(): Double{
        val df =DecimalFormat("#.##")
        val imc = currentWeight / ((currentHeight.toDouble()/100) * (currentHeight.toDouble()/100))
        return df.format(imc).toDouble()

    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }



    private fun changeGender() {
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))

    }

    private fun getBackgroundColor(isSelectedComponent: Boolean): Int {
        val colorReference = if (isSelectedComponent) {
            R.color.background_component_selected
        } else {
            R.color.background_component
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }

}