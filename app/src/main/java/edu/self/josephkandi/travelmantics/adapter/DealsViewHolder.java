package edu.self.josephkandi.travelmantics.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.self.josephkandi.travelmantics.R;

public class DealsViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewPlace;
    public TextView textViewAmount;
    public TextView textViewDescription;
    public ImageView imageViewPlace;

    public DealsViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewPlace = itemView.findViewById(R.id.txtPlace);
        textViewAmount = itemView.findViewById(R.id.txtAmount);
        textViewDescription = itemView.findViewById(R.id.txtDescription);
        imageViewPlace = itemView.findViewById(R.id.imgPlace);
    }
}
