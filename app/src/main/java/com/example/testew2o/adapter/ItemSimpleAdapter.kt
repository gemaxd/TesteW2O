package com.example.testew2o.adapter

import android.content.Context
import android.view.*
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.testew2o.R
import com.example.testew2o.interfaces.ItemClickListener
import com.example.testew2o.model.Item
import com.example.testew2o.util.DateUtils
import kotlinx.android.synthetic.main.item_row.view.*


/***
 * Adpter de item
 *
 * utilizado para manipular as informações de produtos
 */
class ItemSimpledapter(context : Context, listaItens : List<Item>, listener : ItemClickListener)
    : RecyclerView.Adapter<ItemViewHolder>() {

    var mContext = context
    var mListener = listener
    var mItens = listaItens
    val dateUtils = DateUtils()

    override fun getItemCount(): Int {
        return mItens.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val cellClientes = layoutInflater.inflate(R.layout.item_row, parent , false)
        return ItemViewHolder( cellClientes , mItens )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemSelecionado     = mItens[position]
        holder.nome.text    = itemSelecionado.nomItem
        holder.data.text    = dateUtils.formatDate(itemSelecionado)
        holder.valor.text   = String.format("R$ %1$.2f",itemSelecionado.valorItem.replace(",",".").toDouble())
        holder.categoria.text = itemSelecionado.catItem

        holder.setOnClienteItemClickListener(mListener)

        holder.options.setOnClickListener {
            val popup = PopupMenu(mContext, holder.options)
            popup.inflate(R.menu.menu_produtos)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when(item!!.itemId){
                    R.id.menu_compartilhar -> { mListener.onItemShare(itemSelecionado)  }
                    R.id.menu_criar_evento -> { mListener.onItemCreateEvent(itemSelecionado)  }
                    R.id.menu_excluir ->{ mListener.onItemExclude(itemSelecionado) }
                }
                true
            }
            popup.show()
        }
    }
}

class ItemViewHolder(itemView : View, tipos : List<Item>)  : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener{

    val nome       = itemView.nome_val
    val data       = itemView.data_val
    val valor      = itemView.valor_val
    val categoria  = itemView.categoria_val
    val options    = itemView.item_options

    lateinit var tipoPedidoListener : ItemClickListener

    fun setOnClienteItemClickListener(listener: ItemClickListener){
        this.tipoPedidoListener = listener
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu!!.add(Menu.NONE, R.id.menu_compartilhar, Menu.NONE, "Compartilhar")
        menu.add(Menu.NONE, R.id.menu_criar_evento, Menu.NONE, "Criar Evento")
        menu.add(Menu.NONE, R.id.menu_excluir, Menu.NONE, "Excluir")
    }

}