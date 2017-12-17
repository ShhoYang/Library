package com.yl.library.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yl.library.R;
import com.yl.library.adapter.MenuItemAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;


public class MenuBottomDialog {

    private Builder mBuilder;

    private MenuBottomDialog create(Builder builder) {
        mBuilder = builder;
        return this;
    }

    public MenuBottomDialog show() {
        if (mBuilder != null && mBuilder.bottomDialog != null) {
            mBuilder.bottomDialog.show();
        }
        return this;
    }

    public void dismiss() {
        if (mBuilder != null && mBuilder.bottomDialog != null) {
            mBuilder.bottomDialog.dismiss();
        }
    }

    public static class Builder {
        private Context context;
        private List menuItems;
        private BottomDialog bottomDialog;
        private ItemClickListener itemClickListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setMenuItems(List menuItems) {
            this.menuItems = menuItems;
            return this;
        }

        public Builder setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        @UiThread
        public MenuBottomDialog build() {
            if (menuItems == null) {
                new NullPointerException("menu is null");
            }
            View contentView = View.inflate(context, R.layout.menu_bottom_dialog, null);
            contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomDialog.dismiss();
                }
            });
            RecyclerView recyclerView = contentView.findViewById(R.id.rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            MenuItemAdapter adapter = new MenuItemAdapter(context, R.layout.menu_bottom_dialog_item, menuItems);
            if (itemClickListener != null) {
                adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        itemClickListener.itemClick(position);
                        bottomDialog.dismiss();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });
            }
            recyclerView.setAdapter(adapter);
            bottomDialog = new BottomDialog(context, contentView);
            return new MenuBottomDialog().create(this);
        }
    }

    public interface ItemClickListener {
        void itemClick(int position);
    }
}
