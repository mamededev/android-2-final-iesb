package dev.mamede.android2finaliesb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import dev.mamede.android2finaliesb.FirebaseAuthSingleton;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    
        firebaseAuth = FirebaseAuthSingleton.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, ListCarsActivity.class);
            startActivity(intent);
            finish();
        }

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
    
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
              startActivity(intent);
          }
      });

        findViewById(R.id.main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                View focusedView = getCurrentFocus();

                if (focusedView != null) {
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    focusedView.clearFocus();
                }

                return true;
            }
        });
    }

    private void signIn() {
      String email = inputEmail.getText().toString().trim();
      String password = inputPassword.getText().toString().trim();

      if (TextUtils.isEmpty(email)) {
        new AlertDialog.Builder(LoginActivity.this)
            .setTitle("Erro")
            .setMessage("O campo de email está vazio.")
            .setPositiveButton(android.R.string.ok, null)
            .show();
        return;
    }
      
      if (TextUtils.isEmpty(password)) {
        new AlertDialog.Builder(LoginActivity.this)
            .setTitle("Erro")
            .setMessage("O campo de senha está vazio.")
            .setPositiveButton(android.R.string.ok, null)
            .show();
        return;
      }

      firebaseAuth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if (task.isSuccessful()) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Login bem-sucedido")
                .setMessage("Login realizado com sucesso.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoginActivity.this, ListCarsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
            } else {
              new AlertDialog.Builder(LoginActivity.this)
              .setTitle("Erro de autenticação")
              .setMessage("Falha na autenticação.")
              .setPositiveButton(android.R.string.ok, null)
              .setIcon(android.R.drawable.ic_dialog_alert)
              .show();
            }
        }
    });
    }
}