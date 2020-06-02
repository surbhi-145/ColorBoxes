package com.example.colorboxes.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.colorboxes.R
import com.example.colorboxes.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding: FragmentRulesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_rules, container, false)

        return binding.root
    }
}