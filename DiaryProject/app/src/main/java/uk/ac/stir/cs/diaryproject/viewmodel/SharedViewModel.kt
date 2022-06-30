package uk.ac.stir.cs.diaryproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Class used to store the sharedViewModel value of Date
// This passes the date between the DateFragment and AddFragment
class SharedViewModel: ViewModel() {


    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date


    fun submitDate(newDate: String){
        _date.value = newDate
    }
}