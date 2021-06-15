package com.example.dude.projectworkz1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.dude.projectworkz1.GroupHome.setdate;

public class GroupHome extends AppCompatActivity {

    ListView l;
    String date;
    TextView groupName;
    EditText groupAmount;
    EditText groupDiscription;
    ArrayList<Integer> paid=new ArrayList<Integer>();
    ArrayList<Integer> expenses=new ArrayList<Integer>();
    ArrayList<String> description=new ArrayList<String>();
    ArrayList<String> member=new ArrayList<String>();

    //set group members bno
    int memberno=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_home);
        l=findViewById(R.id.listViewgroup);
        groupAmount=findViewById(R.id.groupamount);
        groupName=findViewById(R.id.groupname);
        groupDiscription=findViewById(R.id.grpdescription);
        add("hii",98,89);
        add("how",78,92);
        add("hello",90,36);


    }
    public void add(String des,int pay,int exp)
    {
        paid.add(pay);
        expenses.add(exp);
        description.add(des);
        GroupAdapter adapter=new GroupAdapter(this,description,paid,expenses);
        l.setAdapter(adapter);
    }
    public static String setdate()
    {

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + (month+1) + "/" + year;
        return date;

    }


    public void customSplit(View view) {
        member.add("udit");
        member.add("ashish");
        member.add("Somnath");
        Intent i=new Intent(this,GroupHome.class);
        i.putStringArrayListExtra("members",member);
        startActivity(i);

    }

    public void equalSplit(View view) {
        add(groupDiscription.getText().toString().trim(),Integer.parseInt(groupAmount.getText().toString().trim()),Integer.parseInt(groupAmount.getText().toString().trim()));
    }

}
class GroupAdapter extends ArrayAdapter<String>
{
    Context c;
    ArrayList<Integer> paid=new ArrayList<Integer>();
    ArrayList<Integer> expenses=new ArrayList<Integer>();
    ArrayList<String> desc=new ArrayList<String>();
    GroupAdapter(Context c, ArrayList<String> desc, ArrayList<Integer> paid, ArrayList<Integer> expenses)
    {
        super(c,R.layout.single_row_friendshome,R.id.des,desc);
        this.c=c;
        this.paid=paid;
        this.expenses=expenses;
        this.desc=desc;
    }
    class MyViewHolder{
        TextView mydes;
        TextView paiddes;
        TextView mytranx;
        TextView mynet;

        public MyViewHolder(View v) {
            this.mydes = v.findViewById(R.id.des);
            this.paiddes = v.findViewById(R.id.amo);
            this.mytranx = v.findViewById(R.id.tranxdescription);
            this.mynet = v.findViewById(R.id.net);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        MyViewHolder holder=null;
        if(row==null)
        {
            LayoutInflater inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.single_row_friendshome,parent,false);
            holder=new MyViewHolder(row);
            row.setTag(holder);
        }
        else{
            holder= (MyViewHolder) row.getTag();
        }
        holder.mydes.setText(desc.get(position));
        holder.paiddes.setText("You paid"+"   Rs "+paid.get(position)+"  on  "+setdate());
        if((int)(paid.get(position))-(int)(expenses.get(position))>=0)
        {
            holder.mytranx.setTextColor(Color.parseColor("#34ed23"));
            holder.mytranx.setText("You Lent");
            holder.mynet.setText("Rs "+((int)(paid.get(position))-(int)(expenses.get(position))));
        }
        else
        {
            holder.mytranx.setTextColor(Color.parseColor("#ed2222"));
            holder.mytranx.setText("You Borrowed");
            holder.mynet.setText("Rs "+((int)(expenses.get(position))-(int)(paid.get(position))));
        }

        return row;
    }

}
