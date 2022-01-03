package com.subs.clickableimagesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_DATA = "data";
    public static final String ORDER = "order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton actionButton = findViewById(R.id.floatingActionButton2);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putString(ORDER,getString(R.string.donut_order_message));
                openActivity(OrderActivity.class,mBundle);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onMenuAction(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void onMenuAction(int id){
        switch (id){
            case R.id.menu_actionOpen:
                LessonActivity.open(this,null);
                break;
            case R.id.action_order:
                displayToast(getString(R.string.action_order_message));
                break;
            case R.id.action_status:
                displayToast(getString(R.string.action_status_message));
                break;
            case R.id.action_favorites:
                displayToast(getString(R.string.action_favorites_message));
                break;
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                break;
            default:
                displayToast(getString(R.string.action_error_message));
        }
    }

    public void showDonutOrder(View view){
        displayToast(getString(R.string.donut_order_message));
    }
    public void showFroyoOrder(View view){
        displayToast(getString(R.string.froyo_order_message));
    }
    public void showIceCreamOrder(View view){ displayToast(getString(R.string.ice_cream_order_message)); }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void openActivity(Class mClass, Bundle data){
        Intent intent = new Intent(this,mClass);
        if(data != null){
            intent.putExtra(INTENT_DATA,data);
        }
        startActivity(intent);
    }
}
