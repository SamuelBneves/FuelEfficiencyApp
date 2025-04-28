package com.example.fuelefficiencyapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var editDistance: EditText
    private lateinit var editFuel: EditText
    private lateinit var editSpeed: EditText
    private lateinit var btnCalculate: Button
    private lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editDistance = findViewById(R.id.editDistance)
        editFuel = findViewById(R.id.editFuel)
        editSpeed = findViewById(R.id.editSpeed)
        btnCalculate = findViewById(R.id.btnCalculate)
        textResult = findViewById(R.id.textResult)
        val distanceInput = findViewById<EditText>(R.id.editDistance)
        val fuelInput = findViewById<EditText>(R.id.editFuel)
        val speedInput = findViewById<EditText>(R.id.editSpeed)

        applyDecimalMask(distanceInput)
        applyDecimalMask(fuelInput)
        applyDecimalMask(speedInput)

        btnCalculate.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        val distance = editDistance.text.toString().toDoubleOrNull()
        val fuel = editFuel.text.toString().toDoubleOrNull()
        val speed = editSpeed.text.toString().toDoubleOrNull()

        if (distance == null || fuel == null || speed == null || fuel == 0.0) {
            textResult.text = "Por favor, insira valores válidos."
            return
        }

        val consumption = distance / fuel
        val idealSpeed = getIdealSpeed(speed)

        textResult.text = "Consumo médio: %.2f km/L\nVelocidade sugerida: %.0f km/h".format(consumption, idealSpeed)
    }

    private fun getIdealSpeed(currentSpeed: Double): Double {
        return when {
            currentSpeed < 40 -> 60.0
            currentSpeed in 40.0..90.0 -> currentSpeed
            else -> 90.0
        }
    }

    fun applyDecimalMask(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    editText.removeTextChangedListener(this)

                    val cleanString = s.toString().replace(",", "").replace(".", "")

                    val parsed = cleanString.toDoubleOrNull() ?: 0.0
                    val formatted = NumberFormat.getNumberInstance(Locale.US).format(parsed / 100)

                    current = formatted
                    editText.setText(formatted)
                    editText.setSelection(formatted.length)

                    editText.addTextChangedListener(this)
                }
            }
        })
    }
}
