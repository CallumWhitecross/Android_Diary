package uk.ac.stir.cs.diaryproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.stir.cs.diaryproject.model.Entries

// AppDatabase contains the database holder
// Serves as the main access point for the underlying connection
// to the app's persisted, relational data
@Database(entities = [Entries::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntriesDao

    // Instance variable ensures that we only have one instance of the database class
    companion object{
        // Rights are made immediately visible to other threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            // Checking if the instance already exists and returning it
            // Otherwise in the synchronized block we are creating a new instance
            // Multiple instances of the Room database is very computationally expensive
            if(tempInstance != null){
                return tempInstance
            }
            // Everything within "synchronized" will be protected from concurrent execution by
            // multiple threads. In this block we create a new instance of our room database.
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}