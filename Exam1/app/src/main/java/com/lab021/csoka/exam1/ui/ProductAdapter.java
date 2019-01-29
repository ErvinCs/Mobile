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
import com.lab021.csoka.exam1.DetailsFulfillActivity;
import com.lab021.csoka.exam1.R;
import com.lab021.csoka.exam1.model.Codes;
import com.lab021.csoka.exam1.model.Request;

import java.util.Collections;
import java.util.List;

//Supplier
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textQuantity;
        private final TextView textProduct;

        private ProductViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textQuantity = itemView.findViewById(R.id.textQuantity);
            textProduct = itemView.findViewById(R.id.textProduct);
        }
    }

    private final LayoutInflater mInflater;
    private List<Request> mRequests = Collections.emptyList();
    private Context parentContext;

    public ProductAdapter(Context context) {
        this.parentContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.view_item, viewGroup, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int i) {
        final Request current = mRequests.get(i);
        holder.textName.setText("Name: " + current.getName());
        holder.textQuantity.setText("Quantity: " + current.getQuantity().toString());
        holder.textProduct.setText("Price: " + current.getProduct());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(parentContext, DetailsFulfillActivity.class);
                intent.putExtra(Codes.intent_msg_product_details, current);
                ((Activity)parentContext).startActivityForResult(intent, Codes.PRODUCT_DETAILS_CODE);
            }
        });
    }

    public void setProducts(List<Request> requests) {
        mRequests = requests;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }
}
