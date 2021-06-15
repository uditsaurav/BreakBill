package com.example.dude.projectworkz1;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.dude.projectworkz1.FAQs.cntxt;

public class FAQs extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    static private List<ListItem> listItems;
    static Context cntxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        cntxt = this;
        recyclerView = findViewById(R.id.faq_recycler);
        recyclerView.setHasFixedSize(true);
        //every item has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        String ques[] = {
                "Can I split an expense unequally?",
                "Can I split my expenses by percentages?",
                "Can I be in more than group?",
                "Is there any Limit of adding bills?",
                "Can I register my complaints,if any?",
                "How can I make payments for settlements?",
                "How do I request money settlement by friend?",
                "Help,I can't log in to my accounts!",
                "Help,my balances are wrong!",
                "How do I delete the group after all settlements",
                "How do I remove a person from my friend list",
                "How do I remove a person from my group"
        };

        for (int i = 0; i < ques.length; i++) {
            ListItem l = new ListItem(ques[i]);
            listItems.add(l);
        }

        adapter = new MyAdap(this, listItems);
        recyclerView.setAdapter(adapter);
    }

    public void Clickans(int position) {
        String s=listItems.get(position).toString();
        /*Intent intent = new Intent(FAQs.this, FaqAnswers.class);
        intent.putExtra("questnNo", position);
        intent.putExtra("questn",s);
        startActivity(intent);*/
    }

    public void goHome(View view) {
        Intent intent = new Intent(FAQs.this, MainActivity.class);
        startActivity(intent);
    }
}

class ListItem {
    private String name;

    public ListItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class MyAdap extends RecyclerView.Adapter<MyAdap.ViewHolder> {

    private Context context;
    private List<ListItem> listItems;

    public MyAdap(Context context, List listItem) {
        this.context = context;
        this.listItems = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_list_item, parent, false);

        return new ViewHolder(v, context, (ArrayList<ListItem>) listItems);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.name.setText(listItem.getName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public ViewHolder(View view, Context ctx, ArrayList<ListItem> items) {
            super(view);
            listItems = items;
            context = ctx;
            view.setOnClickListener(this);
            name = view.findViewById(R.id.faQuestion);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Toast.makeText(cntxt, "in development process", Toast.LENGTH_SHORT).show();
            //FAQs f = new FAQs();
            //f.Clickans(position);
        }
    }
}

