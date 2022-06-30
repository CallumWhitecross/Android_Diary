package uk.ac.stir.cs.diaryproject.fragments.display

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uk.ac.stir.cs.diaryproject.model.Entries
import uk.ac.stir.cs.diaryproject.databinding.AppRowBinding

class EntryAdapter: RecyclerView.Adapter<EntryAdapter.MyViewHolder>() {

    private var entryList = emptyList<Entries>()

    class MyViewHolder(val binding: AppRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AppRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = entryList[position]
        holder.binding.dateView.text = currentItem.date
        holder.binding.diaryView.text = currentItem.abstract_
        holder.binding.diaryView.movementMethod = ScrollingMovementMethod()

        holder.binding.rowLayout.setOnClickListener {
            // Pass our entry object to our update fragment
            val action = DisplayFragmentDirections.actionDisplayFragmentToUpdateFragment(currentItem)
            holder.binding.rowLayout.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    fun setData(entry: List<Entries>){
        this.entryList = entry
        // Notifies the recyclerview of any changes made
        notifyDataSetChanged()
    }
}