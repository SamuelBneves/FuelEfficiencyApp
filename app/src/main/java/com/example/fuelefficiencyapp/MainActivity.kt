package com.example.fuelefficiencyapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

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
}
