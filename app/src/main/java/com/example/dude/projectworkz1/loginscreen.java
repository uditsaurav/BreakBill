package com.example.dude.projectworkz1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import java.util.Objects;

public class loginscreen extends AppCompatActivity {
    GoogleApiClient googleApiClient;
    SignInButton signin;
    public int REQ_CODE = 9001;
    public int ival;

    public static final String MYPREFERENCE = "mypref";
    public static final String EmailKey = "email";
    public static final String PasswordKey = "name";
    public static final String ImgKey = "image";
    public static final String UidKey = "uid";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ival = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        ImageView imageView = findViewById(R.id.loginimageview);
        //Glide.with(this).load(R.drawable.bb).into(imageView);

        Firebase.setAndroidContext(this);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        com.google.firebase.FirebaseApp.initializeApp(this);

        signin = findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin(signin);
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ival == 0) {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (!opr.isDone()) {
                Intent intent = new Intent(loginscreen.this, SlideScreen.class);
                startActivity(intent);
            }
            Intent splashIntent = new Intent(this, SplashScreen.class);
            startActivity(splashIntent);
            ival = 1;
        } else {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (opr.isDone()) {
                Intent intent = new Intent(loginscreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void signin(View view) {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                handleresult(result);
            }
        }
    }

    public void handleresult(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
            String name_ = Objects.requireNonNull(googleSignInAccount).getDisplayName(), email_ = googleSignInAccount.getEmail();
            String img_url_ = Objects.requireNonNull(googleSignInAccount.getPhotoUrl()).toString();
            Reg_user r = new Reg_user(email_, name_);
            r.register();
            save(name_, email_, img_url_);

            Intent intent1 = new Intent(loginscreen.this, MainActivity.class);
            startActivity(intent1);
        }
    }

    public void save(String name, String email, String img_) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EmailKey, name);
        editor.putString(PasswordKey, email);
        editor.putString(ImgKey, img_);
        editor.putString(UidKey, " ");
        editor.apply();
    }
}
