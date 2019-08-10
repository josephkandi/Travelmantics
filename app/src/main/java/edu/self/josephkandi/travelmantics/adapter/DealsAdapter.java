package edu.self.josephkandi.travelmantics.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.self.josephkandi.travelmantics.R;
import edu.self.josephkandi.travelmantics.models.Deal;

public class DealsAdapter extends RecyclerView.Adapter<DealsViewHolder> {
    ArrayList<Deal> dealArrayList = new ArrayList<>();

    public void addDeal(Deal deal){
        if(dealArrayList.contains(deal)){
            dealArrayList.set(dealArrayList.indexOf(deal), deal);
        } else {
            dealArrayList.add(deal);
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public ArrayList<Deal> getDeals(){
        return  dealArrayList;
    }

    @NonNull
    @Override
    public DealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal,parent,false);
        return new DealsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsViewHolder holder, int position) {
        Deal deal = dealArrayList.get(position);
        holder.textViewPlace.setText(deal.getPlace());
        holder.textViewDescription.setText(deal.getDescription());
        holder.textViewAmount.setText(deal.getAmount());
        if(!TextUtils.isEmpty(deal.getPlaceImageUrl())){
            Picasso.get().load(deal.getPlaceImageUrl()).into(holder.imageViewPlace);
        }

    }

    @Override
    public int getItemCount() {
        return  dealArrayList.size() == 0 ? 0 : dealArrayList.size();
    }
}




