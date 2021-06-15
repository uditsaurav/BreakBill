package com.example.dude.projectworkz1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tab3 extends Fragment {
    private OnFragmentInteractionListener mListener;
    String email, myid;
    List<String> feed;
    ListView feedListView;

    public tab3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab3, container, false);

        feedListView = v.findViewById(R.id.feed_list);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("name", "");
        feed = new ArrayList<String>();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("users");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    User u = s.getValue(User.class);
                    if (u.getemail().equalsIgnoreCase(email)) {
                        myid = u.getUid();
                        break;
                    }
                }
                DatabaseReference mref1 = FirebaseDatabase.getInstance().getReference("activity").child(myid);
                mref1.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        feed.clear();
                        ;
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Activity_news a = d.getValue(Activity_news.class);
                            feed.add(0, a.getFriend_name() + " " + a.getMessage());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, feed);

                        feedListView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int tabID);
    }

}
