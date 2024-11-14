package com.example.giocoimpiccato;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextParolaDaIndovinare;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextParolaDaIndovinare = findViewById(R.id.editTextParolaDaIndovinare);
    }

    public void onConferma(View v){
        String parola = editTextParolaDaIndovinare.getText().toString().toUpperCase();
        if (!parola.isEmpty()) {
            Intent intent = new Intent(getBaseContext(), Activity1.class);
            intent.putExtra("PAROLA", parola);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Inserisci una parola!!", Toast.LENGTH_SHORT).show();
        }
    }

}