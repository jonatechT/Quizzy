package com.example.quizzy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Initialiser tous les boutons de catégorie et attacher l'écouteur de clic
        findViewById(R.id.btn_math).setOnClickListener(this);
        findViewById(R.id.btn_physique).setOnClickListener(this);
        findViewById(R.id.btn_chimie).setOnClickListener(this);
        findViewById(R.id.btn_sciences).setOnClickListener(this);
        findViewById(R.id.btn_litterature).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String selectedCategory = "";

        // Déterminer le thème en fonction du bouton cliqué
        int id = v.getId();
        if (id == R.id.btn_math) {
            selectedCategory = "Mathématique";
        } else if (id == R.id.btn_physique) {
            selectedCategory = "Physique";
        } else if (id == R.id.btn_chimie) {
            selectedCategory = "Chimie";
        } else if (id == R.id.btn_sciences) {
            selectedCategory = "Sciences";
        } else if (id == R.id.btn_litterature) {
            selectedCategory = "Littérature";
        }

        if (!selectedCategory.isEmpty()) {
            // Lancer la MainActivity (votre QuizActivity)
            Intent intent = new Intent(this, MainActivity.class);
            // Transmettre la catégorie sélectionnée via EXTRA_CATEGORY
            intent.putExtra(MainActivity.EXTRA_CATEGORY, selectedCategory);
            startActivity(intent);
        }
    }
}