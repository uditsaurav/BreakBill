package com.example.dude.projectworkz1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
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

public class tab1 extends Fragment {
    private OnFragmentInteractionListener mListener;
    static tab1 tab1_object;

    public tab1() {
    }

    static String my_id = "", my_name,my_email, my_NAME;
    boolean already_friend = false;
    static List<String> arrayList_friend_name, arrayList_friend_id, arrayList_chat_id;
    static List<ListItem1> listItems;
    RecyclerView recyclerViewFrnd;
    private RecyclerView.Adapter adapter;

    public static String MYPREFERENCE = "mypref";
    public static final String PasswordKey = "name";
    public static final String EmailKey = "email";
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        my_email = sharedPreferences.getString(PasswordKey, "");
        my_NAME = sharedPreferences.getString(EmailKey, "");

        arrayList_friend_name = new ArrayList<>();
        arrayList_friend_id = new ArrayList<>();
        arrayList_chat_id = new ArrayList<>();
        listItems = new ArrayList<>();

        recyclerViewFrnd = view.findViewById(R.id.friend_list_view1);
        recyclerViewFrnd.setHasFixedSize(true);
        recyclerViewFrnd.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference mrefparent = FirebaseDatabase.getInstance().getReference("users");
        mrefparent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotpar) {
                for (DataSnapshot d : dataSnapshotpar.getChildren()) {
                    User u = d.getValue(User.class);
                    if (u != null && u.getemail().equalsIgnoreCase(my_email)) {
                        my_id = u.getUid();
                        my_name = u.getname();
                        break;
                    }
                }

                DatabaseReference mref = FirebaseDatabase.getInstance().getReference("friends").child(my_id);
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayList_friend_id.clear();
                        arrayList_friend_name.clear();
                        arrayList_chat_id.clear();
                        listItems.clear();
                        for (DataSnapshot friend_ : dataSnapshot.getChildren()) {
                            friend g = friend_.getValue(friend.class);
                            String friend_name = g.getFriend_name();
                            String friend_id = g.getFriend_id();
                            String chat_id = g.getChat_id();
                            arrayList_friend_name.add(friend_name);
                            arrayList_friend_id.add(friend_id);
                            arrayList_chat_id.add(chat_id);
                            ListItem1 l = new ListItem1(friend_name, friend_name);
                            listItems.add(l);
                         }
                        adapter = new MyAdapter1(getContext(), listItems);
                        recyclerViewFrnd.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "oncancld on create", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        tab1_object = this;
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
        Intent intent = new Intent(tab1_object.getActivity(), FriendsHome.class);
        intent.putExtra("my_name", my_NAME);
        intent.putExtra("my_id", my_id);
        intent.putExtra("friend_id", arrayList_friend_id.get(position));
        intent.putExtra("friend_name", arrayList_friend_name.get(position));
        intent.putExtra("chat_id", arrayList_chat_id.get(position));
        intent.putExtra("my_email",my_email);
        Objects.requireNonNull(tab1_object.getActivity()).startActivity(intent);
    }

}

class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.ViewHolder1> {

    public Context context;
    private List<ListItem1> listItems;

    MyAdapter1(Context context, List<ListItem1> listItem) {
        this.context = context;
        this.listItems = listItem;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_item, parent, false);

        return new ViewHolder1(v, context, (ArrayList<ListItem1>) listItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        ListItem1 listItem = listItems.get(position);
        holder.name.setText(listItem.getName());
        holder.description.setText(listItem.getDescription());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;

        ViewHolder1(View view, Context ctx, ArrayList<ListItem1> items) {
            super(view);
            listItems = items;
            context = ctx;
            view.setOnClickListener(this);
            name = view.findViewById(R.id.titleR);
            description = view.findViewById(R.id.descriptionR);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            tab1 t = new tab1();
            t.onclickcall(v, position);
        }
    }
}

class ListItem1 {
    private String name;
    private String description;

    ListItem1(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
