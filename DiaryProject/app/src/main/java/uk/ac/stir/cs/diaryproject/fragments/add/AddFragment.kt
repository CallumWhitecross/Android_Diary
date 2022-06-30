package uk.ac.stir.cs.diaryproject.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.selects.select
import uk.ac.stir.cs.diaryproject.R
import uk.ac.stir.cs.diaryproject.model.Entries
import uk.ac.stir.cs.diaryproject.viewmodel.EntryViewModel
import uk.ac.stir.cs.diaryproject.databinding.FragmentAddBinding
import uk.ac.stir.cs.diaryproject.viewmodel.SharedViewModel

// This is the class to add the Date taken from DateFragment and
// the entry entered by the user to the Room database
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mEntryViewModel: EntryViewModel
    private lateinit var mSharedViewModel: SharedViewModel

    private lateinit var dateView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        dateView = binding.dateView

        // UserViewModel
        mEntryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]
        mSharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        mSharedViewModel.date.observe(viewLifecycleOwner, Observer {
            // updating data in displayMsg
            dateView.text = it
        })

        binding.btnAdd.setOnClickListener {
            insertEntryToDiary()
        }

        binding.btnCancel.setOnClickListener {
            cancel()
        }

        return binding.root
    }

    private fun insertEntryToDiary() {
        val date = dateView.text.toString()
        val abstract = editExtract.text.toString()

        if(inputCheck(date, abstract)){
            // Create entry object
            val entry = Entries(0, date, abstract)
            // Add Data to Database
            mEntryViewModel.addEntry(entry)
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()
            // Navigate back to home
            findNavController().navigate(R.id.action_addFragment_to_displayFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(date: String, abstract: String): Boolean {
        return !(TextUtils.isEmpty(date) && TextUtils.isEmpty(abstract))
    }

    private fun cancel() {
        Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addFragment_to_displayFragment)
    }
}