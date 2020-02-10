package com.example.testew2o.views

import android.app.DatePickerDialog
import android.app.Dialog
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.testew2o.R
import com.example.testew2o.adapter.ItemSimpledapter
import com.example.testew2o.auth.KeypairAuthenticator
import com.example.testew2o.database.AppDatabase
import com.example.testew2o.interfaces.ItemClickListener
import com.example.testew2o.interfaces.ItemListInterface
import com.example.testew2o.model.Item
import com.example.testew2o.util.DateUtils
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.item_add_dialog_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Classe Dialog responsavel pelo cadastro de novo item
 **/
class ItemAddDialog : DialogFragment(){

    lateinit var db : AppDatabase
    var simpleSimpledapter : ItemSimpledapter? = null
    lateinit var dialogBuilder : AlertDialog
    lateinit var listener: ItemClickListener
    lateinit var listenerItem: ItemListInterface
    var cal = Calendar.getInstance()
    val dateUtils = DateUtils()

    lateinit var itemNome       : TextInputLayout
    lateinit var itemPreco      : TextInputLayout
    lateinit var itemCategoria  : TextInputLayout
    lateinit var itemData       : TextInputLayout
    lateinit var itemDescricao  : TextInputLayout
    var dataSelecionada : Long = 0
    val auth = KeypairAuthenticator()

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL,
            R.style.ThemeOverlay_MaterialComponents_Dialog
        )
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        var view  = inflater.inflate(R.layout.item_add_dialog_layout, null)

        initComponents(view)

        dialogBuilder.setView(view)
        dialogBuilder.setCancelable(false)

        view.categoria_content.setOnClickListener{ openCategoriaDialog() }
        view.data_content.setOnClickListener { openDataDialog() }

        view.item_confirmar.setOnClickListener{
            var itemCreated = Item()
            itemCreated.nomItem     = itemNome.editText!!.text.toString()
            itemCreated.catItem     = itemCategoria.editText!!.text.toString()
            itemCreated.valorItem   = String.format("%1$.2f", itemPreco.editText!!.text.toString().toDouble())
            itemCreated.datItem     = dataSelecionada
            itemCreated.descItem    = itemDescricao.editText!!.text.toString()
            itemCreated.itemId      = auth.hashString(itemCreated.datItem.toString()+itemCreated.valorItem+itemCreated.catItem)

            if(!validateItem(view, itemCreated)){
                try {
                    db.itemDao().insertAll(itemCreated)
                    dismiss()
                } catch (e : SQLiteConstraintException){
                    val dialog = AlertDialog.Builder(context!!)
                    dialog.setTitle("Atenção!")
                    dialog.setMessage("Você já possui um item com as informações a seguir em sua base de dados:" +
                            "\n\nDATA: ${dateUtils.formatDate(itemCreated)}\nCATEGORIA: ${itemCreated.catItem}\nPREÇO: ${itemCreated.valorItem})\n" +
                            "\nNão é permitida a inserção de um item com esses mesmos parametros. Por favor, redigite os valores.")
                    dialog.setPositiveButton("Ok, compreendi") { dialog, which ->
                        cleanFields()
                        dismiss()
                    }
                    dialog.create().show()
                }
            }
            listenerItem.updateItens()
        }
        view.item_cancelar.setOnClickListener {
            dismiss()
        }
        return dialogBuilder
    }

    companion object {
        fun newInstance(listener: ItemClickListener, listenerItemList : ItemListInterface): ItemAddDialog {
            val frag = ItemAddDialog()
            val args = Bundle()
            frag.arguments = args
            frag.listener = listener
            frag.listenerItem = listenerItemList
            return frag
        }
    }

    fun initComponents(view : View){
        itemNome        = view.item_name
        itemPreco       = view.item_preco
        itemCategoria   = view.item_categoria
        itemData        = view.item_data
        itemDescricao   = view.item_descricacao

        dialogBuilder       = AlertDialog.Builder(context!!).create()
        db                  =
            AppDatabase.getAppDatabase(context!!)
        simpleSimpledapter  = ItemSimpledapter(context!!, db.itemDao().getAll(), listener)
    }

    fun openDataDialog(){
        val datePicker = DatePickerDialog(context!!)
        datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE,0)
            cal.set(Calendar.SECOND,0)
            cal.set(Calendar.MILLISECOND,0)

            dataSelecionada = Date(cal.time.time).time

            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            itemData.editText!!.setText(sdf.format(cal.time))
        }
        datePicker.show()
    }

    fun openCategoriaDialog(){
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Selecione uma categoria")
        val categorias = arrayOf("ENTRADA", "SAÍDA", "TRANSFERÊNCIA", "OUTROS")
        builder.setItems(categorias){ _, which ->
            when (which) {
                0 -> { itemCategoria.editText!!.setText(categorias[which]) }
                1 -> { itemCategoria.editText!!.setText(categorias[which]) }
                2 -> { itemCategoria.editText!!.setText(categorias[which]) }
                3 -> { itemCategoria.editText!!.setText(categorias[which]) }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun validateItem(view : View, item : Item) : Boolean{
        var retorno = false
        if(item.nomItem.trim().isEmpty()){
            view.item_name.isErrorEnabled = true
            view.item_name.error = "Nome de item inválido."
            retorno = true
        }else{
            view.item_name.isErrorEnabled = false
        }
        if(item.valorItem.trim().isEmpty()){
            view.item_preco.isErrorEnabled = true
            view.item_preco.error = "Valor de item inválido."
            retorno = true
        }else{
            view.item_preco.isErrorEnabled = false
        }
        if(item.catItem.trim().isEmpty()){
            view.item_categoria.isErrorEnabled = true
            view.item_categoria.error = "Categoria de item inválido."
            retorno = true
        }else{
            view.item_categoria.isErrorEnabled = false
        }
        if(item.datItem == 0L){
            view.item_data.isErrorEnabled = true
            view.item_data.error = "Data de item inválido."
            retorno = true
        }else{
            view.item_data.isErrorEnabled = false
        }
        return retorno
    }

    fun cleanFields(){
        itemNome.editText!!.setText("")
        itemPreco.editText!!.setText("")
        itemCategoria.editText!!.setText("")
        itemData.editText!!.setText("")
        itemDescricao.editText!!.setText("")
    }
}

