package com.example.mathersmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager

class MainActivity : AppCompatActivity(), MainMenuFragment.MainMenuListener, GameFragment.GameFragmentListener, InfoFragment.InfoFragmentListener {
    override fun infoButtonOnClick() {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, infoFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun backFromInfoOnClick() {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, mainMenuFragment)
        transaction.commit()
    }

    private val manager: FragmentManager = supportFragmentManager
    private val mainMenuFragment = MainMenuFragment()
    private val gameFragment = GameFragment()
    private val infoFragment = InfoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = manager.beginTransaction()
        transaction.add(R.id.main_frame, mainMenuFragment)
        transaction.add(R.id.main_frame, gameFragment)
        transaction.replace(R.id.main_frame, mainMenuFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun backFromGameOnClick() {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, mainMenuFragment)
        transaction.commit()
    }

    override fun startButtonOnClick(min:Int, max:Int, size:Int) {
        gameFragment.setMinMax(min, max)
        gameFragment.setSize(size)
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, gameFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
