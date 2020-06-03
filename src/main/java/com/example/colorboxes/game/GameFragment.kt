package com.example.colorboxes.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.colorboxes.R
import com.example.colorboxes.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModelFactory = GameViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.gameFinished.observe(this, Observer { newState ->
            if (newState) {
                val currentScore = viewModel.score.value ?: 0
                val maxScore =viewModel.maxScore.value ?: 0
                val action = GameFragmentDirections.actionGameFragmentToScoreFragment(currentScore,maxScore)
                NavHostFragment.findNavController(this).navigate(action)
                viewModel.hasGameFinishedComplete()
            }
        })

        return binding.root
    }
}

