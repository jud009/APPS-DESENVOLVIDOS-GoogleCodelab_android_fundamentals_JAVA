package com.subs.menuandpickersapp;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private static final String ACTION_KEY = "actionKey";
    private TextView textNews;
    private ActionView action;
    private static boolean wasRestore;
    public static boolean isOpen;

    private static ActionView saveAction;

    public OrderFragment(ActionView action) {
        this.action = action;
    }

    public void setAction(ActionView action) {
        this.action = action;
    }

    public interface ActionView {
        List<View> getView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(saveAction != null){
            saveAction = action;
            wasRestore = true;
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(wasRestore){
            setAction(saveAction);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(View values: action.getView()){
            values.setVisibility(View.INVISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textNews = view.findViewById(R.id.text_news);
        registerForContextMenu(textNews);
        isOpen = true;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        onActionItemSelected(item.getItemId());
        return super.onContextItemSelected(item);
    }

    private void onActionItemSelected(int id) {
        switch (id) {
            case R.id.action_edit:
                displayToast("Edit choice clicked.");
                break;
            case R.id.action_share:
                displayToast("Share choice clicked.");
                break;
            case R.id.action_delete:
                displayToast("Delete choice clicked.");
                break;
            default:
                displayToast("No options found");
        }
    }

    private void displayToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(MainActivity.TAG, "onDestroy: ");
        if (action != null) {
            for (View value : action.getView()) {
                value.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(MainActivity.TAG, "onDetach: ");
        isOpen = false;
    }
}
