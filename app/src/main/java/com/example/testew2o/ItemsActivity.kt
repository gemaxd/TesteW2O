package com.example.testew2o

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.CalendarContract
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testew2o.adapter.ItemSimpledapter
import com.example.testew2o.database.AppDatabase
import com.example.testew2o.interfaces.ItemClickListener
import com.example.testew2o.interfaces.ItemListInterface
import com.example.testew2o.model.Item
import com.example.testew2o.util.CheckConnection
import com.example.testew2o.util.DateUtils
import com.example.testew2o.views.ItemAddDialog
import kotlinx.android.synthetic.main.activity_items.*


/**
 * Activity responsavel pelas informações de itens
 * */
class ItemsActivity : AppCompatActivity(), ItemClickListener, ItemListInterface {

    lateinit var dialogAddItem: ItemAddDialog
    private var simpleSimpledapter : ItemSimpledapter? = null
    lateinit var db : AppDatabase
    var listaItens = ArrayList<Item>()
    val dateUtils = DateUtils()
    var delay = 2000L
    val handler = Handler()
    var runnable = Runnable { }
    var checkConnection = CheckConnection(this)
    var isConnected : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        initcomponents()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview.adapter = simpleSimpledapter
        adicionar_item.setOnClickListener{
            addItem()
        }
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            if(checkConnection.networkUtils.isNetworkAvailable(this)){
                isConnected = true
                adicionar_item.visibility = View.VISIBLE
                card_wifi.visibility = View.GONE
            }else{
                isConnected = false
                adicionar_item.visibility = View.GONE
                card_wifi.visibility = View.VISIBLE
            }
            handler.postDelayed(runnable, delay)
        }.also { runnable = it }, delay)

        super.onResume()
    }

    override fun onPause() {
        handler.removeCallbacks(runnable) //stop handler when activity not visible
        super.onPause()
    }

    private fun addItem(){
        dialogAddItem = ItemAddDialog.newInstance(this, this)
        dialogAddItem.show(supportFragmentManager, "dialogTipoPedido")
    }

    fun initcomponents(){
        db = AppDatabase.getAppDatabase(this)
        simpleSimpledapter = ItemSimpledapter(this, listaItens, this)
        getItems()
    }

    override fun onItemShare(item: Item) {
        sendMail(item)
    }

    override fun onItemCreateEvent(item: Item) {
        eventCreate(item)
    }

    override fun onItemExclude(item: Item) {
        if(isConnected){
            db.itemDao().delete(item)
            updateItens()
        }else{
            Toast.makeText(this, "Desculpe, você não pode concluir essa ação estando desconectado.", Toast.LENGTH_LONG).show()
        }
    }

    fun sendMail(item : Item){
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, "manarimjesse@gmail.com")
        email.putExtra(Intent.EXTRA_SUBJECT, "teste W2O")
        email.putExtra(Intent.EXTRA_TEXT, item.toString())
        email.type = "text/plain"

        startActivity(Intent.createChooser(email, "Choose Email Client..."))
    }

    override fun updateItens() {
        getItems()
        recyclerview.adapter!!.notifyDataSetChanged()
    }

    fun getItems(){
        listaItens.clear()
        listaItens.addAll(db.itemDao().getAll())
    }

    fun eventCreate(item : Item){
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.Events.TITLE, "Produto:${item.nomItem}")
            .putExtra(CalendarContract.Events.DESCRIPTION, "Produto:${item.nomItem} Preço:${item.valorItem} Data:${dateUtils.formatDate(item)} Categoria:${item.catItem} Descrição:${item.descItem}")
            .putExtra(CalendarContract.Events.EVENT_LOCATION, "AQUI")
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())

        startActivity(intent)

    }

}
