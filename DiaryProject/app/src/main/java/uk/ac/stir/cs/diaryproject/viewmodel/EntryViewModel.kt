package uk.ac.stir.cs.diaryproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.stir.cs.diaryproject.data.AppDatabase
import uk.ac.stir.cs.diaryproject.repository.DiaryRepository
import uk.ac.stir.cs.diaryproject.model.Entries

// EntryViewModel provides data to the UI that survives configuration changes
// e.g. screen rotation. ViewModel acts as a communication between the repository and the UI
// AndroidViewModel includes application reference
class EntryViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Entries>>
    private val repository: DiaryRepository

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    // First called when EntryViewModel is created
    init {
        val entryDao = AppDatabase.getDatabase(application).entryDao()
        repository = DiaryRepository(entryDao)
        readAllData = repository.readAllData
    }

    fun addEntry(entry: Entries){
        // ViewModelScope is part of kotlin coroutines which runs the code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEntry(entry)
        }
    }

    fun updateEntries(entries: Entries) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEntries(entries)
        }
    }

    fun deleteEntry(entries: Entries) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEntry(entries)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun submitDate(newDate: String) {
        _date.value = newDate
    }
}