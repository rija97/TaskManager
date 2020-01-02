package com.rija.taskmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rija.taskmanager.Url.url;
import com.rija.taskmanager.api.userapi;
import com.rija.taskmanager.model.User;
import com.rija.taskmanager.serverresponse.SignupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ImageView imgProfile;
    Button btnSignUp;
    EditText etfName, etlName, etUserName, etPassword, etcPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        imgProfile = findViewById(R.id.imgProfile);
        etfName = findViewById(R.id.etfName);
        etlName = findViewById(R.id.etlName);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etcPassword = findViewById(R.id.etcPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().equals((etcPassword.getText().toString()))){
//                    saveImageOnly();
                    signup();
                }else
                {
                    Toast.makeText(SignupActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

            }
        });

    }
    private void BrowseImage(){
        Intent intent = new Intent (Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (data==null){
                Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show();

            }

            Uri uri = data.getData();
            imgProfile.setImageURI(uri);
        }

    }

    private void signup() {

        String FirstName = etfName.getText().toString();
        String LastName  = etlName.getText().toString();
        String Username = etUserName.getText().toString();
        String Password = etPassword.getText().toString();


        User user = new User(FirstName,LastName,Username,Password);
        userapi userapi = url.getInstance().create(userapi.class);
        Call<SignupResponse> signupResponseCall = userapi.registerUser(user);

        signupResponseCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SignupActivity.this, "Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    }

