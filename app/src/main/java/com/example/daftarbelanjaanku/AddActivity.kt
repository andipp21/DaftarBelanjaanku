package com.example.daftarbelanjaanku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.daftarbelanjaanku.database.Belanjaan
import com.example.daftarbelanjaanku.database.DatabaseBelanjaan
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private lateinit var db: DatabaseBelanjaan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        DatabaseBelanjaan.getInstance(this)?.let {
            db = it
        }

        btnSave.setOnClickListener {
            val belanjaan = Belanjaan(
                null,
                namaProdukAdd.text.toString(),
                jumlahProdukAdd.text.toString().toInt(),
                satuanProdukAdd.text.toString(),
                hargaSatuanProdukAdd.text.toString().toInt(),
                false
            )

            GlobalScope.launch {
                val totalSaved = db.belanjaanDao().add(belanjaan)

                runOnUiThread{
                    if (totalSaved>0){
                        Toast.makeText(it.context, "Data telah disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddActivity, "Data gagal disimpan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}