package com.gabrielmorenoibarra.offlinenotes.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.wear.widget.WearableLinearLayoutManager
import com.gabrielmorenoibarra.offlinenotes.R
import com.gabrielmorenoibarra.offlinenotes.data.AppPrefsManager
import com.gabrielmorenoibarra.offlinenotes.domain.Note
import com.gabrielmorenoibarra.offlinenotes.framework.extension.parseExtras
import com.gabrielmorenoibarra.offlinenotes.view.adapter.BaseAdapter
import com.gabrielmorenoibarra.offlinenotes.view.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import kotlin.math.min

class MainActivity : WearableActivity() {

    private lateinit var activity: MainActivity

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAmbientEnabled()
        activity = this@MainActivity

        initRv()
        initIbAdd()
        initIbRemove()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == KeyboardActivity.REQUEST_CODE) {
            intent.parseExtras(resultCode) {
                val input = it.getString(KeyboardActivity.EXTRA_RESULT_INPUT)
                val editing = it.getBoolean(KeyboardActivity.EXTRA_RESULT_EDITING)
                val position = it.getInt(KeyboardActivity.EXTRA_RESULT_POSITION)
                if (!input.isNullOrBlank()) {
                    val note = Note(input)
                    if (editing) {
                        AppPrefsManager.setNote(note.text, position)
                        adapter.setItem(note, position)
                    } else {
                        AppPrefsManager.addNote(note.text)
                        adapter.addItem(note)
                    }
                }
            }
        }
    }

    private fun initRv() {
        rv.apply {
            setHasFixedSize(true)
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(activity)

            val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(context, R.drawable.divider_vertical)?.let { itemDecorator.setDrawable(it) }
            addItemDecoration(itemDecorator)
        }

        val list = mutableListOf<Note>()
        val notes = AppPrefsManager.getAllNotes()
        notes.forEach {
            list.add(Note(it))
        }

        adapter = NoteAdapter(list,
            object : BaseAdapter.Listener<Note> {
                override fun onClickListener(item: Note, position: Int) {
                    goToKeyboard(item.text, position)
                }

                override fun onLongClickListener(item: Note, position: Int) {
                    showDeleteNoteDialog(item.text, position)
                }
            })
        rv.adapter = adapter
    }

    private fun initIbAdd() {
        ibAdd.setOnClickListener {
            goToKeyboard()
        }
    }

    private fun initIbRemove() {
        ibRemove.setOnClickListener {
            if (AppPrefsManager.isNotEmpty()) {
                showDeleteAllNotesDialog()
            } else {
                toast(R.string.there_are_no_notes)
            }
        }
    }

    private fun showDeleteNoteDialog(text: String, position: Int) {
        alert(R.string.dialog_delete_note_title) {
            yesButton {
                AppPrefsManager.deleteNote(position)
                adapter.removeItemAt(position)
                showDeleteMessage(text)
            }
            noButton { }
        }.show()
    }

    private fun showDeleteAllNotesDialog() {
        alert(R.string.dialog_delete_all_notes_title) {
            yesButton {
                AppPrefsManager.deleteAllNotes()
                adapter.removeAllItems()
                toast(R.string.dialog_delete_all_notes_yes_message)
            }
            noButton { }
        }.show()
    }

    private fun showDeleteMessage(text: String) {
        val sb = StringBuilder()
        sb.append("'")
        sb.append(text.substring(0, min(text.length, 10)))
        sb.append("...")
        sb.append("'")
        val s = String.format(getString(R.string.dialog_delete_note_yes_message), sb.toString())
        toast(s)
    }

    private fun goToKeyboard(text: String = "", position: Int = -1) {
        KeyboardActivity.startForResult(activity, text, position)
    }
}
