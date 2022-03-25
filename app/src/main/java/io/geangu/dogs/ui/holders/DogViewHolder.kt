package io.geangu.dogs.ui.holders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.geangu.dogs.R

class DogViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun render(dog: String) {
        val imageView = view.findViewById<ImageView>(R.id.ivDog)
        Picasso.get().load(dog).into(imageView)
    }

}