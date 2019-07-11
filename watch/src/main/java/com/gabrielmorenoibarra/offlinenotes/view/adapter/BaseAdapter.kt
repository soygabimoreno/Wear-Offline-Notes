package com.gabrielmorenoibarra.offlinenotes.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    private var items: MutableList<T>,
    private val listener: Listener<T>
) :
    RecyclerView.Adapter<BaseAdapter.Holder<T>>() {

    interface Listener<T> {
        fun onClickListener(item: T, position: Int)
        fun onLongClickListener(item: T, position: Int)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<T>

    override fun onBindViewHolder(holder: Holder<T>, position: Int) {
        val item = items[position]
        holder.bind(this, item)
    }

    fun addItems(items: MutableList<T>) {
        val start = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(start, items.size)
    }

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun setItem(item: T, position: Int) {
        items[position] = item
        notifyDataSetChanged()
    }

    fun removeItemAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeAllItems() {
        items = mutableListOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    fun click(item: T, position: Int) {
        listener.onClickListener(item, position)
    }

    fun longClick(item: T, position: Int) {
        listener.onLongClickListener(item, position)
    }

    abstract class Holder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(adapter: BaseAdapter<T>, item: T) = with(itemView) {
            bindItem(item)
            initClickListener(adapter, item)
            initLongClickListener(adapter, item)
        }

        abstract fun View.bindItem(item: T)

        private fun View.initClickListener(adapter: BaseAdapter<T>, item: T) {
            setOnClickListener {
                adapter.click(item, adapterPosition)
            }
        }

        private fun View.initLongClickListener(adapter: BaseAdapter<T>, item: T) {
            setOnLongClickListener {
                adapter.longClick(item, adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }
}
