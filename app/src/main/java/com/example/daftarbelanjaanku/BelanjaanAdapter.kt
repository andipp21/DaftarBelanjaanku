package com.example.daftarbelanjaanku

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        fun cbCheck(){
            if (cb.isChecked){
                cardView.setCardBackgroundColor(Color.parseColor("#000"))
            }
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

        val totalPembayaran = listBelanjaan[position].price * listBelanjaan[position].quantity

        holder.itemView.totalBiaya.text = totalPembayaran.toString()
        holder.itemView.satuanProdukView.text = "/ ${listBelanjaan[position].typeQuantity}"

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

        holder.cbCheck()
//        val cb = holder.itemView.chekcboxBTN

//        cb.setOnClickListener{
//            if (cb.isChecked){
//                holder.itemView.
//            }
//        }

    }


}