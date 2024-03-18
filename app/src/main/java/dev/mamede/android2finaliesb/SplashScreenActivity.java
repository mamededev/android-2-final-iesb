package dev.mamede.android2finaliesb;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
    private static final int DISPLAY_DURATION = 2000;
    private FirebaseAuth authInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        authInstance = FirebaseAuthSingleton.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = authInstance.getCurrentUser();
        if(currentUser != null) {
            goToListCarsActivity();
        } else {
            System.out.println("FirebaseAuth::: User not logged in");
            proceedToLoginAfterDelay();
        }
    }

    private void goToListCarsActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, ListCarsActivity.class);
        startActivity(intent);
    }

    private void proceedToLoginAfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
        }, DISPLAY_DURATION);
    }
}