package dev.mamede.android2finaliesb;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListCarsActivity extends AppCompatActivity {

    private TextView emptyMessage;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      EdgeToEdge.enable(this);
      setContentView(R.layout.activity_list_cars);

      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      Button addCarButton = findViewById(R.id.addCarButton);
      addCarButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
            // Navigate to the car registration activity
            Intent intent = new Intent(ListCarsActivity.this, CreateCarsActivity.class);
            startActivity(intent);
        }
    });

      ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          return insets;
      });

      emptyMessage = findViewById(R.id.emptyMessage);

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference carsReference = database.getReference("Cars");
      recyclerView = findViewById(R.id.recyclerView);
      adapter = new CarAdapter(new ArrayList<Car>());
      recyclerView.setAdapter(adapter);
      carsReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
              List<Car> cars = new ArrayList<Car>();
              for (DataSnapshot carSnapshot : dataSnapshot.getChildren()) {
                  Car car = carSnapshot.getValue(Car.class);
                  cars.add(car);
              }
              ((CarAdapter) adapter).updateCars(cars);
              adapter.notifyDataSetChanged();
          } else {
              emptyMessage.setText("Não foram encontrados carros no momento, que tal criar um anuncio?");
          }
          }

          @Override
          public void onCancelled(DatabaseError error) {
              // Failed to read value
              Log.w(TAG, "Failed to read value.", error.toException());
          }
      });
  }
}