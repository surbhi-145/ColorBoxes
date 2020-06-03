package com.example.colorboxes.game

import android.graphics.Color
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.NullPointerException

class GameViewModel  : ViewModel(){
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
        //time for round 0
        const val ROUND_ZERO_TIME = 3000L
        //time for round 1
        const val ROUND_ONE_TIME=2000L
        //time for round 2
        const val ROUND_TWO_TIME=1500L
    }

    //Timer field
    private val timer: CountDownTimer
    private var perRoundTimer : CountDownTimer

    // Current Time
    private val _currentTime= MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _button1Text=MutableLiveData<String>()
    val button1Text:LiveData<String>
        get()=_button1Text

    private val _button1Color=MutableLiveData<String>()
    val button1Color:LiveData<String>
        get()=_button1Color

    private val _button2Text=MutableLiveData<String>()
    val button2Text:LiveData<String>
        get()=_button2Text

    private val _button2Color=MutableLiveData<String>()
    val button2Color:LiveData<String>
        get()=_button2Color

    private val _hintText=MutableLiveData<String>()
    val hintText:LiveData<String>
        get()=_hintText

    private val _hintColor=MutableLiveData<String>()
    val hintColor:LiveData<String>
        get()=_hintColor

    private val _gameFinished=MutableLiveData<Boolean>()
    val gameFinished:LiveData<Boolean>
        get() = _gameFinished

    private val _score=MutableLiveData<Int>()
    val score:LiveData<Int>
        get()=_score

    private val _maxScore=MutableLiveData<Int>()
    val maxScore:LiveData<Int>
        get()=_maxScore

    private lateinit var colorList : MutableList<String>
    private var ans=-1
    private var indexOptions : MutableList<Int> = mutableListOf(0,1)
    private lateinit var colorCode : Map<String,String>
    init {

        initializeColorCode()
        setList()
        setProp()

        print("Here")

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

        perRoundTimer= object : CountDownTimer(ROUND_ZERO_TIME, ONE_SECOND){

            override fun onFinish() {
                setProp()
                resetRoundTimer()
            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }
        perRoundTimer.start()

    }

    private fun setList(){
        colorList= mutableListOf(
            "red",
            "yellow",
            "blue",
            "green",
            "purple",
            "orange",
            "pink",
            "maroon",
            "grey",
            "brown"
        )
        colorList.shuffle()
    }

    private fun initializeColorCode(){
        colorCode= mapOf(
            "red" to "#FFF80202",
            "yellow" to "#EFDB02",
            "blue" to "#FA6BE9FA",
            "green" to "#FF8CF316",
            "purple" to "#89169D",
            "orange" to "#FFFB8117",
            "pink" to "#FD6AA5",
            "maroon" to "#74052D",
            "grey" to "#FFA99595",
            "brown" to "#FF80594C"

        )
    }

    private fun setProp(){
        _maxScore.value=maxScore.value ?. plus(1) ?: 0
        resetRoundTimer()
        colorList.shuffle()
        indexOptions.shuffle()
        ans=indexOptions[0]

        if(ans==0){
            val rand=(0..10).random()
            _button1Color.value=colorList[rand%(colorList.size)]
            _button1Text.value=colorList[(rand+1)%(colorList.size)]
            _hintColor.value=colorList[(rand+2)%(colorList.size)]
            _hintText.value=colorList[rand%(colorList.size)]
            _button2Color.value=colorList[(rand+2)%(colorList.size)]
            _button2Text.value=colorList[9-((rand+1)%(colorList.size))]
        }else{
            val rand=(0..10).random()
            _button2Color.value=colorList[rand%(colorList.size)]
            _button2Text.value=colorList[(rand+1)%(colorList.size)]
            _hintColor.value=colorList[(rand+2)%(colorList.size)]
            _hintText.value=colorList[rand%(colorList.size)]
            _button1Color.value=colorList[(rand+2)%(colorList.size)]
            _button1Text.value=colorList[9-((rand+1)%(colorList.size))]
        }

    }

    fun onClickButtonLeft(){

        if(ans==0){
            _score.value=score.value?.plus(1) ?: 1
        }else{
            _score.value=score.value?.minus(1) ?: 0
        }

        setProp()
    }

    fun onClickButtonRight(){

        if(ans==1){
            _score.value=score.value?.plus(1) ?: 1

        }else{
            _score.value=score.value?.minus(1) ?: 0
        }

        setProp()
    }


    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        perRoundTimer.cancel()
    }

    fun hasGameFinishedComplete(){
        _gameFinished.value=false
    }

    fun getDrawableColor(color : String): Int {
        return Color.parseColor(colorCode[color])
    }

    private fun resetRoundTimer() {

        if (perRoundTimer != null) {
            perRoundTimer.cancel()
        }


        if (score.value==null || score.value!!< 15){
            perRoundTimer = object : CountDownTimer(ROUND_ZERO_TIME, ONE_SECOND) {

                override fun onFinish() {
                    setProp()
                    resetRoundTimer()
                }

                override fun onTick(millisUntilFinished: Long) {
                    //Nothing
                }
            }
    }else if(score.value!! <30){
            perRoundTimer = object : CountDownTimer(ROUND_ONE_TIME, ONE_SECOND) {

                override fun onFinish() {
                    setProp()
                    resetRoundTimer()
                }

                override fun onTick(millisUntilFinished: Long) {
                    //Nothing
                }
            }
        }else{
            perRoundTimer = object : CountDownTimer(ROUND_TWO_TIME, ONE_SECOND) {

                override fun onFinish() {
                    setProp()
                    resetRoundTimer()
                }

                override fun onTick(millisUntilFinished: Long) {
                    //Nothing
                }
            }
        }
        perRoundTimer.start()
    }

}
