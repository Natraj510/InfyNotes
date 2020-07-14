package com.example.infynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {

    EditText E1,E2;
    TextView t1,t2;
    Button B1;
    ImageView bk;
    CheckBox remember;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        Intent in1=getIntent();

        E1=(EditText)findViewById(R.id.edit_Text);
        E2=(EditText)findViewById(R.id.edit_Text2);
        B1=(Button)findViewById(R.id.login_bt);
        t1=(TextView)findViewById(R.id.textView4);
        t2=(TextView)findViewById(R.id.textView);
        bk=(ImageView)findViewById(R.id.bk_bt);
        fAuth=FirebaseAuth.getInstance();
        remember=(CheckBox)findViewById(R.id.ck_box);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=E1.getText().toString().trim();
                String password=E2.getText().toString().trim();
                if(password.length()<6){
                    E2.setError("Password should contain atleast 6 characters");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    E1.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    E2.setError("Password is required");
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if(fAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(Main2Activity.this,"Welcome",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Main3Activity.class));

                            }
                            else {
                                Toast.makeText(Main2Activity.this,"Please verify your Email address",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(Main2Activity.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=preferences.getString("remember","");

        if(checkbox.equals("true")){
            Intent in=new Intent(Main2Activity.this,Main3Activity.class);
            startActivity(in);
            finish();
        }
        else if(checkbox.equals("false")){
            Toast.makeText(this,"Please signIn",Toast.LENGTH_SHORT).show();
        }

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main2Activity.this,Register.class);
                startActivity(i);

            }
        });
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(Main2Activity.this,"Checked",Toast.LENGTH_SHORT).show();
                }
                else if(!compoundButton.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(Main2Activity.this,"Unchecked",Toast.LENGTH_SHORT).show();
                }

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2Activity.this,ForgetPass.class);
                startActivity(intent);
            }
        });

    }
}
