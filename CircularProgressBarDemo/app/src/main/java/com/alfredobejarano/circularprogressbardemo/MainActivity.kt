package com.alfredobejarano.circularprogressbardemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
    *  Initializes the views in this activity.
    **/
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seek_bar.progress = progress_bar.progress // Set the ProgressBar progress as the SeekBar progress.
        text_view.text = "${progress_bar.progress}%" // Set the ProgressBar progress value as the text for the progress indicator Textview.
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener { // Set a ChangeListener to the SeekBar, for reporting its percentage to the ProgressBar.
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progress_bar.progress = progress // Set the new SeekBar progress as the progress to the ProgressBar
                text_view.text = "$progress%" // Change the text in the TextView that displays the progress.
            }

            /**
             * Do nothing.
             */
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

            /**
             * Do nothing.
             */
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }
}
