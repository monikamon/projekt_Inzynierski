package com.example.monika.inzynierka.Dialog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.monika.inzynierka.R
import kotlinx.android.synthetic.main.activity_choose_photo_action.*

class PhotoDialog : DialogFragment(){

    var pm: PackageManager?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_choose_photo_action, container)

    }

    //wybranie co ma się stać po otwarciu dialogu na podstawie wybranego guzika
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //zamknięcie dialogu
        CancelButton.setOnClickListener{
            view -> dismiss()
        }

        TakeAPhotoButton.setOnClickListener{
            view ->
            if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                //jeżeli nie, to proszę o dostęp do kamery(aparatu)
                requestPermissions(arrayOf(Manifest.permission.CAMERA),1)
                //REQUEST_TAKE_PHOTO=1 (zamiast 1 może być zmienna: REQUEST_TAKE_PHOTO)

            }else{

                //zrób zdjęcie
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture ->
                    takePicture.resolveActivity(pm)?.also {
                        startActivityForResult(takePicture, 1)
                    }
                }

            }
        }

        ChoosePictureFromGaleryButton.setOnClickListener{
            view ->
            //sprawdzenie, czy mam zezwolenie na dostęp do pamięci
            if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){

                //jeżeli nie, to proszę o dostęp do pamięci
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }else{

                //pobierz zdjecie z galerii
                val choosePhoto = Intent(Intent.ACTION_PICK)
                choosePhoto.type = "image/*"
                startActivityForResult(choosePhoto, 1)
                //RESULT_LOAD_IMG zostało zastąpione 1

            }
        }

    }

}