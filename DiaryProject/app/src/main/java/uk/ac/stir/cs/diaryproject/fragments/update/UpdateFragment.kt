package uk.ac.stir.cs.diaryproject.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_update.*
import uk.ac.stir.cs.diaryproject.R
import uk.ac.stir.cs.diaryproject.databinding.FragmentUpdateBinding
import uk.ac.stir.cs.diaryproject.model.Entries
import uk.ac.stir.cs.diaryproject.viewmodel.EntryViewModel

// This class allows the user to update a currently existing entry in the
// diary database
// Also allows the user to delete the currently selected diary entry.
class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var mEntryViewModel: EntryViewModel

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        binding.updateDateView.setText(args.currentEntry.date)
        binding.updateEditExtract.setText(args.currentEntry.abstract_)

        mEntryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]

        binding.btnUpdate.setOnClickListener {
            updateItem()
        }

        binding.btnCancel.setOnClickListener {
            cancel()
        }

        // Add Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        val entry = updateEditExtract.text.toString()

        if(inputCheck(entry)) {
            // Create Entry Object
            val updatedEntry = Entries(args.currentEntry.entID, args.currentEntry.date, entry)
            // Update Entry
            mEntryViewModel.updateEntries(updatedEntry)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_displayFragment)
        } else {
            Toast.makeText(requireContext(), "Please complete all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(abstract: String): Boolean {
        return !(TextUtils.isEmpty(abstract))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteEntry()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteEntry() {
        // When the user clicks on the 'delete' icon we want to ask
        // the user if they are sure
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mEntryViewModel.deleteEntry(args.currentEntry)
            Toast.makeText(
                requireContext(),
                "Successfully removed entry on: ${args.currentEntry.date}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_displayFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Entry on ${args.currentEntry.date}?")
        builder.setMessage("Are you sure you want to delete this entry?")
        builder.create().show()
    }

    private fun cancel() {
        Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_displayFragment)
    }
}