package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mistershorr.soundboard.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var buttonA : Button
    lateinit var buttonBb : Button
    lateinit var buttonB : Button
    lateinit var buttonC : Button
    lateinit var buttonCs : Button
    lateinit var buttonD : Button
    lateinit var buttonDs : Button
    lateinit var buttonE : Button
    lateinit var buttonF : Button
    lateinit var buttonFs : Button
    lateinit var buttonG : Button
    lateinit var buttonGs : Button
    lateinit var playButton : Button
    var noteMap = HashMap<String, Int>()
    private lateinit var binding: ActivityMainBinding


    lateinit var soundPool : SoundPool
    var aNote = 0
    var bbNote = 0
    var bNote = 0
    var cNote = 0
    var csNote = 0
    var dNote = 0
    var dsNote = 0
    var eNote = 0
    var fNote = 0
    var fsNote = 0
    var gNote = 0
    var gsNote = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        wireWidgets()
        initializeSoundPool()
        setListeners()

        val inputStream = resources.openRawResource(R.raw.song)
        val jsonString = inputStream.bufferedReader().use{
            it.readText()
        }

        Log.d(TAG,  "onCreate: jsonString $jsonString")

        val gson = Gson()
        val type = object : TypeToken<List<Note>>() { }.type
        val notes = gson.fromJson<List<Note>>(jsonString, type)


    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        binding.buttonA.setOnClickListener(soundBoardListener)
        binding.buttonBb.setOnClickListener(soundBoardListener)
        binding.buttonB.setOnClickListener(soundBoardListener)
        binding.buttonC.setOnClickListener(soundBoardListener)
        binding.buttonCs.setOnClickListener(soundBoardListener)
        binding.buttonD.setOnClickListener(soundBoardListener)
        binding.buttonDs.setOnClickListener(soundBoardListener)
        binding.buttonDs.setOnClickListener(soundBoardListener)
        binding.buttonE.setOnClickListener(soundBoardListener)
        binding.buttonF.setOnClickListener(soundBoardListener)
        binding.buttonFs.setOnClickListener(soundBoardListener)
        binding.buttonG.setOnClickListener(soundBoardListener)
        binding.buttonGs.setOnClickListener(soundBoardListener)

        binding.playSong.setOnClickListener{
            GlobalScope.launch {
                playCot()
            }
        }
    }


    private fun wireWidgets() {
        buttonA = findViewById(R.id.buttonA)
        buttonBb = findViewById(R.id.buttonBb)
        buttonB = findViewById(R.id.buttonB)
        buttonC = findViewById(R.id.buttonC)
        buttonCs = findViewById(R.id.buttonCs)
        buttonD = findViewById(R.id.buttonD)
        buttonDs = findViewById(R.id.buttonDs)
        buttonE = findViewById(R.id.buttonE)
        buttonF = findViewById(R.id.buttonF)
        buttonFs = findViewById(R.id.buttonFs)
        buttonG = findViewById(R.id.buttonG)
        buttonGs = findViewById(R.id.buttonGs)
        playButton = findViewById(R.id.playSong)
    }

    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)

        aNote = soundPool.load(this, R.raw.scalea, 1)
        bbNote = soundPool.load(this, R.raw.scalebb, 1)
        bNote = soundPool.load(this, R.raw.scaleb, 1)
        cNote =  soundPool.load(this, R.raw.scalec, 1)
        csNote = soundPool.load(this, R.raw.scalecs, 1)
        dNote = soundPool.load(this, R.raw.scaled, 1)
        dsNote = soundPool.load(this, R.raw.scaleds, 1)
        eNote =  soundPool.load(this, R.raw.scalee, 1)
        fNote = soundPool.load(this, R.raw.scalef, 1)
        fsNote = soundPool.load(this, R.raw.scalefs, 1)
        gNote = soundPool.load(this, R.raw.scaleg, 1)
        gsNote =  soundPool.load(this, R.raw.scalegs, 1)

        //eefggfedccdeedd
        noteMap.put("E", eNote)
        noteMap.put("E", eNote)
        noteMap.put("F", fNote)
        noteMap.put("G", gNote)
        noteMap.put("G", gNote)
        noteMap.put("F", fNote)
        noteMap.put("E", eNote)
        noteMap.put("D", dNote)
        noteMap.put("C", cNote)
        noteMap.put("C", cNote)
        noteMap.put("D", dNote)
        noteMap.put("E", eNote)
        noteMap.put("E", eNote)
        noteMap.put("D", dNote)
        noteMap.put("D", dNote)
    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }
    private fun playNote(note: String) {
        playNote(noteMap[note] ?: 0)
    }

    private inner class SoundBoardListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.buttonA -> playNote(aNote)
                R.id.buttonBb -> playNote(bbNote)
                R.id.buttonB -> playNote(bNote)
                R.id.buttonC -> playNote(cNote)
                R.id.buttonCs -> playNote(csNote)
                R.id.buttonD -> playNote(dNote)
                R.id.buttonDs -> playNote(dsNote)
                R.id.buttonE -> playNote(eNote)
                R.id.buttonF -> playNote(fNote)
                R.id.buttonFs -> playNote(fsNote)
                R.id.buttonG -> playNote(gNote)
                R.id.buttonGs -> playNote(gsNote)

            }
        }
    }

    private suspend fun playSong(song: List<Note>) {
        // loop through a list of notes
        for (n: Note in song) {
            delay(500)
            if (n.note == "A") {
                playNote(aNote)
            } else if (n.note == "BF") {
                playNote(bbNote)
            } else if (n.note == "B") {
                playNote(bNote)
            } else if (n.note == "C") {
                playNote(cNote)
            } else if (n.note == "CS") {
                playNote(csNote)
            } else if (n.note == "D") {
                playNote(dNote)
            } else if (n.note == "DS") {
                playNote(dsNote)
            } else if (n.note == "E") {
                playNote(eNote)
            } else if (n.note == "F") {
                playNote(fNote)
            } else if (n.note == "FS") {
                playNote(fsNote)
            } else if (n.note == "G") {
                playNote(gNote)
            } else if (n.note == "Gs") {
                playNote(gsNote)
            }
        }
    }



}
private suspend fun playCot(){
    playNote("BF")
    kotlinx.coroutines.delay(500)
    playNote("B")
    kotlinx.coroutines.delay(500)
    playNote("C")
    kotlinx.coroutines.delay(500)
    playNote("CF")
}
private fun delay(time: Long) {
    try {
        Thread.sleep(time)
    } catch(e: InterruptedException) {
        e.printStackTrace()
    }
}
}