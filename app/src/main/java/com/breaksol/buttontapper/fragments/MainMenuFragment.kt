package com.breaksol.buttontapper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.breaksol.buttontapper.activities.MainActivity
import com.breaksol.buttontapper.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.bPlay.setOnClickListener {
            val activity: MainActivity = activity as MainActivity
            activity.replaceFragments(ButtonLayoutFragment::class.java)
        }
        binding.bSettings.setOnClickListener {
            val activity: MainActivity = activity as MainActivity
            activity.replaceFragments(SettingsFragment::class.java)
        }
        binding.bRecords.setOnClickListener {
            val activity: MainActivity = activity as MainActivity
            activity.replaceFragments(RecordsFragment::class.java)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MainMenuFragment()
    }
}