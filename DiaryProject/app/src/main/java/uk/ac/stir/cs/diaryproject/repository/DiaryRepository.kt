package uk.ac.stir.cs.diaryproject.repository

import androidx.lifecycle.LiveData
import uk.ac.stir.cs.diaryproject.data.EntriesDao
import uk.ac.stir.cs.diaryproject.model.Entries

// User Repository abstracts access to multiple data sources
class DiaryRepository(private val entriesDao: EntriesDao) {

    val readAllData: LiveData<List<Entries>> = entriesDao.getAll()

    fun addEntry(entry: Entries){
        entriesDao.addEntry(entry)
    }

    fun updateEntries(entry: Entries) {
        entriesDao.updateEntry(entry)
    }

    fun deleteEntry(entry: Entries) {
        entriesDao.deleteEntry(entry)
    }

    fun deleteAll() {
        entriesDao.deleteAll()
    }
}