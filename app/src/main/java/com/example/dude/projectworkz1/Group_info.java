package com.example.dude.projectworkz1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group_info extends AppCompatActivity {

    EditText input;
    ListView listView;
    String[] s={"hey","you","boy"};
    List<String> arrayList_group_name,arrayList_group_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        listView=findViewById(R.id.group_list_view);
        arrayList_group_name=new ArrayList<String>();
        arrayList_group_id=new ArrayList<String>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                List<String> group_name=arrayList_group_name,group_id=arrayList_group_id;
                String groupname_=group_name.get(position),groupid_=group_id.get(position);
                Intent intent=new Intent(Group_info.this,InsideGroups.class);
                intent.putExtra("group_name",groupname_);
                intent.putExtra("group_id",groupid_);
                startActivity(intent);

            }
        });
        Start();
    }

    protected void Start() {
        super.onStart();
        DatabaseReference mref2=FirebaseDatabase.getInstance().getReference("groups");


        Log.i("moko","ok1");
        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList_group_id.clear();
                arrayList_group_name.clear();
                for(DataSnapshot groupss:dataSnapshot.getChildren())
                {
                    Group g=groupss.getValue(Group.class);
                    String group_name= Objects.requireNonNull(g).getGroup_name();
                    arrayList_group_name.add(group_name);
                    String group_id=g.getGroup_uid();
                    arrayList_group_id.add(group_id);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(Group_info.this,android.R.layout.simple_list_item_1,arrayList_group_name);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"data base error",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void make_a_new_group(View v)
    {
        input=findViewById(R.id.group_name);
        String group_name=input.getText().toString();
        if(group_name.length()==0)
        {
            Toast.makeText(Group_info.this,"PLEASE ENTER A GROUP NAME !",Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference("groups");
        Group group= new Group(group_name);
        String key=mref.push().getKey();
        group.group_uid=key;
        mref.child(key).setValue(group);

    }

}
class Group {

    public String group_uid,group_name;
    public Group()
    {

    }
    public Group(String group_name)
    {
        this.group_name=group_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_uid() {
        return group_uid;
    }
}
