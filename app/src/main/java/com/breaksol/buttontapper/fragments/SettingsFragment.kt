package com.breaksol.buttontapper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.breaksol.buttontapper.PreferencesUtils
import com.breaksol.buttontapper.R
import com.breaksol.buttontapper.activities.MainActivity
import com.breaksol.buttontapper.databinding.FragmentButtonLayoutBinding
import com.breaksol.buttontapper.databinding.FragmentSettingsBinding
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvRowAmount.text = "Rows: ${PreferencesUtils.getRows(requireContext()).toString()}"
        binding.tvColumnAmount.text = "Columns: ${PreferencesUtils.getColumns(requireContext()).toString()}"
        binding.sbRows.progress = PreferencesUtils.getRows(requireContext()) - 2
        binding.sbColumns.progress = PreferencesUtils.getColumns(requireContext()) - 2

        binding.homeButton.setOnClickListener {
            val activity: MainActivity = activity as MainActivity
            activity.replaceFragments(MainMenuFragment::class.java)
        }

        binding.sbRows.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            var currentValue by Delegates.notNull<Int>()

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                currentValue = i
                binding.tvRowAmount.text = "Rows: ${i + 2}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                PreferencesUtils.saveRows(requireContext(), currentValue + 2)
            }
        })

        binding.sbColumns.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            var currentValue by Delegates.notNull<Int>()

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                currentValue = i
                binding.tvColumnAmount.text = "Columns: ${i + 2}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                PreferencesUtils.saveColumns(requireContext(), currentValue + 2)
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SettingsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}