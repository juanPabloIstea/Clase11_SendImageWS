package com.example.clase11sendimagebysms

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var buttonEnviar: Button
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissionReadExternalStorage()

        imageView = findViewById(R.id.imageView)
        buttonEnviar = findViewById(R.id.buttonEnviar)

        imageView.setOnClickListener {
            val intentImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentImage, 123)
        }

        buttonEnviar.setOnClickListener {
            val intentSend = Intent(Intent.ACTION_SEND)
            intentSend.type = "image/*"
            //intentSend.setPackage("com.whatsapp")

            if (uri != null) {
                intentSend.putExtra(Intent.EXTRA_STREAM, uri)
                try {
                    startActivity(intentSend)
                } catch (e: Exception) {
                    Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "debe seleccionar una imagen", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            uri = data.data
            imageView.setImageURI(uri)
        }
    }

    private fun checkPermissionReadExternalStorage() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        }
    }
}