package uk.ac.stir.cs.diaryproject.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// Entity represents a table within the database
// Holds a random primary key (ID), the date of entry and extract from the diary entry
@Parcelize
@Entity(tableName = "diary_data")
data class Entries (
    @PrimaryKey(autoGenerate = true)
    val entID: Int,
    val date: String,
    val abstract_: String

): Parcelable