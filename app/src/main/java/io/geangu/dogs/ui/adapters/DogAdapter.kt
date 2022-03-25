package io.geangu.dogs.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.geangu.dogs.R
import io.geangu.dogs.ui.holders.DogViewHolder

class DogAdapter(
    private val dogs: List<String>,
    private val onClickListener: View.OnClickListener? = null
) : RecyclerView.Adapter<DogViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.dog_item, parent, false)
        view.setOnClickListener(this)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) =
        holder.render(dogs[position])

    override fun getItemCount(): Int = dogs.count()

    override fun onClick(view: View?) {
        onClickListener?.onClick(view)
    }
}