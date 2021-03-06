package com.example.mathersmobile

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager

class MainActivity : AppCompatActivity(), MainMenuFragment.MainMenuListener, GameFragment.GameFragmentListener, InfoFragment.InfoFragmentListener, EndGameFragment.EndGameFragmentListener {



    private val manager: FragmentManager = supportFragmentManager
    private val mainMenuFragment = MainMenuFragment()
    private val gameFragment = GameFragment()
    private val infoFragment = InfoFragment()
    private val endGameFragment = EndGameFragment()
    val REQUEST_READ_EXTERNAL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = manager.beginTransaction()
        transaction.add(R.id.main_frame, mainMenuFragment)
        transaction.add(R.id.main_frame, gameFragment)
        transaction.replace(R.id.main_frame, mainMenuFragment)
        //transaction.addToBackStack(null)
        transaction.commit()

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_EXTERNAL
            )
        }
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

    override fun onBackPressed() {
        if(endGameFragment.isVisible)
        {
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.main_frame, mainMenuFragment)
            transaction.commit()
        }
        else
            super.onBackPressed()
    }

    override fun backToMainMenu() {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, mainMenuFragment)
        transaction.commit()
    }

    override fun winConditionAction(minutes : Int, seconds : Int, size : Int, max : Int) {
        endGameFragment.setFragmentActivity(this)
        endGameFragment.setTimeOfGame(minutes, seconds)
        endGameFragment.setGameMode(size, max)
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame,endGameFragment)
        transaction.commit()
    }

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
}
