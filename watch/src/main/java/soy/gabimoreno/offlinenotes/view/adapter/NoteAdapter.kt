package soy.gabimoreno.offlinenotes.view.adapter

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_note.view.*
import soy.gabimoreno.offlinenotes.R
import soy.gabimoreno.offlinenotes.domain.Note
import soy.gabimoreno.offlinenotes.framework.extension.inflate

class NoteAdapter(
    items: MutableList<Note>,
    listener: Listener<Note>
)
    : BaseAdapter<Note>(items, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_note))
    }

    class Holder(itemView: View) : BaseAdapter.Holder<Note>(itemView) {
        override fun View.bindItem(item: Note) {
            initTv(item)
        }

        private fun View.initTv(item: Note) {
            tv.text = item.text
        }
    }
}
