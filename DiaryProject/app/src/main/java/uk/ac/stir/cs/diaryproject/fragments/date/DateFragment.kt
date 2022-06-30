package uk.ac.stir.cs.diaryproject.fragments.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uk.ac.stir.cs.diaryproject.R
import uk.ac.stir.cs.diaryproject.databinding.FragmentDateBinding
import uk.ac.stir.cs.diaryproject.viewmodel.SharedViewModel


// This class provides a calenderView that allows the user to select a date from the calender
// Takes the user to the next page (AddFragment)
class DateFragment : Fragment() {

    private var _binding: FragmentDateBinding? = null
    private val binding get() = _binding!!

    private lateinit var mSharedViewModel: SharedViewModel

    private lateinit var dateIntViews: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        mSharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        _binding = FragmentDateBinding.inflate(inflater, container, false)

        dateIntViews = binding.dateIntView

        binding.btnDate.setOnClickListener {
            // call function "sendMessage" defined in SharedViewModel
            // to store the value in message.
            val selectedDate = dateIntViews.text.toString()
            mSharedViewModel.submitDate(selectedDate)
            findNavController().navigate(R.id.action_dateFragment_to_addFragment)
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            Toast.makeText(
                requireContext(),
                "The selected date is $dayOfMonth.${year + 1}.$month",
                Toast.LENGTH_SHORT
            ).show()
            dateIntViews.text = "$dayOfMonth / $month / ${year + 1}"
        }

        return binding.root
    }
}