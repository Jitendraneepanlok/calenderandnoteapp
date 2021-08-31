package com.timespace.notesapp.ui.notes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.timespace.notesapp.R
import com.timespace.notesapp.base.BaseActivity
import com.timespace.notesapp.databinding.ActivityNotesBinding
import com.timespace.notesapp.firebasemodel.Notes
import com.timespace.notesapp.firebasemodel.TodoNotes
import com.timespace.notesapp.ui.home.viewmodel.NotesViewModel
import com.timespace.notesapp.ui.notes.createNotes.CreateNotesActivity
import com.timespace.notesapp.viewmodel.NotesViewHolder
import java.text.NumberFormat
import java.util.*


class NotesActivity : BaseActivity() {
    private lateinit var binding: ActivityNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    private  var myRef: DatabaseReference?=null
    private  var eventListner: ValueEventListener?=null

    //retrive note
    var layoutManager: RecyclerView.LayoutManager? = null

    var firebaseAuth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var notelist: DatabaseReference? = null
    var adapter: FirebaseRecyclerAdapter<TodoNotes, NotesViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes)
        OnClickListner()
        myRef = databaseFirebase.getReference()
        AccessFirebase()

        //Firebase
        //Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        notelist = database!!.getReference("Notes/MySpace/TO-DO-LIST/${firebaseAuth!!.currentUser!!.uid}")


        binding.rvNote.setHasFixedSize(false)
        // layoutManager=new GridLayoutManager(this,2);
        // layoutManager=new GridLayoutManager(this,2);
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNote.layoutManager = layoutManager

        loadnotes()
    }

    private fun loadnotes() {
        adapter = object : FirebaseRecyclerAdapter<TodoNotes, NotesViewHolder>(
            TodoNotes::class.java,
            R.layout.final_file_item,
            NotesViewHolder::class.java,
            notelist
        ) {
            protected override fun populateViewHolder(
                viewHolder: NotesViewHolder,
                model: TodoNotes,
                position: Int
            ) {
                viewHolder.title.text = model.title

            }
        }
        binding.rvNote.adapter = adapter
    }

    private fun AccessFirebase() {
        eventListner=object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("Firebase", "Value is: ${dataSnapshot.toString()}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        }
        myRef!!.addValueEventListener(eventListner!!)
    }

    private fun OnClickListner() {
        binding.close.setOnClickListener {
            finish()
        }
        binding.myspace.setOnClickListener {
            if (binding.MySpaceList.isVisible) {
                binding.MySpaceList.visibility = View.GONE
            } else {
                binding.MySpaceList.visibility = View.VISIBLE
            }
        }
        binding.toDoLay.setOnClickListener {
            if (binding.toDoListLay.isVisible) {
                binding.toDayArrow.setImageResource(R.drawable.ic_icon_arrow_normal)
                binding.toDoListLay.visibility = View.GONE

            } else {
                binding.toDayArrow.setImageResource(R.drawable.ic_play_arrow_down)
                binding.toDoListLay.visibility = View.VISIBLE
            }
        }
        binding.goalLay.setOnClickListener {
            if (binding.goalListLay.isVisible) {
                binding.goalArrow.setImageResource(R.drawable.ic_icon_arrow_normal)
                binding.goalListLay.visibility = View.GONE

            } else {
                binding.goalArrow.setImageResource(R.drawable.ic_play_arrow_down)
                binding.goalListLay.visibility = View.VISIBLE
            }
        }
        binding.toDayArrow.setOnClickListener {
            binding.rvNote.visibility = View.VISIBLE
        }
        binding.add.setOnClickListener {
            startActivity(Intent(this@NotesActivity, CreateNotesActivity::class.java))
        }

    }
}