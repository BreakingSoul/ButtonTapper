package com.breaksol.buttontapper.activities

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.breaksol.buttontapper.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonLayout.legitClicksTextView = binding.legitClicks

        var secondsLeft = 0

        object : CountDownTimer(10500, 100) {
            override fun onTick(ms: Long) {
                if ((ms.toFloat() / 1000.0f).roundToInt() != secondsLeft) {
                    secondsLeft = (ms.toFloat() / 1000.0f).roundToInt()
                    if (secondsLeft >= 10) {
                        binding.timer.text = "0:$secondsLeft"
                    } else {
                        binding.timer.text = "0:0$secondsLeft"
                    }
                }
  //              Log.i("test", "ms=$ms till finished=$secondsLeft")
            }

            override fun onFinish() {
                binding.timer.text = "0:00"
                binding.buttonLayout.enableButtons(false)
            }
        }.start()

    }

}