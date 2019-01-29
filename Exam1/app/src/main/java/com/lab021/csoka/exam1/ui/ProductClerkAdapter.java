package com.lab021.csoka.exam1.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lab021.csoka.exam1.DetailsEmployee;
import com.lab021.csoka.exam1.R;
import com.lab021.csoka.exam1.model.Codes;
import com.lab021.csoka.exam1.model.Request;

import java.util.Collections;
import java.util.List;

public class ProductClerkAdapter extends RecyclerView.Adapter<ProductClerkAdapter.ProductClerkViewHolder> {

    class ProductClerkViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textQuantity;

        private ProductClerkViewHolder(View itemView) {
           super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textQuantity = itemView.findViewById(R.id.textQuantity);
        }
    }

    private final LayoutInflater mInflater;
    private List<Request> mRequests = Collections.emptyList();
    private Context parentContext;

    public ProductClerkAdapter(Context context) {
        this.parentContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductClerkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.clerk_item_view, viewGroup, false);
        return new ProductClerkAdapter.ProductClerkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductClerkAdapter.ProductClerkViewHolder holder, int i) {
        final Request current = mRequests.get(i);
        holder.textName.setText("Name: " + current.getName());
        holder.textQuantity.setText("Quantity: " + current.getQuantity().toString());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(parentContext, DetailsEmployee.class);
                intent.putExtra(Codes.intent_msg_product_open, current);
                ((Activity)parentContext).startActivityForResult(intent, Codes.PRODUCT_OPEN_CODE);
            }
        });
    }

    public void setProducts(List<Request> requests) {
        requests.sort(Request.ProductQuantityComparator);
        mRequests = requests;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }
}
