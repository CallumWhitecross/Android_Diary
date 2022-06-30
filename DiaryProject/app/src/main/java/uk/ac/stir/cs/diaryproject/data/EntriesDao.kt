package uk.ac.stir.cs.diaryproject.data

import androidx.lifecycle.LiveData
import androidx.room.*
import uk.ac.stir.cs.diaryproject.model.Entries

// Data Access Object (DAO)
// Contains the methods used for accessing the database
@Dao
interface EntriesDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEntry(entry: Entries)

    @Delete
    fun delete(entry: Entries)

    @Delete
    fun deleteEntry(entry: Entries)

    @Query("DELETE FROM diary_data")
    fun deleteAll()

    @Update
    fun updateEntry(entry: Entries)

    // Gathers all entries from the database in order of ID
    @Query("SELECT * FROM diary_data ORDER BY entID ASC")
    fun getAll(): LiveData<List<Entries>>
}