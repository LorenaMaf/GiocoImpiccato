package com.example.giocoimpiccato;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity1 extends AppCompatActivity {

    private TextView parolaNascostaView, tentativiView;
    private EditText letteraInput;
    private String parolaDaIndovinare;
    private StringBuilder parolaNascosta;
    private int tentativi = 0;
    private boolean parolaCompletata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
        
        Intent intent = getIntent();
        parolaDaIndovinare = intent.getStringExtra("PAROLA");

        parolaNascostaView = findViewById(R.id.textViewParolaNascosta);
        tentativiView = findViewById(R.id.textViewTentativi);
        letteraInput = findViewById(R.id.editTextLettera);

        if (savedInstanceState != null) {
            tentativi = savedInstanceState.getInt("TENTATIVI");
            parolaNascosta= new StringBuilder(savedInstanceState.getString("INDOVINA"));
            tentativiView.setText("Tentativi: " + tentativi);
            parolaNascostaView.setText(parolaNascosta);
        }else {
            parolaNascosta = new StringBuilder();
            for (int i = 0; i < parolaDaIndovinare.length(); i++) {
                parolaNascosta.append("_");
            }

            parolaNascostaView.setText(parolaNascosta.toString());
        }
    }


    public void onProva(View v) {
        if (!parolaCompletata) {

            String lettera = letteraInput.getText().toString().toUpperCase();
            if (lettera.length() == 1) {
                boolean trovata = false;
                for (int i = 0; i < parolaDaIndovinare.length(); i++) {
                    if (parolaDaIndovinare.charAt(i) == lettera.charAt(0)) {
                        parolaNascosta.setCharAt(i, lettera.charAt(0));
                        trovata = true;
                    }
                }
                if (trovata) tentativi++;
                tentativiView.setText("Tentativi: " + tentativi);
                parolaNascostaView.setText(parolaNascosta);
                letteraInput.setText("");

                if (parolaNascosta.toString().equals(parolaDaIndovinare)) {
                    parolaCompletata = true;
                    Toast.makeText(this, "Hai indovinato la parola!", Toast.LENGTH_SHORT).show();

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    };

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setMessage("Hai vinto.")
                            .setPositiveButton("ok", dialogClickListener)
                            .show();

                    return;
                }
                }
            } else {
                Toast.makeText(this, "Inserisci una sola lettera", Toast.LENGTH_SHORT).show();
            }
        }



    public void onReset(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(getApplicationContext(), "Azione annullata", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Stai per resettare il gioco. Sei sicuro?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }


    public void onAiuto(View v) {
            for (int i = 0; i < parolaDaIndovinare.length(); i++) {
                if (parolaNascosta.charAt(i) == '_') {
                    parolaNascosta.setCharAt(i, parolaDaIndovinare.charAt(i));
                    tentativi += 5;
                    tentativiView.setText("Tentativi: " + tentativi);
                    parolaNascostaView.setText(parolaNascosta);
                    break;
                }
            }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("TENTATIVI", tentativi);
        savedInstanceState.putString("INDOVINA", String.valueOf(parolaNascosta));
    }
}
