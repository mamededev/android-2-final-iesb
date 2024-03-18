package dev.mamede.android2finaliesb;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.carColor.setText(car.getCor());
        holder.carYear.setText(String.valueOf(car.getAno()));
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        public ImageView carImage;
        public TextView carColor;
        public TextView carYear;
        Button deleteButton;

        public CarViewHolder(View view) {
            super(view);
            carColor = view.findViewById(R.id.carColor);
            carYear = view.findViewById(R.id.carYear);
            deleteButton = view.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    v.setEnabled(false);

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Car car = carList.get(position);
                        DatabaseReference carRef = FirebaseDatabase.getInstance().getReference("Cars").child(car.getId());
                        carRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    removeCarFromList(getAdapterPosition());
                                } else {
                                    // Falha ao deletar
                                    Log.e("CarAdapter", "Falha ao deletar carro", task.getException());
                                }
                                v.setEnabled(true);
                            }
                        });
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Car car = carList.get(position);
                        Intent intent = new Intent(v.getContext(), CreateCarsActivity.class);
                        intent.putExtra("CAR_ID", car.getId());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    private void removeCarFromList(int position) {
        if (position >= 0 && position < carList.size()) {
            carList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updateCars(List<Car> cars) {
        this.carList = cars;
        notifyDataSetChanged();
    }
}