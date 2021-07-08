package com.breaksol.buttontapper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.breaksol.buttontapper.utils.PreferencesUtils
import com.breaksol.buttontapper.activities.MainActivity
import com.breaksol.buttontapper.databinding.FragmentSettingsBinding
import kotlin.properties.Delegates

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvRowAmount.text = "Rows: ${PreferencesUtils.getRows(requireContext())}"
        binding.tvColumnAmount.text = "Columns: ${PreferencesUtils.getColumns(requireContext())}"
        binding.tvTime.text = "Time: ${PreferencesUtils.getTime(requireContext())} seconds"
        binding.sbRows.progress = PreferencesUtils.getRows(requireContext()) - 2
        binding.sbColumns.progress = PreferencesUtils.getColumns(requireContext()) - 2
        binding.sbTime.progress = PreferencesUtils.getTime(requireContext()) / 5 - 1

        binding.homeButton.setOnClickListener {
            val activity: MainActivity = activity as MainActivity
            activity.replaceFragments(MainMenuFragment::class.java)
        }

        binding.sbRows.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            var currentValue = -1

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                currentValue = i + 2
                binding.tvRowAmount.text = "Rows: $currentValue"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                if (currentValue != -1) {
                    PreferencesUtils.saveRows(requireContext(), currentValue)
                }
            }
        })

        binding.sbColumns.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            var currentValue = -1

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                currentValue = i + 2
                binding.tvColumnAmount.text = "Columns: $currentValue"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                if (currentValue != -1) {
                    PreferencesUtils.saveColumns(requireContext(), currentValue)
                }
            }
        })

        binding.sbTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            var currentValue = -1

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                currentValue = (i + 1) * 5
                binding.tvTime.text = "Time: $currentValue seconds"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                if (currentValue != -1) {
                    PreferencesUtils.saveTime(requireContext(), currentValue)
                }
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}