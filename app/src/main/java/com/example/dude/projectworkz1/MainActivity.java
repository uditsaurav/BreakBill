package com.example.dude.projectworkz1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, tab1.OnFragmentInteractionListener,
        tab2.OnFragmentInteractionListener, tab3.OnFragmentInteractionListener {
    GoogleApiClient googleApiClient;

    public static final String MYPREFERENCE = "mypref";
    public static final String EmailKey = "email";
    public static final String PasswordKey = "name";
    public static final String ImgKey = "image";
    public static final String UidKey = "uid";

    ImageView prof_pic;
    TextView name, email;
    SharedPreferences sharedPreferences;

    String email_notify, id_notify;
    DatabaseReference mref_1, mref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        prof_pic = header.findViewById(R.id.prof_pic);
        name =  header.findViewById(R.id.textViewname);
        email = header.findViewById(R.id.textViewemail);
        Fetch();

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("FRIENDS"));
        tabLayout.addTab(tabLayout.newTab().setText("GROUPS"));
        tabLayout.addTab(tabLayout.newTab().setText("ACTIVITY"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                onFragmentInteraction(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        notify_();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_group) {
            startActivity(new Intent(MainActivity.this, Group_info.class));
            return true;
        } else if (id == R.id.action_friend) {
            Intent intent = new Intent(MainActivity.this, friends_info.class);
            String name_ = sharedPreferences.getString(EmailKey, "");
            String email_ = sharedPreferences.getString(PasswordKey, "");
            String img_url_ = sharedPreferences.getString(ImgKey, "");
            String uid = sharedPreferences.getString(UidKey, "");
            intent.putExtra("username", name_);
            intent.putExtra("email", email_);
            intent.putExtra("uid", uid);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_friend) {

            Intent intent = new Intent(MainActivity.this, friends_info.class);
            String name_ = sharedPreferences.getString(EmailKey, "");
            String email_ = sharedPreferences.getString(PasswordKey, "");
            String img_url_ = sharedPreferences.getString(ImgKey, "");
            String uid = sharedPreferences.getString(UidKey, "");
            intent.putExtra("username", name_);
            intent.putExtra("email", email_);
            intent.putExtra("uid", uid);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, loginscreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        } else if (id == R.id.nav_faq) {
            startActivity(new Intent(MainActivity.this, FAQs.class));
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(MainActivity.this, feedbak.class));
        } else if (id == R.id.nav_developer) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(int tabID) {
    }

    public void Fetch() {
        if (sharedPreferences.contains(EmailKey)) {
            String name_ = sharedPreferences.getString(EmailKey, "").toString();
            String email_ = sharedPreferences.getString(PasswordKey, "").toString();
            String img_url_ = sharedPreferences.getString(ImgKey, "").toString();
            name.setText(name_);
            email.setText(email_);
            Glide.with(this).load(img_url_).into(prof_pic);
        }
    }

    public void notify_() {
        mref_1 = FirebaseDatabase.getInstance().getReference("users");
        email_notify = sharedPreferences.getString(PasswordKey, "");
        mref_1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    User user1 = d.getValue(User.class);
                    if ((user1.getemail().equalsIgnoreCase(email_notify)) == true) {
                        id_notify = user1.getUid();
                        break;
                    }
                }
                mref1 = FirebaseDatabase.getInstance().getReference("notifications").child(id_notify);
                mref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int f = 0;
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            f = 1;
                            Notification n = d.getValue(Notification.class);
                            String friend_name = n.getFriend_name(), friend_id = n.getRequester();
                            String chat_id = n.getChat_id(), notification_id = n.getNotification_id();
                            String message = n.getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            mref1.child(notification_id).setValue(null);

                            NotificationCompat.Builder Builder = new NotificationCompat.Builder(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), FriendsHome.class);
                            intent.putExtra("my_name", sharedPreferences.getString(EmailKey, ""));
                            intent.putExtra("my_id", id_notify);
                            intent.putExtra("friend_id", friend_id);
                            intent.putExtra("friend_name", friend_name);
                            intent.putExtra("my_email", sharedPreferences.getString(PasswordKey, ""));
                            intent.putExtra("chat_id", chat_id);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            Builder.setSmallIcon(R.drawable.ic_home_black_24dp);
                            Builder.setContentTitle(friend_name);
                            Builder.setContentText(message);
                            Builder.setContentIntent(pendingIntent);
                            Builder.setDefaults(android.app.Notification.DEFAULT_ALL);
                            Builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
                            NotificationManager NotificationManager = (NotificationManager) getSystemService(getApplication().NOTIFICATION_SERVICE);
                            NotificationManager.notify(1, Builder.build());
                        }
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

    }
}
