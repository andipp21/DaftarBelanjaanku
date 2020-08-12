package com.example.daftarbelanjaanku

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.daftarbelanjaanku.database.Belanjaan
import com.example.daftarbelanjaanku.database.DatabaseBelanjaan
import kotlinx.android.synthetic.main.item_belanjaan.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class  BelanjaanAdapter(val listBelanjaan: List<Belanjaan>): RecyclerView.Adapter<BelanjaanAdapter.ViewHolder>(){
    private lateinit var db: DatabaseBelanjaan

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val cb = itemView.chekcboxBTN
        private val cardView = itemView.cardView

        fun cbCheck(belanjaan: Belanjaan){
            if (belanjaan.isChecked == true){
                cb.isChecked = true
                cardView.setCardBackgroundColor(Color.parseColor("#f5b776"))
            }
        }

        fun hitung(belanjaan: Belanjaan): Int {
            val hasil = belanjaan.price * belanjaan.quantity

            return hasil
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_belanjaan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBelanjaan.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.namaProduk.setText(listBelanjaan[position].name)
        holder.itemView.jumlahProduk.setText(listBelanjaan[position].quantity.toString())
        holder.itemView.satuanProduk.setText(listBelanjaan[position].typeQuantity)
        holder.itemView.hargaSatuanProduk.setText(listBelanjaan[position].price.toString())

        val totalPembayaran = holder.hitung(listBelanjaan[position])

        holder.itemView.totalBiaya.text = totalPembayaran.toString()
        holder.itemView.satuanProdukView.text = "/ ${listBelanjaan[position].typeQuantity}"

        holder.cbCheck(listBelanjaan[position])

        Log.d("checkbox", "${listBelanjaan[position].isChecked.toString()}")

        DatabaseBelanjaan.getInstance(holder.itemView.context)?.let {
            db = it
        }

        holder.itemView.deleteBTN.setOnClickListener{
            GlobalScope.launch {
                val rowDeleted = db.belanjaanDao().delete(listBelanjaan[position])
                (holder.itemView.context as MainActivity).runOnUiThread {
                    if (rowDeleted > 0) {
                        Toast.makeText(holder.itemView.context, "Belanjaan kamu, ${listBelanjaan[position].name} Telah dihapus", Toast.LENGTH_LONG)
                    } else {
                        Toast.makeText(holder.itemView.context, "Belanjaan, ${listBelanjaan[position].name} Gagal dihapus", Toast.LENGTH_LONG)
                    }
                }
                (holder.itemView.context as MainActivity).fetchData()
            }
        }

//        holder.cbCheck()
        val cb = holder.itemView.chekcboxBTN

        cb.setOnClickListener{
            if (cb.isChecked){
                listBelanjaan[position].apply {
                    isChecked = true
                }
            } else {
                listBelanjaan[position].apply {
                    isChecked = false
                }
            }

            GlobalScope.launch {
                val rowUpdated = db.belanjaanDao().update(listBelanjaan[position])

                (holder.itemView.context as MainActivity).fetchData()
            }
        }

        holder.itemView.namaProduk.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.belanjaanDao().update(listBelanjaan[position])

                    Log.d("Text Change", "$p0")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listBelanjaan[position].apply {
                    name = holder.itemView.namaProduk.text.toString()
                }
            }
        })

        holder.itemView.satuanProduk.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.belanjaanDao().update(listBelanjaan[position])

                    Log.d("Text Change", "$p0")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listBelanjaan[position].apply {
                    typeQuantity = holder.itemView.satuanProduk.text.toString()
                }
            }
        })

        holder.itemView.jumlahProduk.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.belanjaanDao().update(listBelanjaan[position])

                    Log.d("Text Change", "$p0")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var textJumlah = holder.itemView.jumlahProduk.text.toString()

                if (textJumlah == ""){
                    textJumlah = "0"
                }

                listBelanjaan[position].apply {
                    quantity = textJumlah.toInt()
                }
            }
        })

        holder.itemView.hargaSatuanProduk.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.belanjaanDao().update(listBelanjaan[position])

                    Log.d("Text Change", "$p0")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var textJumlah = holder.itemView.hargaSatuanProduk.text.toString()

                if (textJumlah == ""){
                    textJumlah = "0"
                }

                listBelanjaan[position].apply {
                    price = textJumlah.toInt()
                }
            }
        })

    }

}
