package com.timespace.notesapp.ui.space;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;
import com.timespace.notesapp.R;
import com.timespace.notesapp.firebasemodel.Spaces;
import com.timespace.notesapp.ui.notes.createNotes.CreateNotesActivity;

public class SpacesActivity extends AppCompatActivity {

    RecyclerView rvSpaceList;

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaces);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        rvSpaceList = findViewById(R.id.rv_spaces_list);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //get data from firebase
        query = firebaseFirestore.collection("spaces");
        FirestoreRecyclerOptions<Spaces> options = new FirestoreRecyclerOptions.Builder<Spaces>()
                .setQuery(query,Spaces.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Spaces, SpaceViewHolder>(options) {
            @NonNull
            @Override
            public SpaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spaces,parent,false);
                return new SpaceViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SpaceViewHolder holder, int position, @NonNull Spaces model) {
                holder.tvTitle.setText(model.getTitle());
                Picasso.get().load(model.getIcon()).into(holder.ivIcon);

                holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0){
                            Intent intent = new Intent(SpacesActivity.this, MySpacesActivity.class);
                            startActivity(intent);
                        }
                        else if (position == 1){
                            Intent intent = new Intent(SpacesActivity.this, SharedActivity.class);
                            startActivity(intent);
                        }
                        else if (position == 2){
                            Intent intent = new Intent(SpacesActivity.this, My_SchoolActivity.class);
                            startActivity(intent);
                        }
                        else if (position == 3){
                            Intent intent = new Intent(SpacesActivity.this, CreateNotesActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        rvSpaceList.setHasFixedSize(false);
        rvSpaceList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rvSpaceList.setAdapter(adapter);


    }
    private class SpaceViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_space_text);
            ivIcon = itemView.findViewById(R.id.iv_space_icon);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}