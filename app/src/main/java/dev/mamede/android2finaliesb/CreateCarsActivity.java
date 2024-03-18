package dev.mamede.android2finaliesb;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

public class CreateCarsActivity extends AppCompatActivity {

  private EditText anoEditText;
  private EditText anotacoesEditText;
  private EditText corEditText;
  private EditText marcaEditText;
  private EditText modeloEditText;
  private EditText nomeDoProprietarioEditText;
  private EditText placaEditText;
  private Button submitButton;
  private String carId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      EdgeToEdge.enable(this);
      setContentView(R.layout.activity_create_cars);

      anoEditText = findViewById(R.id.anoEditText);
      anotacoesEditText = findViewById(R.id.anotacoesEditText);
      corEditText = findViewById(R.id.corEditText);
      marcaEditText = findViewById(R.id.marcaEditText);
      modeloEditText = findViewById(R.id.modeloEditText);
      nomeDoProprietarioEditText = findViewById(R.id.nomeDoProprietarioEditText);
      placaEditText = findViewById(R.id.placaEditText);
      submitButton = findViewById(R.id.submitButton);
      carId = getIntent().getStringExtra("CAR_ID");
      
      if (carId != null) {
        DatabaseReference carRef = FirebaseDatabase.getInstance().getReference("Cars").child(carId);
        carRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Car car = dataSnapshot.getValue(Car.class);
                anoEditText.setText(car.getAno());
                anotacoesEditText.setText(car.getAnotacoes());
                corEditText.setText(car.getCor());
                marcaEditText.setText(car.getMarca());
                modeloEditText.setText(car.getModelo());
                nomeDoProprietarioEditText.setText(car.getNomeDoProprietario());
                placaEditText.setText(car.getPlaca());
            }
    
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Falha ao ler valor.", databaseError.toException());
            }
        });
    }

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

      submitButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String ano = anoEditText.getText().toString();
              String anotacoes = anotacoesEditText.getText().toString();
              String cor = corEditText.getText().toString();
              String marca = marcaEditText.getText().toString();
              String modelo = modeloEditText.getText().toString();
              String nomeDoProprietario = nomeDoProprietarioEditText.getText().toString();
              String placa = placaEditText.getText().toString();

              Car newCar = new Car(ano, anotacoes, cor, marca, modelo, nomeDoProprietario, placa);

              FirebaseDatabase database = FirebaseDatabase.getInstance();
              DatabaseReference carsReference = database.getReference("Cars");

              if (carId != null) {
                newCar.setId(carId);
                carsReference.child(carId).setValue(newCar);
            } else {
                String id = carsReference.push().getKey();
                newCar.setId(id); 
                carsReference.child(id).setValue(newCar); 
            }
              finish();
          }
      });
  }
}