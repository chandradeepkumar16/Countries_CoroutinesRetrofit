package com.devtides.imageprocessingcoroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val IMAGE_URL = "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"

    private val coroutineScope=CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch {
            val orignalDeffered = coroutineScope.async ( Dispatchers.IO ){getOriginalBitmap()}

            var orignalBitmap=orignalDeffered.await()

//            val filterDeffered = coroutineScope.async(Dispatchers.Default) {applyFilter(orignalBitmap) }
//            val filterBitmap=filterDeffered.await()

            orignalBitmap=Filter.apply(orignalBitmap)

            loadImage(orignalBitmap)

        }


    }

    private fun getOriginalBitmap() =

        URL(IMAGE_URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }

    private fun loadImage(bmp:Bitmap){
        progressBar.visibility= View.GONE
        imageView.setImageBitmap(bmp)
        imageView.visibility= View.VISIBLE
    }

    private fun applyFilter(orignalBitmap: Bitmap){
        Filter.apply(orignalBitmap)
    }

}
