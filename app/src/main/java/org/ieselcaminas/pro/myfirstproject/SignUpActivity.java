package org.ieselcaminas.pro.myfirstproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String TAG = "FirebaseMarc";

    EditText editTextConfPass;
    EditText editTextPass;
    EditText editTextMail;
    EditText editTextUser;

    String email;
    String username;
    String pass;
    String confPass;

    Button buttonSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editTextConfPass = findViewById(R.id.editTextConfPass);
        editTextPass = findViewById(R.id.editTextPass);
        editTextMail = findViewById(R.id.editTextMail);
        editTextUser = findViewById(R.id.editTextUser);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextMail.getText().toString();
                username = editTextUser.getText().toString();
                pass = editTextPass.getText().toString();
                confPass = editTextConfPass.getText().toString();
                if (email.length() == 0 || username.length() == 0 || pass.length() == 0 || confPass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "You missed some fields...", Toast.LENGTH_LONG).show();
                } else if (!pass.equals(confPass)) {
                    Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();


                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username).build();
                                user.updateProfile(profileUpdates);


                               /* User userDB = new User(
                                        username,
                                        email
                                );


                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(userDB);*/


                                user.sendEmailVerification().addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        // Re-enable button
                                        //findViewById(R.id.verify_email_button).setEnabled(true);

                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(SignUpActivity.this, "Verification user sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                            finish();
                                            if (user.isEmailVerified()) {
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Please verify your user.", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Log.e(TAG, "sendEmailVerification", task.getException());
                                            Toast.makeText(SignUpActivity.this, "Failed to send verification user.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
