package com.lab021.csoka.exam1.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.lab021.csoka.exam1.DetailsClerkActivity;
import com.lab021.csoka.exam1.DetailsProductActivity;
import com.lab021.csoka.exam1.MainActivity;
import com.lab021.csoka.exam1.R;
import com.lab021.csoka.exam1.model.Codes;
import com.lab021.csoka.exam1.model.Product;
import com.lab021.csoka.exam1.volley.APIController;
import com.lab021.csoka.exam1.volley.ServiceVolley;
import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class ProductClerkAdapter extends RecyclerView.Adapter<ProductClerkAdapter.ProductClerkViewHolder> {

    class ProductClerkViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textQuantity;
        private final TextView textPrice;
        private final TextView textStatus;

        private ProductClerkViewHolder(View itemView) {
           super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textQuantity = itemView.findViewById(R.id.textQuantity);
            textPrice = itemView.findViewById(R.id.textPrice);
            textStatus = itemView.findViewById(R.id.textStatus);
        }
    }

    private final LayoutInflater mInflater;
    private List<Product> mProducts = Collections.emptyList();
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
        final Product current = mProducts.get(i);
        holder.textName.setText("Name: " + current.getName());
        holder.textQuantity.setText("Quantity: " + current.getQuantity().toString());
        holder.textPrice.setText("Price: " + current.getPrice().toString());
        holder.textStatus.setText("Status: " + current.getStatus().toString());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(parentContext, DetailsClerkActivity.class);
                intent.putExtra(Codes.intent_msg_product_open, current);
                ((Activity)parentContext).startActivityForResult(intent, Codes.PRODUCT_OPEN_CODE);
            }
        });
    }

    public void setProducts(List<Product> products) {
        products.sort(Product.ProductQuantityComparator);
        mProducts = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
