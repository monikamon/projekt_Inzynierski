package com.example.monika.inzynierka.Dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.monika.inzynierka.DataStructure.tools.returnPhotoInterface
import com.example.monika.inzynierka.R
import kotlinx.android.synthetic.main.activity_choose_photo_action.*

class PhotoDialog : DialogFragment(){

    var activity:Activity?=null
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
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2)
            }else{

                //pobierz zdjecie z galerii
                val choosePhoto = Intent(Intent.ACTION_PICK)
                choosePhoto.type = "image/*"
                startActivityForResult(choosePhoto, 2)

            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            val image = data!!.extras.get("data") as Bitmap
            (activity!! as returnPhotoInterface).returnPhoto(image)
            dismiss()

        }else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            val stream = activity!!.contentResolver.openInputStream(data!!.data)
            val image = BitmapFactory.decodeStream(stream)
            (activity!! as returnPhotoInterface).returnPhoto(image)
            dismiss()
        }

    }

}