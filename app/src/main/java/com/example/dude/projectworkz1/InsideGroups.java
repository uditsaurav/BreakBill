package com.example.dude.projectworkz1;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class InsideGroups extends AppCompatActivity {
    ListView customaddbilllv;
    ArrayList<String> member=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_groups);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width-15),(int)(height*.75));
        customaddbilllv=findViewById(R.id.customaddbilllv);
        member=getIntent().getExtras().getStringArrayList("members");

        GrpCustomAdapter adapter=new GrpCustomAdapter(this,member);
        customaddbilllv.setAdapter(adapter);
    }

    public void groupcustomadd_bill(View view) {
    }
}
class GrpCustomAdapter extends ArrayAdapter<String>
{
    Context c;
    ArrayList<Integer> paid=new ArrayList<Integer>();
    ArrayList<Integer> expenses=new ArrayList<Integer>();
    ArrayList<String> member=new ArrayList<String>();
    GrpCustomAdapter(Context c, ArrayList<String> member)
    {
        super(c,R.layout.group_addbill_singlerow,R.id.membersname,member);
        this.c=c;
        this.member=member;
    }
    class MyViewHolder{
        TextView membername;
        EditText pay;
        EditText expense;

        public MyViewHolder(View v) {
            this.membername = v.findViewById(R.id.membersname);
            this.pay = v.findViewById(R.id.memberpaid);
            this.expense = v.findViewById(R.id.memberexpense);
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
            row=inflater.inflate(R.layout.group_addbill_singlerow,parent,false);
            holder=new MyViewHolder(row);
            row.setTag(holder);
        }
        else{
            holder= (MyViewHolder) row.getTag();
        }
        holder.membername.setText(member.get(position));
        try {
            if(holder.pay==null)
                holder.pay.requestFocus();
            else
                paid.add(parseInt(holder.pay.getText().toString()));
            if(holder.expense==null)
                holder.expense.requestFocus();
            else
                paid.add(parseInt(holder.expense.getText().toString()));


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return row;
    }

}
