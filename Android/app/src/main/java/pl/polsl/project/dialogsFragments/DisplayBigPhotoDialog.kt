package pl.polsl.project.dialogsFragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.polsl.project.R
import kotlinx.android.synthetic.main.activity_photo_screen.*


class DisplayBigPhotoDialog : DialogFragment(){

    //klasa, która odpowiada za wyświetlenie dużego zdjecia
    var photo: Bitmap?=null
    var xSize: Int=0
    var ySize: Int=0

    //stworzenie widoku
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_photo_screen, container)

    }


    //wyświewieltenie zdjęcia, które jest zmniejszone
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       if(photo!=null){
          bigPhoto.setImageBitmap(photo)
       }
        xSize = (xSize*0.8).toInt()
        ySize = (ySize*0.8).toInt()
        bigPhoto.layoutParams = ConstraintLayout.LayoutParams(xSize, ySize)

        bigPhoto.setOnClickListener {
            dismiss()
        }

    }

}