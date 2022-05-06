package com.example.buscaminas

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buscaminas.databinding.ActivityConfigBinding

class ActivityConfig: AppCompatActivity(){
    private lateinit var binding: ActivityConfigBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.radioButtonPM15.isChecked = true
        binding.radioButtonTC5.isChecked = true
        binding.startButton.setOnClickListener{startGame()}
    }

    private fun startGame(){
        val playerName = binding.editTextName.text
        if (playerName.isEmpty()){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            binding.editTextName.error = "This field must be filled"
            return
        }
        val gridSize = checkRadioButton(binding.radioGroupGrid.checkedRadioButtonId)
        val time = binding.checkbox.isChecked
        val bombPercentage = checkRadioButton(binding.radioGroupBomb.checkedRadioButtonId)
        if (gridSize == null || bombPercentage == null){
            return
        }
//        Toast.makeText(this, "Todo Ok", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ActivityGame::class.java)
        val bundle = Bundle()
        bundle.putString("playerName", playerName.toString())
        bundle.putInt("gridSize", Integer.parseInt(gridSize.toString()))
        bundle.putString("time", time.toString())
        bundle.putDouble("bombPercentage", bombPercentage.toString().toDouble())
        intent.putExtras(bundle)
        startActivity(intent)
        /*println("playerName $playerName")
        println("gridSize $gridSize")
        println("time $time")
        println("bombPercentage $bombPercentage")*/
        finish()
    }

    private fun checkRadioButton(radioButtonId: Int): CharSequence? {
        val radioButton = findViewById<RadioButton>(radioButtonId)
        return if (radioButton != null){
            radioButton.text
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            null
        }
    }
}