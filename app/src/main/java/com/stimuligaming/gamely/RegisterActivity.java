package com.stimuligaming.gamely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stimuligaming.gamely.helperClasses.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private String FNAME, LNAME, EMAIL, USERNAME, PASSWORD, UID;
    private static final String TAG = "register";
    private FirebaseAuth mAuth;
    private User user;
    TextInputEditText fNAmeField, lNAmeField, emailField, userNameField, pwdField;
    TextView actionText;
    MaterialButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        fNAmeField = findViewById(R.id.first_name_field);
        lNAmeField = findViewById(R.id.last_name_field);
        emailField = findViewById(R.id.email_field);
        pwdField = findViewById(R.id.password_field);
        userNameField = findViewById(R.id.username_field);
        actionText = findViewById(R.id.login_text);
        submitBtn = findViewById(R.id.register_btn);

        submitBtn.setOnClickListener(click -> {
            getValues();
            createAccount(EMAIL, PASSWORD);
        });
    }

    private void getValues(){
        FNAME = Objects.requireNonNull(fNAmeField.getText()).toString();
        LNAME = Objects.requireNonNull(lNAmeField.getText()).toString();
        EMAIL = Objects.requireNonNull(emailField.getText()).toString();
        USERNAME = Objects.requireNonNull(userNameField.getText()).toString();
        PASSWORD = Objects.requireNonNull(pwdField.getText()).toString();
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Register success
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            newUser(fUser.getUid());
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setView(R.layout.registeration_success_dialog);
                            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            // Register failed
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "User creation failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void newUser(String uid) {
        user = new User(FNAME, LNAME, EMAIL, USERNAME, uid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        reference.child(uid).setValue(user);
    }
}
