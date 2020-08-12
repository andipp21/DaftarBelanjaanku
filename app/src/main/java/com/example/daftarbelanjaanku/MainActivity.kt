package com.example.daftarbelanjaanku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daftarbelanjaanku.database.DatabaseBelanjaan
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseBelanjaan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DatabaseBelanjaan.getInstance(this)?.let {
            db = it
        }

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listBelanjaan = db.belanjaanDao().getAll()

            runOnUiThread{
                rvContainer.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                val adapter = BelanjaanAdapter(listBelanjaan)
                rvContainer.adapter = adapter
            }
        }
    }
}