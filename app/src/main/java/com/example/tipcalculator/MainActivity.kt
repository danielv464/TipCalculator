package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //Initializes variables
    lateinit var billAmount: EditText
    lateinit var percent: TextView
    lateinit var tipAmount: TextView
    lateinit var totalAmount: TextView
    lateinit var tipAdjuster: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Gets variables from views
        billAmount = findViewById(R.id.billAmt)
        percent = findViewById(R.id.percent)
        tipAmount = findViewById(R.id.tipAmt)
        totalAmount = findViewById(R.id.totalAmt)
        tipAdjuster = findViewById(R.id.tipSlider)

        tipAdjuster.max = 100 //Sets max for tip
        tipAdjuster.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, user: Boolean) {
                percent.text = "$progress%" //Shows the percentage change when you slide the slider
                calculateTipAndTotal() // Calls function to update tip and total amounts
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        billAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateTipAndTotal()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun calculateTipAndTotal() {
        // Uses local variables to update original ones
        val bill = billAmount.text.toString().toDoubleOrNull() ?: 0.0
        val tipPercentage = tipAdjuster.progress
        val tip = bill * tipPercentage / 100
        val total = bill + tip


        tipAmount.text = String.format("$%.2f", tip)
        totalAmount.text = String.format("$%.2f", total)
    }
}
