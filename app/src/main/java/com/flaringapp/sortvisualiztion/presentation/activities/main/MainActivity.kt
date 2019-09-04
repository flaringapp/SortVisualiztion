package com.flaringapp.sortvisualiztion.presentation.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.transaction
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.create_array.impl.CreateArrayFragment
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl.SortFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.commit { add(R.id.fragmentContainer, CreateArrayFragment.newInstance()) }

        supportFragmentManager.commit { add(R.id.fragmentContainer, SortFragment.newInstance()) }
    }

}
