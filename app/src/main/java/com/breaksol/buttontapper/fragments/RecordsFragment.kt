package com.breaksol.buttontapper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.breaksol.buttontapper.activities.MainActivity
import com.breaksol.buttontapper.adapters.RecordAdapter
import com.breaksol.buttontapper.database.AppDatabase
import com.breaksol.buttontapper.databinding.FragmentRecordsBinding
import kotlinx.coroutines.launch

class RecordsFragment : Fragment() {

    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecordAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.homeButton.setOnClickListener {
            val activity: MainActivity = activity as MainActivity
            activity.replaceFragments(MainMenuFragment::class.java)
        }

        val db = Room.databaseBuilder(
                requireActivity().applicationContext,
                AppDatabase::class.java, "recordsDB"
        ).build()

        val recordDao = db.recordDao()

        viewLifecycleOwner.lifecycleScope.launch {
            val recordsList = recordDao.getAll()
            if (recordsList.isNotEmpty()) {
                adapter = RecordAdapter(recordsList, requireContext())
                binding.rwRecords.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                binding.rwRecords.adapter = adapter
                showViews()
            } else {
                binding.pbLoading.visibility = View.GONE
                binding.tvNoRecords.visibility = View.VISIBLE
            }

        }

        return view
    }

    private fun showViews() {
        binding.pbLoading.visibility = View.GONE
        binding.rwRecords.visibility = View.VISIBLE
        binding.tvColumnsHeader.visibility = View.VISIBLE
        binding.tvPlaceHeader.visibility = View.VISIBLE
        binding.tvRowsHeader.visibility = View.VISIBLE
        binding.tvScoreHeader.visibility = View.VISIBLE
        binding.tvTimeHeader.visibility = View.VISIBLE
        binding.vDivider.visibility = View.VISIBLE
        binding.tvRecordsTitle.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}