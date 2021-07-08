package com.breaksol.buttontapper.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.breaksol.buttontapper.R
import com.breaksol.buttontapper.activities.MainActivity
import com.breaksol.buttontapper.database.AppDatabase
import com.breaksol.buttontapper.database.Record
import com.breaksol.buttontapper.databinding.FragmentButtonLayoutBinding
import com.breaksol.buttontapper.utils.PreferencesUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class ButtonLayoutFragment : Fragment() {

    private var _binding: FragmentButtonLayoutBinding? = null
    private val binding get() = _binding!!

    // 10 secs = 11000.
    private var time by Delegates.notNull<Int>()

    private var isGameGoing = false
    private var isCountDownGoing = false

    private var secondsLeft = 0
    private var countDownTimer = object : CountDownTimer(3500, 100) {
        override fun onTick(ms: Long) {
            if ((ms.toFloat() / 1000.0f).roundToInt() != secondsLeft) {
                secondsLeft = (ms.toFloat() / 1000.0f).roundToInt()
                if (secondsLeft > 0) {
                    binding.tvCountdownTimer.text = "$secondsLeft"
                } else {
                    isCountDownGoing = false
                    binding.clCountdownLayout.visibility = View.GONE
                    binding.clMainFragmentLayout.isClickable = true
                    startGame()
                }
            }
        }

        override fun onFinish() {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentButtonLayoutBinding.inflate(inflater, container, false)
        val view = binding.root

        val task = "Tap <u><font color='#FFEB3B'>golden</font></u> buttons!"
        binding.tvTask.text = Html.fromHtml(task)
        binding.buttonLayout.legitClicksTextView = binding.legitClicks

        setListeners()
        setCountDownLayout()

        return view
    }

    private fun setListeners() {
        time = (PreferencesUtils.getTime(requireContext()) * 1000) + 1000

        binding.timer.typeface = ResourcesCompat.getFont(requireContext(), R.font.adrip1)

        binding.timer.currentTime = (time - 1).toLong()
        binding.timer.setOnChronometerTickListener {
            if (SystemClock.elapsedRealtime() - it.base >= -1000)  {
                it.stop()
                finishGame()
            }
        }

        binding.homeButton.setOnClickListener {
            binding.timer.stop()
            countDownTimer.cancel()
            openMenu()
        }

        binding.restartButton.setOnClickListener {
            restartGame()
        }

        binding.endingHomeButton.setOnClickListener {
            openMenu()
        }

        binding.endingRestartButton.setOnClickListener {
            restartGame()
        }
    }

    private fun setCountDownLayout() {
        isCountDownGoing = true
        countDownTimer.start()
    }

    private fun startGame() {
        isGameGoing = true
        binding.buttonLayout.enableButtons(true)
        binding.buttonLayout.lightUpRandomButton(true)
        binding.timer.base = SystemClock.elapsedRealtime() + time
        binding.timer.start()
        binding.restartButton.isEnabled = true
        binding.homeButton.isEnabled = true
    }

    private fun finishGame() {
        isGameGoing = false
        binding.buttonLayout.enableButtons(false)
        binding.homeButton.isEnabled = false
        binding.restartButton.isEnabled = false
        checkAndInsertRecord()
        showEndingScreen()
    }

    private fun showEndingScreen() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1500)
            binding.endingHomeButton.visibility = View.VISIBLE
            binding.endingRestartButton.visibility = View.VISIBLE
            binding.tvTask.text = "Result"
            binding.tvCountdownTimer.text = binding.legitClicks.text
            binding.clCountdownLayout.visibility = View.VISIBLE
        }
    }

    private fun checkAndInsertRecord() {
        val db = Room.databaseBuilder(
                requireActivity().applicationContext,
                AppDatabase::class.java, "recordsDB"
        ).build()

        val recordDao = db.recordDao()

        val record = Record(binding.buttonLayout.legitClicks, PreferencesUtils.getRows(requireContext()),
                PreferencesUtils.getColumns(requireContext()), PreferencesUtils.getTime(requireContext()))

        viewLifecycleOwner.lifecycleScope.launch {
            if (recordDao.getAll().size < 10) {
                recordDao.insertRecord(record)
            } else {
                val worstResult = recordDao.getWorseRecord(record.result)
                if (worstResult.isNotEmpty()) {
                    recordDao.delete(worstResult[0])
                    recordDao.insertRecord(record)
                }
            }

        }
    }

    private fun openMenu() {
        val activity: MainActivity = activity as MainActivity
        activity.replaceFragments(MainMenuFragment::class.java)
    }

    private fun restartGame() {
        val activity: MainActivity = activity as MainActivity
        activity.replaceFragments(ButtonLayoutFragment::class.java)
    }

    override fun onResume() {
        super.onResume()
        if (isCountDownGoing) {
            countDownTimer.start()
        }
        if (isGameGoing) {
            binding.timer.start()
        }
    }

    override fun onPause() {
        if (isCountDownGoing) {
            countDownTimer.cancel()
        }
        if (isGameGoing) {
            binding.timer.stop()
        }
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}