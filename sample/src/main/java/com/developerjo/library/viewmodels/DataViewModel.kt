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
                ExampleData("백화점"),
                ExampleData("박하사탕"),
                ExampleData("백합꽃"),  // Lily
                ExampleData("박하음료"),
                ExampleData("박카스"),
                ExampleData("백화요란"),
                ExampleData("버섯"),
                ExampleData("포도"),
                ExampleData("사랑"),
                ExampleData("우정"),
                ExampleData("부엌"),
                ExampleData("딸기"),
            )
        )
        _exampleData.value = fetched
    }

}