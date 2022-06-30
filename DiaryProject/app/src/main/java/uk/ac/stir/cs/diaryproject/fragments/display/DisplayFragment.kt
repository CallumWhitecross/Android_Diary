package uk.ac.stir.cs.diaryproject.fragments.display

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uk.ac.stir.cs.diaryproject.R
import uk.ac.stir.cs.diaryproject.viewmodel.EntryViewModel
import uk.ac.stir.cs.diaryproject.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {

    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!

    private lateinit var mEntryViewModel: EntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDisplayBinding.inflate(inflater, container, false)

        // Recyclerview
        val adapter = EntryAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mEntryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]
        // Whenever a value changes within the database this will observe any changes and apply
        // them to the display
        mEntryViewModel.readAllData.observe(viewLifecycleOwner, Observer { entry ->
            adapter.setData(entry)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_displayFragment_to_dateFragment)
        }

        // Add Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteAll()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        // When the user clicks on the 'delete' icon we want to ask
        // the user if they are sure
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mEntryViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully removed all entries",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all entries?")
        builder.setMessage("Are you sure you want to delete all entries?")
        builder.create().show()
    }

}