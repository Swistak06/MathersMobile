package com.example.mathersmobile

import android.app.Activity
import java.util.*

class NumberGenerator {

    var rowTable = ArrayList<ArrayList<Int>>()
    var targetRowSum = ArrayList<Int>()
    var targetColumnSum = ArrayList<Int>()


    fun createBoard(size : Int, minRange : Int, maxRange : Int){

        val rand = Random()

        //Counting how many buttons in row can be disabled
        var maxNumberOfDisabledButtonsInRow = size - 1

        do {
            rowTable = ArrayList<ArrayList<Int>>()
            targetRowSum = ArrayList<Int>()
            targetColumnSum = ArrayList<Int>()

            //Inserting rows into table
            for (it : Int in 1..size)
                rowTable.add(ArrayList())

            for(i : Int in 0..(size-1)){

                val numbersToDisable = rand.nextInt(((maxNumberOfDisabledButtonsInRow) - 1) + 1) +1
                val indexesToBeDisabled = ArrayList<Int>()

                for(j : Int in 1..size)
                    rowTable[i].add(rand.nextInt(maxRange + 1 - minRange ) + minRange)

                //Disabling indexes
                for(j : Int in 0..(numbersToDisable-1)){
                    var counter = 1

                    indexesToBeDisabled.add(rand.nextInt(size))

                    if(j == 1)
                        while (indexesToBeDisabled[j] == indexesToBeDisabled[j-1])
                            indexesToBeDisabled[j] = rand.nextInt(size)
                    if(j > 1){
                        do {
                            if(indexesToBeDisabled[j] == indexesToBeDisabled[j - counter]){
                                indexesToBeDisabled[j] = rand.nextInt(size)
                                counter = 1
                            }
                            else
                                counter++

                        }while(j - counter >= 0)
                    }
                }

                for(j : Int in 0..(indexesToBeDisabled.size - 1))
                    rowTable[i][indexesToBeDisabled[j]] = 0
            }

            //Counting target sums in rows and columns
            for(i : Int in 0..(size - 1)){
                var sumInRow = 0
                var sumInCol = 0
                for(j : Int in 0..(size - 1)){
                    sumInRow += rowTable[i][j]
                    sumInCol += rowTable[j][i]
                }
                targetRowSum.add(sumInRow)
                targetColumnSum.add(sumInCol)
            }
        }while (!checkCorrectnessOfColumns())


//        //Log print of generated table
//        for(i : Int in 0..(size - 1)){
//            for(j : Int in 0..(size - 1))
//                print("${rowTable[i][j]} ")
//            print("\n")
//        }

        //Filling fields 0 with random numbers
        for(i : Int in 0..(size - 1))
            for(j : Int in 0..(size - 1))
                if(rowTable[i][j] == 0)
                    rowTable[i][j]= rand.nextInt(maxRange - minRange + 1) + minRange

    }

    private fun checkCorrectnessOfColumns() : Boolean{
        targetColumnSum.forEach {
            if(it == 0)
                return false
        }
        return true
    }

}