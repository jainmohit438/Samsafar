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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText etname_reg,etmail_reg,etpass_reg;
    Button bSignUp_reg;

    // Firebase
    FirebaseAuth auth;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etname_reg = (EditText)findViewById(R.id.etname_reg);
        etmail_reg = (EditText)findViewById(R.id.etmail_reg);
        etpass_reg = (EditText)findViewById(R.id.etpass_reg);
        bSignUp_reg = (Button)findViewById(R.id.bSignUp_reg);

        auth = FirebaseAuth.getInstance();

        bSignUp_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String un = etname_reg.getText().toString();
                String um = etmail_reg.getText().toString();
                String upass = etpass_reg.getText().toString();

                if (TextUtils.isEmpty(un) || TextUtils.isEmpty(um) || TextUtils.isEmpty(upass)){
                    Toast.makeText(Register.this,"Fill All entries ",Toast.LENGTH_LONG).show();
                }

                else {
                    RegisterNow(un,um,upass);
                }

            }
        });
    }


    private void RegisterNow(final String rname, String rmail, String rpass) {
        auth.createUserWithEmailAndPassword(rmail,rpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    myref = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);

                    //Hash Map

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("UserName", rname);
                    hashMap.put("ImageURL", "default");


                    //Opening main Activity After SuccessFull Registration

                    myref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {
                                Intent intent = new Intent(Register.this,Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }

                else {
                    Toast.makeText(Register.this,"Invalid Input",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}