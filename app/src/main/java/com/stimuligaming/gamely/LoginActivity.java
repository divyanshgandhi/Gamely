package com.stimuligaming.gamely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;

public class LoginActivity extends AppCompatActivity {
    private static final String APPID = "216e4b0515cc4b47a07360ac87e4ec92";
    private static final String TAG = "login" ;
    String EMAIL, PASSWORD, intEMAIL;
    private FirebaseAuth mAuth;
    TextInputEditText userNameField, pwdField;
    TextView actionText;
    MaterialButton submitBtn;

    String userUid;
    private RtmClient mRtmClient;
    private boolean mIsInChat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //Getting all the elements
        userNameField = findViewById(R.id.email_field);
        pwdField = findViewById(R.id.password_field);
        submitBtn = findViewById(R.id.login_btn);
        actionText = findViewById(R.id.register_text);


        //Set all the clickers on the elements
        submitBtn.setOnClickListener(click -> {
            EMAIL = Objects.requireNonNull(userNameField.getText()).toString();
            PASSWORD = Objects.requireNonNull(pwdField.getText()).toString();
            if (EMAIL!=null && PASSWORD!=null){
                //submitBtn.setEnabled(false);
                signIn(EMAIL, PASSWORD);
            }
        });

        actionText.setOnClickListener(click ->{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //Set email after registration
        Intent intent = getIntent();
        intEMAIL = intent.getStringExtra("user");
        if(intEMAIL != null)
            userNameField.setText(intEMAIL);
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            userUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Intent intent = new Intent();
                            setResult(1,intent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            // ...
                        }
                    }
                });
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
