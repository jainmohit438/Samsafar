package com.example.samsafar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText etmail_log, etpass_log;
    Button blogin_log;
    Button bcreate_log;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        //Checking For User Existance
        if(firebaseUser!=null) {
            Intent intent2 = new Intent(Login.this,MainActivity.class);
            startActivity(intent2);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etmail_log = (EditText)findViewById(R.id.etmail_log);
        etpass_log = (EditText)findViewById(R.id.etpass_log);
        blogin_log = (Button)findViewById(R.id.blogin_log);
        bcreate_log = (Button)findViewById(R.id.bcreate_log);

        //Firebase

        auth = FirebaseAuth.getInstance();


        //Create Button

        bcreate_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Login.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        blogin_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lmail = etmail_log.getText().toString();
                String lpass = etpass_log.getText().toString();

                if(TextUtils.isEmpty(lmail) || TextUtils.isEmpty(lpass)) {
                    Toast.makeText(Login.this,"Fill Complete Data",Toast.LENGTH_LONG).show();
                }

                else {
                    auth.signInWithEmailAndPassword(lmail,lpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {

                                Intent intent = new Intent(Login.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                            else {
                                Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }

            }
        });


    }
}