package com.strixtechnology.diceroller2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.strixtechnology.diceroller2.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private  lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false)

        binding = FragmentSettingsBinding.inflate(layoutInflater)


        return  binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
    }
}