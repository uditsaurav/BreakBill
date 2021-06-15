package com.example.dude.projectworkz1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class tab2 extends Fragment {
    private OnFragmentInteractionListener mListener;

    public tab2() {
    }

    static tab2 tab2_object;
    static List<String> arrayList_group_name, arrayList_group_id;
    static List<ListItem2> listItems;
    RecyclerView recyclerViewGrp;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        arrayList_group_name = new ArrayList<>();
        arrayList_group_id = new ArrayList<>();
        listItems = new ArrayList<>();

        recyclerViewGrp = view.findViewById(R.id.friend_list_view2);
        recyclerViewGrp.setHasFixedSize(true);
        recyclerViewGrp.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference mref2 = FirebaseDatabase.getInstance().getReference("groups");
        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList_group_id.clear();
                arrayList_group_name.clear();
                listItems.clear();
                for (DataSnapshot groupss : dataSnapshot.getChildren()) {
                    Group g = groupss.getValue(Group.class);
                    String group_name = g.getGroup_name();
                    arrayList_group_name.add(group_name);
                    String group_id = g.getGroup_uid();
                    arrayList_group_id.add(group_id);
                    ListItem2 l = new ListItem2(group_name);
                    listItems.add(l);
                }
                adapter = new MyAdapter2(getContext(), listItems);
                recyclerViewGrp.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "data base error", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        tab2_object = this;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int tabID);
    }

    public void onclickcall(View v, int position) {
        Intent intent = new Intent(tab2_object.getActivity(), inside_group.class);
        intent.putExtra("group_name", arrayList_group_name.get(position));
        intent.putExtra("group_id", arrayList_group_id.get(position));
        Objects.requireNonNull(tab2_object.getActivity()).startActivity(intent);
    }
}

class ListItem2 {
    private String name;

    public ListItem2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder2> {

    private Context context;
    private List<ListItem2> listItems;

    public MyAdapter2(Context context, List listItem) {
        this.context = context;
        this.listItems = listItem;
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_list_item, parent, false);

        return new ViewHolder2(v, context, (ArrayList<ListItem2>) listItems);
    }

    @Override
    public void onBindViewHolder(ViewHolder2 holder, int position) {
        ListItem2 listItem = listItems.get(position);
        holder.name.setText(listItem.getName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;

        ViewHolder2(View view, Context ctx, ArrayList<ListItem2> items) {
            super(view);
            listItems = items;
            context = ctx;
            view.setOnClickListener(this);
            name = view.findViewById(R.id.faQuestion);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            tab2 t = new tab2();
            t.onclickcall(v, position);
        }
    }
}