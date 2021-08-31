package com.timespace.notesapp.ui.space.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.timespace.notesapp.R;
import com.timespace.notesapp.firebasemodel.SharedNote;

public class SharedNotesFragment extends Fragment {

    RecyclerView rvSharedNotes;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shared_notes, container, false);
        rvSharedNotes = view.findViewById(R.id.rv_shared_event);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //get data from firebase
        query = firebaseFirestore.collection("shared_note");
        FirestoreRecyclerOptions<SharedNote> options = new FirestoreRecyclerOptions.Builder<SharedNote>()
                .setQuery(query,SharedNote.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<SharedNote, SharedNotesViewHolder>(options) {
            @NonNull
            @Override
            public SharedNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_notes_item,parent,false);
                return new SharedNotesViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SharedNotesViewHolder holder, int position, @NonNull SharedNote model) {
                holder.tvTitle.setText(model.getTitle());
                holder.tvName.setText(model.getName());

                holder.tvJoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.tvJoin.setVisibility(View.GONE);

                        holder.ivCross.setVisibility(View.VISIBLE);
                        holder.ivTick.setVisibility(View.VISIBLE);

                    }
                });
                holder.ivCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.ivCross.setVisibility(View.GONE);

                        holder.tvJoin.setVisibility(View.VISIBLE);
                        holder.ivTick.setVisibility(View.GONE);

                    }
                });

                // Toast.makeText(getActivity(),model.getTitle().toString(),Toast.LENGTH_SHORT).show();
            }
        };
        rvSharedNotes.setHasFixedSize(false);
        rvSharedNotes.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        rvSharedNotes.setAdapter(adapter);

        return view;
    }
    private class SharedNotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvName;
        TextView tvJoin;
        ImageView ivTick,ivCross;
        public SharedNotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvName = itemView.findViewById(R.id.tv_name);
            tvJoin = itemView.findViewById(R.id.tv_join);
            ivTick = itemView.findViewById(R.id.tick);
            ivCross = itemView.findViewById(R.id.cross);
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
}