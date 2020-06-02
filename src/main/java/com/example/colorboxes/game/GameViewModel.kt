package com.example.colorboxes.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel  : ViewModel(){
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    //Timer field
    private val timer: CountDownTimer

    // Current Time
    private val _currentTime= MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _button1=MutableLiveData<Prop>()
    val button1:LiveData<Prop>
        get()=_button1

    private val _button2=MutableLiveData<Prop>()
    val button2:LiveData<Prop>
        get()=_button2

    private val _colorText=MutableLiveData<Prop>()
    val colorText:LiveData<Prop>
        get()=_colorText

    private val _gameFinished=MutableLiveData<Boolean>()
    val gameFinished:LiveData<Boolean>
        get() = _gameFinished

    private val _score=MutableLiveData<Int>()
    val score:LiveData<Int>
        get()=_score

    private lateinit var colorList : MutableList<String>
    private var ans=-1
    private var indexOptions : MutableList<Int> = mutableListOf(0,1)

    init {
        resetList()
        setProp()

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _gameFinished.value = true
            }
        }

        timer.start()

    }

    private fun resetList(){
        colorList= mutableListOf(
            "red",
            "yellow",
            "blue",
            "green",
            "purple",
            "orange",
            "pink",
            "white",
            "grey",
            "brown"
        )
        colorList.shuffle()
    }

    private fun setProp(){
        resetList()
        indexOptions.shuffle()
        ans=indexOptions[0]
        if(ans==0){
            val rand=(0..10).random()
            _button1.value!!.color=colorList[rand%(colorList.size)]
            _button1.value!!.text=colorList[(rand+1)%(colorList.size)]
            _colorText.value!!.color=colorList[(rand+2)%(colorList.size)]
            _colorText.value!!.text=colorList[rand%(colorList.size)]
            _button2.value!!.color=colorList[9-(rand%(colorList.size))]
            _button2.value!!.text=colorList[9-((rand+1)%(colorList.size))]
        }else{
            val rand=(0..10).random()
            _button2.value!!.color=colorList[rand%(colorList.size)]
            _button2.value!!.text=colorList[(rand+1)%(colorList.size)]
            _colorText.value!!.color=colorList[(rand+2)%(colorList.size)]
            _colorText.value!!.text=colorList[rand%(colorList.size)]
            _button1.value!!.color=colorList[9-(rand%(colorList.size))]
            _button1.value!!.text=colorList[9-((rand+1)%(colorList.size))]
        }

    }

    fun onClickButtonLeft(){
        if(ans==0){
            _score.value=score.value?.plus(1)
        }else{
            _score.value=score.value?.minus(1)
        }

        setProp()
    }

    fun onClickButtonRight(){
        if(ans==1){
            _score.value=score.value?.plus(1)
        }else{
            _score.value=score.value?.minus(1)
        }

        setProp()
    }


    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    fun hasGameFinishedComplete(){
        _gameFinished.value=false
    }

}