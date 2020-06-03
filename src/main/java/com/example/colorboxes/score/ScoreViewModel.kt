package com.example.colorboxes.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore : Int, maxScore : Int) : ViewModel(){

    private val _eventPlayAgain =MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _maxScore=MutableLiveData<Int>()
    val maxScore:LiveData<Int>
        get()=_maxScore

    private val _accuracy = MutableLiveData<Float>()
    val accuracy: LiveData<Float>
        get() = _accuracy

    init {
        _score.value = finalScore
        _maxScore.value=maxScore
        _accuracy.value= score.value?.toFloat()?. div(maxScore.toFloat()) ?. times(100)
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

}