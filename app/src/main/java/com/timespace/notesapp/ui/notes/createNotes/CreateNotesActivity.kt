package com.timespace.notesapp.ui.notes.createNotes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jkcarino.rtexteditorview.RTextEditorView
import com.timespace.notesapp.R
import com.timespace.notesapp.databinding.ActivityCreateNotesBinding
import com.timespace.notesapp.databinding.BottomSheetDialogStringBinding
import com.timespace.notesapp.firebasemodel.NotesData
import org.jetbrains.anko.checkBox
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CreateNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNotesBinding
    var bindingDialog: BottomSheetDialogStringBinding? = null
    var bottomSheetDialog: BottomSheetDialog? = null
    var runnable: Runnable? = null
    var handler: Handler? = null
    var curDate: String? = null
    var date: Date? = null
    var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_notes)
        bindingDialog = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.bottom_sheet_dialog_string, null, false
        )

        handler = Handler()

        bindingDialog!!.heading1.setOnClickListener {
            binding.editor.setHeading(1)
            bottomSheetDialog!!.dismiss()
        }

        bindingDialog!!.heading2.setOnClickListener {
            binding.editor.setHeading(2)
            bottomSheetDialog!!.dismiss()
        }
        bindingDialog!!.plainText.setOnClickListener { binding.editor.setHeading(3) }


        bindingDialog!!.bulletList.setOnClickListener {
            binding.editor.setUnorderedList()
            bottomSheetDialog!!.dismiss()
        }
        bindingDialog!!.numberlist.setOnClickListener {
            binding.editor.setOrderedList()
            bottomSheetDialog!!.dismiss()
        }
        bindingDialog!!.checkBoxList.setOnClickListener {
            binding.editor.checkBox()
            bottomSheetDialog!!.dismiss()
        }


        //  findViewById<View>(R.id.action_color).setOnClickListener { binding. editor.updateTextColor("#FF3333") }


        // findViewById<View>(R.id.action_unordered_numbered).setOnClickListener{ binding.  editor.insertList(true) }

        bindingDialog!!.devider.setOnClickListener {
            binding.editor.html=binding.editor.html+"<hr>"
            bottomSheetDialog!!.dismiss()
        }

        //findViewById<View>(R.id.action_insert_image).setOnClickListener{ binding.  editor.openImagePicker() }

        bindingDialog!!.insertLink.setOnClickListener {
            binding.editor.insertLink("Ishant website","www.devishant.in")
            bottomSheetDialog!!.dismiss()
        }

        //  findViewById<View>(R.id.action_erase).setOnClickListener { binding. editor.clearAllContents() }

        /*  findViewById<View>(R.id.action_blockquote).setOnClickListener { binding. editor.updateTextStyle(EditorTextStyle.BLOCKQUOTE) }*/



        binding.functionality.setOnClickListener {
            BottomeSheet()!!.show()
        }
        /* binding.save.setOnClickListner{
             val  data =binding.editor.getContentAsHTML()

         }*/

//        binding.editor.setOnTextChangeListener {
//            if ( binding.editor != null &&  binding.editor.size > 0) {
//                if (runnable != null) {
//                    handler!!.removeCallbacks(runnable!!)
//                }
//                runnable = Runnable { saveNote() }
//                handler!!.postDelayed(runnable!!, 1000)
//            } else {
//                // Observers("")
//            }
//        }

//        binding.editor.setOnTextChangeListener(object : TextWatcher,
//            RTextEditorView.OnTextChangeListener {
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun onTextChanged(p0: String?) {
//                if ( binding.editor != null &&  binding.editor.size > 0) {
//                    if (runnable != null) {
//                        handler!!.removeCallbacks(runnable!!)
//                    }
//                    runnable = Runnable { saveNote(p0!!) }
//                    handler!!.postDelayed(runnable!!, 1000)
//                } else {
//
//                }
//            }
//        })



    }

    private fun saveNote(s:String) {
        date = Calendar.getInstance().time
        val dateFormat: DateFormat = SimpleDateFormat("MM-dd-yyyy")
        curDate = dateFormat.format(date)
        val filename = System.currentTimeMillis()
        firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth!!.currentUser!!.uid
        val note = NotesData()
        note.title = binding.title.text.toString()
        note.notesData = s
        note.notesBackground = "#ffffff"
        note.notesTextColor = "#000000"
        note.remainderTime = "0.5"
        note.time = filename.toString()
        note.userId = userId
        note.taskId = "TASK-$filename"

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("Notes/MySpace/TO-DO-LIST/$userId/TASK-$filename")
        ref.setValue(note).addOnCompleteListener {
            Toast.makeText(this,"succes",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit Editor?")
            .setMessage("Are you sure you want to exit the editor?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->

                    if (binding.title.text.equals("")) {
                        finish()
                    } else {
                        //Toast.makeText(this,binding.title.text,Toast.LENGTH_SHORT).show()
                        saveNote(binding.editor.html)
                        finish()
                    }
                })

            .setNegativeButton("No", null)
            .show()
    }

    fun BottomeSheet(): BottomSheetDialog? {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog!!.setContentView(bindingDialog!!.root)
        }

        return bottomSheetDialog

    }
}