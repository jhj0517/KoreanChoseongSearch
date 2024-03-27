package com.developerjo.library.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developerjo.library.models.ExampleData

class DataViewModel: ViewModel() {

    private val _exampleData = MutableLiveData<ArrayList<ExampleData>>()
    val exampleData get() = _exampleData

    init {
        fetchData()
    }

    private fun fetchData() {
        val fetched = ArrayList(
            listOf(
                ExampleData("바나나"),
                ExampleData("바람"),
                ExampleData("박수"),
                ExampleData("배"),
                ExampleData("백화점"),
                ExampleData("버섯"),
                ExampleData("별"),
                ExampleData("병원"),
                ExampleData("보라색"),
                ExampleData("부엌"),
                ExampleData("박하사탕"),
            )
        )
        _exampleData.value = fetched
    }

}