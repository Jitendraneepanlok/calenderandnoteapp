package com.timespace.notesapp.ui.space.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.timespace.notesapp.R;
import com.timespace.notesapp.adapter.SharedEventAdapter;
import com.timespace.notesapp.common.Common;
import com.timespace.notesapp.firebasemodel.Events;
import com.timespace.notesapp.firebasemodel.SharedEvent;

import java.util.ArrayList;
import java.util.List;


public class SharedEventFragment extends Fragment {
    //    private FirebaseFirestore firebaseFirestore;
//    private FirestoreRecyclerAdapter adapter;
//    Query query;

    List<String> allDocId = new ArrayList<>();
    List<SharedEvent> eventList = new ArrayList<>();
    RecyclerView rvSharedEvent;
    private SharedEventAdapter sharedEventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shared_event, container, false);

        rvSharedEvent = view.findViewById(R.id.rv_shared_event);
//        firebaseFirestore = FirebaseFirestore.getInstance();
//
//        //get data from firebase
//        query = firebaseFirestore.collection("shared_event");
//        FirestoreRecyclerOptions<SharedEvent> options = new FirestoreRecyclerOptions.Builder<SharedEvent>()
//                .setQuery(query,SharedEvent.class)
//                .build();

//        adapter = new FirestoreRecyclerAdapter<SharedEvent, SharedEventViewHolder>(options) {
//            @NonNull
//            @Override
//            public SharedEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_event_item,parent,false);
//                return new SharedEventViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull SharedEventViewHolder holder, int position, @NonNull SharedEvent model) {
//                holder.tvTitle.setText(model.getTitle());
//                holder.tvName.setText(model.getName());
//                holder.tvTime.setText(model.getDate()+" , "+model.getTime());
//
//                holder.tvAdd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        holder.tvAdd.setVisibility(View.GONE);
//
//                        holder.ivCross.setVisibility(View.VISIBLE);
//                        holder.ivTick.setVisibility(View.VISIBLE);
//
//                    }
//                });
//                holder.ivCross.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        holder.ivCross.setVisibility(View.GONE);
//
//                        holder.tvAdd.setVisibility(View.VISIBLE);
//                        holder.ivTick.setVisibility(View.GONE);
//
//                    }
//                });
//
//               // Toast.makeText(getActivity(),model.getTitle().toString(),Toast.LENGTH_SHORT).show();
//            }
//        };
//        rvSharedEvent.setHasFixedSize(false);
//        rvSharedEvent.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
//        rvSharedEvent.setAdapter(adapter);

        Common.db.collection("shared").document(Common.firebaseAuth.getUid()).collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean documentExists;
                        if (task.isSuccessful()){
                            documentExists = !task.getResult().isEmpty();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                allDocId.add(document.getId());
                                //new introduce

                            }
                            for (int i=0;i<allDocId.size();i++){
                                Common.db.collection("event").document(allDocId.get(i))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                Events events = task.getResult().toObject(Events.class);

                                                //   Toast.makeText(HomeActivity.this,events.getSubject(),Toast.LENGTH_SHORT).show();
                                                //   String one = events.getDescriptionEvent().getDescription4();
//                                                tvEventName.setText(events.getSubject());
//                                                tvEventTime.setText(events.getStartTime());

                                                SharedEvent sharedEvent = new SharedEvent();
                                                sharedEvent.setName(events.getAdministrated_by());
                                                sharedEvent.setTitle(events.getSubject_title());
                                                sharedEvent.setDate(events.getStart_date());
                                                sharedEvent.setTime(events.getStart_time());
                                                eventList.add(sharedEvent);

                                                sharedEventAdapter = new SharedEventAdapter(getActivity(), eventList);
                                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                                rvSharedEvent.setLayoutManager(linearLayoutManager);
                                                rvSharedEvent.setAdapter(sharedEventAdapter);
                                                rvSharedEvent.setNestedScrollingEnabled(true);

                                            }
                                        });
                            }

                        }
                    }
                });

        return view;
    }
}