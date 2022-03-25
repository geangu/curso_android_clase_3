package io.geangu.dogs

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.geangu.dogs.databinding.ActivityMainBinding
import io.geangu.dogs.io.helper.getRetrofit
import io.geangu.dogs.io.service.DogApiService
import io.geangu.dogs.ui.adapters.DogAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dogAdapter: DogAdapter

    private val dogs = mutableListOf(
        "Coco",
        "Firulais",
        "Tommy"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dogAdapter = DogAdapter(dogs) { view ->
            val position = binding.rvDogs.getChildAdapterPosition(view)
            val url = dogs[position]
            openBrowser(url)
        }

        binding.btnList.setOnClickListener { initRecyclerView() }
        binding.btnTable.setOnClickListener { initRecyclerView(true) }

        binding.svFind.setOnQueryTextListener(this)

        initRecyclerView()

        searchByBreed("bulldog")
    }

    private fun initRecyclerView(grid: Boolean = false) {
        with(binding.rvDogs) {
            adapter = dogAdapter
            layoutManager = if (grid) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
        }
    }

    private fun searchByBreed(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit()
                    .create(DogApiService::class.java)
                    .getDogsByBreed("breed/${query.lowercase()}/images")

                val response = call.body()
                runOnUiThread {
                    if (!call.isSuccessful) {
                        Toast.makeText(binding.root.context, "Ocurrio un error", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val images = response?.images ?: emptyList()
                        dogs.clear()
                        dogs.addAll(images)
                        dogAdapter.notifyDataSetChanged()
                    }
                }
            } catch (ex: Exception) {
                println(ex.message)
            }
        }
        hideKeyboard()
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchByBreed(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = true

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}