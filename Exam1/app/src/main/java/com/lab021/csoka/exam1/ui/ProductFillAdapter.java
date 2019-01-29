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

public class ProductFillAdapter extends RecyclerView.Adapter<ProductFillAdapter.ProductViewHolder> {

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView textReqName;
        private final TextView textProdName;
        private final TextView textStatus;

        private ProductViewHolder(View itemView) {
            super(itemView);
            textReqName = itemView.findViewById(R.id.tvBigReqName);
            textProdName = itemView.findViewById(R.id.tvBigProdName);
            textStatus = itemView.findViewById(R.id.tvBigStatus);
        }
    }

    private final LayoutInflater mInflater;
    private List<Request> mRequests = Collections.emptyList();
    private Context parentContext;

    public ProductFillAdapter(Context context) {
        this.parentContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductFillAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.view_big_item, viewGroup, false);
        return new ProductFillAdapter.ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductFillAdapter.ProductViewHolder holder, int i) {
        final Request current = mRequests.get(i);
        holder.textReqName.setText("Request: " + current.getName());
        holder.textProdName.setText("Product: " + current.getProduct());
        holder.textStatus.setText("Status: " + current.getStatus().toString());
    }

    public void setProducts(List<Request> requests) {
        mRequests = requests.subList(0, 9);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }
}
