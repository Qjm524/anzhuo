package com.example.foodordering;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderIdText.setText("订单号: " + order.getOrderId());
        holder.orderDateText.setText(order.getDate());
        holder.orderItemsText.setText(order.getItemsSummary());
        holder.orderTotalText.setText(String.format(holder.itemView.getContext().getString(R.string.price), order.getTotal()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, orderDateText, orderItemsText, orderTotalText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdText = itemView.findViewById(R.id.orderIdText);
            orderDateText = itemView.findViewById(R.id.orderDateText);
            orderItemsText = itemView.findViewById(R.id.orderItemsText);
            orderTotalText = itemView.findViewById(R.id.orderTotalText);
        }
    }
}