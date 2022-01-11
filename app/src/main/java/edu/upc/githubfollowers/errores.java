package edu.upc.githubfollowers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class errores extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errores);
        String user=getIntent().getStringExtra("user");
        TextView text= findViewById(R.id.errortxt);
        text.setText(user+" no encontrado");
    }
    public void onVolverClick(View view){
        Intent returnIntent = new Intent(errores.this, MainActivity.class);
        startActivity(returnIntent);


    }
}
