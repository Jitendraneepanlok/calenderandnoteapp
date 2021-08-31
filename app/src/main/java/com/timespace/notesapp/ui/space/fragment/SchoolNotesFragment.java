package com.timespace.notesapp.ui.space.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.timespace.notesapp.R;
import com.timespace.notesapp.adapter.NoteAdapter;
import com.timespace.notesapp.common.Common;
import com.timespace.notesapp.firebasemodel.Notes;

import java.util.ArrayList;
import java.util.List;


public class SchoolNotesFragment extends Fragment {

    String noteDocId="";
    List<String> allDocId = new ArrayList<>();
    List<Notes> notesList = new ArrayList<>();
    RecyclerView rvNotes;
    private NoteAdapter noteAdapter;

    //new
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    FirebaseAuth firebaseAuth;
    //newsAdapter;
    Query query;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_school_notes, container, false);

        rvNotes = view.findViewById(R.id.rv_notes);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //get data from firebase
        query = firebaseFirestore.collection("notes")
                .orderBy("created_on", Query.Direction.DESCENDING)
        .whereEqualTo("userid",firebaseAuth.getUid()
        );
        FirestoreRecyclerOptions<Notes> options = new FirestoreRecyclerOptions.Builder<Notes>()
                .setQuery(query,Notes.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Notes, NotesViewHolder>(options) {
            @NonNull
            @Override
            public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes,parent,false);
                return new NotesViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull NotesViewHolder holder, int position, @NonNull Notes model) {
                holder.tvTitle.setText(model.getTitle());
                holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        };

        rvNotes.setHasFixedSize(false);
        rvNotes.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        rvNotes.setAdapter(adapter);

        return  view;
    }

    private class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        //new
        private FirebaseFirestore firebaseFirestore;
        private FirestoreRecyclerAdapter subAadapter;
        FirebaseAuth firebaseAuth;
        //newsAdapter;
        Query subQuery;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);

            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();

            //get data from firebase
            subQuery = firebaseFirestore.collection("notes")
                    .orderBy("created_on", Query.Direction.DESCENDING)
                    .whereEqualTo("parent_id",2);
            FirestoreRecyclerOptions<Notes> options = new FirestoreRecyclerOptions.Builder<Notes>()
                    .setQuery(subQuery,Notes.class)
                    .build();

            subAadapter = new FirestoreRecyclerAdapter<Notes, NotesViewHolder>(options) {
                @NonNull
                @Override
                public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_notes,parent,false);
                    return new NotesViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull NotesViewHolder holder, int position, @NonNull Notes model) {
                    holder.tvTitle.setText(model.getTitle());

                }
            };

            subAadapter.startListening();
            
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    @Override
    public void onResume() {
        super.onResume();

    }
}
