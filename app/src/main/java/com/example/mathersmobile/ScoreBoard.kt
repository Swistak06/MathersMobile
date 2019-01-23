package com.example.mathersmobile

import android.app.Activity
import android.content.Context
import android.os.Environment
import org.json.JSONObject
import java.io.*
import java.nio.channels.FileChannel
import java.nio.charset.Charset

class ScoreBoard(var context : Activity) {

    val sizeFour : IntArray = intArrayOf(0,0,0,0)
    val sizeFive : IntArray = intArrayOf(0,0,0,0)
    val sizeSix :  IntArray = intArrayOf(0,0,0,0)
    val sizeSeven : IntArray = intArrayOf(0,0,0,0)




    fun updateScoreBoard(size : Int, max : Int){
        readScoreBoard()
        increaseCompletedGames(size,max)
        saveScoreBoard()
    }

    fun getCompletedModeScore(size : Int, max :Int ) : String{
        val i : Int = when(max){
            9 -> 1
            14 -> 2
            22 -> 3
            else -> 0
        }

        when(size){
            4 -> return sizeFour[i].toString()
            5 -> return sizeFive[i].toString()
            6 -> return sizeSix[i].toString()
            else -> return sizeSeven[i].toString()
        }
    }
    fun increaseCompletedGames(size : Int, maxRange : Int){
        val i : Int = when(maxRange){
            9 -> 1
            14 -> 2
            22 -> 3
            else -> 0
        }

        when(size){
            4 -> sizeFour[i]++
            5 -> sizeFive[i]++
            6 -> sizeSix[i]++
            else -> sizeSeven[i]++
        }
    }

    fun saveScoreBoard(){

        var json = JSONObject();

        json
            .put("4x4",addOneScoreMode(sizeFour))
            .put("5x5",addOneScoreMode(sizeFive))
            .put("6x6",addOneScoreMode(sizeSix))
            .put("7x7",addOneScoreMode(sizeSeven))


        saveJson(json.toString())

    }
    private fun addOneScoreMode(arr: IntArray) : JSONObject{

        return JSONObject()
            .put("2-4",arr[0])
            .put("2-9",arr[1])
            .put("5-14",arr[2])
            .put("9-22",arr[3])

    }
    private fun saveJson(jsonString : String){
        val output : Writer
        val file = createFile()
        output = BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()
    }
    private fun createFile() : File {
        var storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if(!storageDir!!.exists())
            storageDir!!.mkdir()

        //return File.createTempFile("scoresMathers",".json",storageDir)
        return File(storageDir,"scoresMathers.json")
    }

    fun readScoreBoard(){
        val jsonObject = JSONObject(readFile())

         sizeFour[0] = jsonObject.getJSONObject("4x4").getInt("2-4")
         sizeFour[1] = jsonObject.getJSONObject("4x4").getInt("2-9")
         sizeFour[2] = jsonObject.getJSONObject("4x4").getInt("5-14")
         sizeFour[3] = jsonObject.getJSONObject("4x4").getInt("9-22")

         sizeFive[0] = jsonObject.getJSONObject("5x5").getInt("2-4")
         sizeFive[1] = jsonObject.getJSONObject("5x5").getInt("2-9")
         sizeFive[2] = jsonObject.getJSONObject("5x5").getInt("5-14")
         sizeFive[3] = jsonObject.getJSONObject("5x5").getInt("9-22")

         sizeSix[0] = jsonObject.getJSONObject("6x6").getInt("2-4")
         sizeSix[1] = jsonObject.getJSONObject("6x6").getInt("2-9")
         sizeSix[2] = jsonObject.getJSONObject("6x6").getInt("5-14")
         sizeSix[3] = jsonObject.getJSONObject("6x6").getInt("9-22")

         sizeSeven[0] = jsonObject.getJSONObject("7x7").getInt("2-4")
         sizeSeven[1] = jsonObject.getJSONObject("7x7").getInt("2-9")
         sizeSeven[2] = jsonObject.getJSONObject("7x7").getInt("5-14")
         sizeSeven[3] = jsonObject.getJSONObject("7x7").getInt("9-22")
    }
    private fun readFile() : String{
        val file = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            .absolutePath + "/scoresMathers.json"

        val stream = FileInputStream(file)


        var jsonString = ""
        stream.use { stream ->
            val fileChannel = stream.channel
            val mappedByteBuffer = fileChannel.map(
                FileChannel.MapMode.READ_ONLY, 0, fileChannel.size())
            jsonString = Charset.defaultCharset().decode(mappedByteBuffer).toString()
        }
        stream.channel.close()
        return jsonString
    }

}