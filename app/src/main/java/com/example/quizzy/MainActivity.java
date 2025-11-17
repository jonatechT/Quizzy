package com.example.quizzy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale; // Import nécessaire pour corriger les warnings de Locale
import java.util.Objects; // Import nécessaire pour corriger le warning 'if statement'

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Clé pour transférer la catégorie via Intent
    public static final String EXTRA_CATEGORY = "com.example.quizzy.CATEGORY";

    // Déclaration des éléments de l'UI
    private TextView questionTextView;
    private TextView scoreTextView;
    private TextView timerTextView;
    private Button[] choiceButtons;
    private Button btnContinue;
    private Button btnQuit;

    // Déclaration des variables de logique
    private Question[] questions;
    private int score = 0;
    private int currentQuestionIndex = 0;

    // Chronomètre et temps final
    private CountDownTimer countDownTimer;
    private long timeElapsed = 0;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  3. Initialisation des composants UI
        questionTextView = findViewById(R.id.question_text);
        scoreTextView = findViewById(R.id.score_text);
        timerTextView = findViewById(R.id.timer_text);
        btnContinue = findViewById(R.id.btn_continue);
        btnQuit = findViewById(R.id.btn_quit);

        choiceButtons = new Button[]{
                findViewById(R.id.choice_1),
                findViewById(R.id.choice_2),
                findViewById(R.id.choice_3),
                findViewById(R.id.choice_4)
        };

        // Attacher l'écouteur de clic (ceci) à tous les boutons
        for (Button button : choiceButtons) {
            button.setOnClickListener(this);
        }

        btnContinue.setOnClickListener(this);
        btnQuit.setOnClickListener(this);

        //  Initialisation du Modèle (Chargement par thème)
        // Utilisation de Objects.requireNonNullElse pour corriger le warning 'if statement'
        String selectedCategory = Objects.requireNonNullElse(getIntent().getStringExtra(EXTRA_CATEGORY), "Mathématique");

        questions = getQuestionsForCategory(selectedCategory);

        // Lancement du Chrono et du Quiz
        startTimer(); // Démarrer le chronomètre
        displayCurrentQuestion();
    }

    // Démarrer le chronomètre
    private void startTimer() {
        startTime = System.currentTimeMillis();

        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeElapsed = System.currentTimeMillis() - startTime;
                long seconds = timeElapsed / 1000;
                long minutes = seconds / 60;
                seconds = seconds % 60;

                timerTextView.setText(String.format(Locale.getDefault(), "Temps : %02d:%02d", minutes, seconds));

                // Mettre à jour le score en haut
                scoreTextView.setText(getString(R.string.score_format, score, questions.length));
            }

            @Override
            public void onFinish() {}
        }.start();
    }

    // Arrêter le chronomètre
    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // Méthode pour créer et classer TOUTES les questions par thème (50 questions)
    private Question[] getQuestionsForCategory(String category) {
        Question[] allQuestions = new Question[]{
                // -------------------------------------------------------------------
                // MATHÉMATIQUES (10 questions)
                // -------------------------------------------------------------------
                new Question("Combien font 12 x 3 ?", new String[]{"36", "24", "48", "30"}, 0, "Mathématique"),
                new Question("Quel est le chiffre après le 9 ?", new String[]{"10", "11", "90", "0"}, 0, "Mathématique"),
                new Question("Quelle est la racine carrée de 144 ?", new String[]{"10", "12", "14", "16"}, 1, "Mathématique"),
                new Question("Quelle est la valeur de Pi (approx.) ?", new String[]{"3.14", "3.04", "3.41", "3.44"}, 0, "Mathématique"),
                new Question("Un triangle avec deux côtés égaux est...", new String[]{"Scalène", "Équilatéral", "Isocèle", "Rectangle"}, 2, "Mathématique"),
                new Question("Combien de degrés y a-t-il dans un cercle ?", new String[]{"90", "180", "360", "45"}, 2, "Mathématique"),
                new Question("Quelle est la somme des angles intérieurs d'un triangle ?", new String[]{"90°", "180°", "270°", "360°"}, 1, "Mathématique"),
                new Question("Que représente le nombre 'i' ?", new String[]{"Imaginaire", "Infini", "Intégrale", "Indéfini"}, 0, "Mathématique"),
                new Question("Si x + 5 = 12, que vaut x ?", new String[]{"6", "7", "8", "9"}, 1, "Mathématique"),
                new Question("Le résultat de 5! (Factorielle de 5) est :", new String[]{"25", "50", "100", "120"}, 3, "Mathématique"),


                // -------------------------------------------------------------------
                // PHYSIQUE (10 questions)
                // -------------------------------------------------------------------
                new Question("Quelle est l'unité de la force en SI ?", new String[]{"Joule", "Watt", "Newton", "Pascal"}, 2, "Physique"),
                new Question("Quelle est la vitesse de la lumière (en approximation) ?", new String[]{"300 km/s", "300 000 km/s", "30 000 km/s", "300 000 m/s"}, 1, "Physique"),
                new Question("Quelle loi régit le mouvement des planètes ?", new String[]{"Lois de Newton", "Lois de Kepler", "Lois de Ohm", "Relativité"}, 1, "Physique"),
                new Question("L'énergie cinétique est l'énergie due au...", new String[]{"Potentiel", "Mouvement", "Chaleur", "Volume"}, 1, "Physique"),
                new Question("Quelle est l'unité de l'énergie en SI ?", new String[]{"Watt", "Volt", "Ampère", "Joule"}, 3, "Physique"),
                new Question("Que mesure un voltmètre ?", new String[]{"Courant", "Résistance", "Tension", "Puissance"}, 2, "Physique"),
                new Question("L'effet Doppler concerne les ondes de quel type ?", new String[]{"Lumière", "Son", "Chaleur", "Toutes"}, 3, "Physique"),
                new Question("Quelle est la formule de la force (selon Newton) ?", new String[]{"E=mc²", "F=ma", "P=VI", "V=IR"}, 1, "Physique"),
                new Question("Qu'est-ce qui caractérise un corps noir ?", new String[]{"Il absorbe toute la lumière", "Il réfléchit toute la lumière", "Il est invisible", "Il produit de l'énergie"}, 0, "Physique"),
                new Question("Quelle est l'accélération due à la gravité sur Terre (approx.) ?", new String[]{"1 m/s²", "9.8 m/s²", "12 m/s²", "15 m/s²"}, 1, "Physique"),


                // -------------------------------------------------------------------
                // CHIMIE (10 questions)
                // -------------------------------------------------------------------
                new Question("Quelle formule chimique représente l'eau ?", new String[]{"H2O", "CO2", "O2", "NaCl"}, 0, "Chimie"),
                new Question("Quel est le symbole du Fer ?", new String[]{"Fe", "Au", "Ag", "Fr"}, 0, "Chimie"),
                new Question("Quel est le gaz le plus abondant dans l'atmosphère terrestre ?", new String[]{"Oxygène", "Azote", "Argon", "Dioxyde de carbone"}, 1, "Chimie"),
                new Question("Le pH neutre est égal à :", new String[]{"0", "7", "14", "10"}, 1, "Chimie"),
                new Question("Qu'est-ce qu'un ion positif ?", new String[]{"Anion", "Isotope", "Cation", "Molécule"}, 2, "Chimie"),
                new Question("Combien de protons l'atome d'hydrogène a-t-il ?", new String[]{"0", "1", "2", "3"}, 1, "Chimie"),
                new Question("Quelle est l'abréviation de la Table Périodique des Éléments ?", new String[]{"TPE", "CPE", "MPE", "PTE"}, 3, "Chimie"),
                new Question("Lesquels sont des gaz nobles ?", new String[]{"Oxygène, Azote", "Hélium, Néon", "Fluor, Chlore", "Hydrogène, Carbone"}, 1, "Chimie"),
                new Question("Quel élément est essentiel à la vie et se trouve dans tous les organismes ?", new String[]{"Or", "Silicium", "Carbone", "Soufre"}, 2, "Chimie"),
                new Question("Un acide a un pH inférieur à :", new String[]{"14", "7", "0", "5"}, 1, "Chimie"),


                // -------------------------------------------------------------------
                // SCIENCES (Biologie/Terre - 10 questions)
                // -------------------------------------------------------------------
                new Question("Quel organe pompe le sang ?", new String[]{"Le poumon", "Le foie", "Le cerveau", "Le coeur"}, 3, "Sciences"),
                new Question("Quel est le plus grand continent ?", new String[]{"Afrique", "Asie", "Europe", "Amérique"}, 1, "Sciences"),
                new Question("Quel est le processus par lequel les plantes fabriquent leur nourriture ?", new String[]{"Respiration", "Transpiration", "Photosynthèse", "Germination"}, 2, "Sciences"),
                new Question("Combien y a-t-il d'os dans le corps humain adulte ?", new String[]{"206", "180", "250", "300"}, 0, "Sciences"),
                new Question("Quelle est la planète la plus proche du Soleil ?", new String[]{"Vénus", "Mars", "Mercure", "Terre"}, 2, "Sciences"),
                new Question("Le cycle de l'eau est appelé le cycle...", new String[]{"Géologique", "Hydrologique", "Atmosphérique", "Biologique"}, 1, "Sciences"),
                new Question("Quelle est la principale fonction des globules rouges ?", new String[]{"Combattre les infections", "Transporter l'oxygène", "Coaguler le sang", "Produire des anticorps"}, 1, "Sciences"),
                new Question("Quel est le plus grand océan du monde ?", new String[]{"Atlantique", "Indien", "Arctique", "Pacifique"}, 3, "Sciences"),
                new Question("La maladie causée par une carence en vitamine C est :", new String[]{"Le béribéri", "Le scorbut", "Le rachitisme", "La pellagre"}, 1, "Sciences"),
                new Question("La partie de l'œil sensible à la lumière est :", new String[]{"La cornée", "Le cristallin", "L'iris", "La rétine"}, 3, "Sciences"),


                // -------------------------------------------------------------------
                // LITTÉRATURE (10 questions)
                // -------------------------------------------------------------------
                new Question("Qui a écrit 'Le Père Goriot' ?", new String[]{"Victor Hugo", "Émile Zola", "Honoré de Balzac", "Albert Camus"}, 2, "Littérature"),
                new Question("Quel est le genre de 'L'Étranger' de Camus ?", new String[]{"Poésie", "Théâtre", "Roman", "Essai"}, 2, "Littérature"),
                new Question("Quel auteur a écrit 'Les Misérables' ?", new String[]{"Gustave Flaubert", "Alexandre Dumas", "Victor Hugo", "Jules Verne"}, 2, "Littérature"),
                new Question("Quelle figure de style utilise 'un silence éloquent' ?", new String[]{"Métaphore", "Euphémisme", "Oxymore", "Litote"}, 2, "Littérature"),
                new Question("Qui est l'auteur du roman 'Candide' ?", new String[]{"Rousseau", "Molière", "Voltaire", "Diderot"}, 2, "Littérature"),
                new Question("De quel pays est originaire l'écrivain Gabriel García Márquez ?", new String[]{"Mexique", "Argentine", "Colombie", "Espagne"}, 2, "Littérature"),
                new Question("Quel mouvement littéraire célèbre l'émotion et l'imagination ?", new String[]{"Classicisme", "Réalisme", "Romantisme", "Surréalisme"}, 2, "Littérature"),
                new Question("Qu'est-ce qu'un sonnet ?", new String[]{"Un poème de 10 vers", "Une courte nouvelle", "Un poème de 14 vers", "Une chanson populaire"}, 2, "Littérature"),
                new Question("Qui est l'héroïne tragique de la pièce de Shakespeare 'Roméo et Juliette' ?", new String[]{"Ophélie", "Cléopâtre", "Juliette", "Desdémone"}, 2, "Littérature"),
                new Question("Quel type de texte vise à convaincre le lecteur ?", new String[]{"Narratif", "Descriptif", "Argumentatif", "Injonctif"}, 2, "Littérature")
        };

        // Filtrer les questions en fonction du thème sélectionné
        List<Question> filteredList = new ArrayList<>();
        for (Question q : allQuestions) {
            if (q.getCategory().equals(category)) {
                filteredList.add(q);
            }
        }
        return filteredList.toArray(new Question[0]);
    }

    // Méthode pour afficher la question et les options à l'écran
    private void displayCurrentQuestion() {
        btnContinue.setVisibility(View.GONE);
        btnQuit.setVisibility(View.GONE);

        if (currentQuestionIndex < questions.length) {
            Question currentQuestion = questions[currentQuestionIndex];

            // Assurer que le score et le temps sont visibles pendant le quiz
            scoreTextView.setVisibility(View.VISIBLE);
            timerTextView.setVisibility(View.VISIBLE);

            // Affichage du texte de la question
            questionTextView.setText(currentQuestion.getQuestionText());

            // Affichage des options sur les boutons
            String[] choices = currentQuestion.getChoices();
            for (int i = 0; i < choiceButtons.length; i++) {
                choiceButtons[i].setText(choices[i]);
                choiceButtons[i].setVisibility(View.VISIBLE);
                choiceButtons[i].setEnabled(true);
            }
        } else {
            // Le quiz est terminé
            showFinalScore();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // 1. Logique des boutons de fin de quiz
        if (id == R.id.btn_quit) {
            finishAffinity(); // Quitte l'application
            return;
        } else if (id == R.id.btn_continue) {
            // Retourne à l'écran de sélection de catégorie
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        // 2. Logique des boutons de réponse
        int selectedChoiceIndex = -1;
        for (int i = 0; i < choiceButtons.length; i++) {
            if (id == choiceButtons[i].getId()) {
                selectedChoiceIndex = i;
                break;
            }
        }

        if (selectedChoiceIndex != -1) {
            checkAnswer(selectedChoiceIndex);
        }
    }

    // Méthode pour vérifier la réponse choisie
    private void checkAnswer(int selectedChoiceIndex) {
        // Désactiver tous les boutons de choix
        for (Button button : choiceButtons) {
            button.setEnabled(false);
        }

        Question currentQuestion = questions[currentQuestionIndex];

        if (selectedChoiceIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
            Toast.makeText(this, getString(R.string.toast_correct), Toast.LENGTH_SHORT).show();
        } else {
            // Afficher la bonne réponse
            String message = getString(R.string.toast_wrong) + " : " +
                    currentQuestion.getChoices()[currentQuestion.getCorrectAnswerIndex()];
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        currentQuestionIndex++;
        // Utilisation de Lambda pour corriger le warning "Anonymous new Runnable"
        new android.os.Handler().postDelayed(this::displayCurrentQuestion, 1000); // Délai de 1 seconde
    }

    // MÉTHODE CORRIGÉE : Afficher le score final et le temps (sans chevauchement)
    private void showFinalScore() {
        stopTimer(); // Arrêter le chronomètre

        timeElapsed = System.currentTimeMillis() - startTime;
        long seconds = timeElapsed / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;


        // L'affichage complet sera dans la TextView de la question.
        scoreTextView.setVisibility(View.GONE);

        // Mise à jour de l'affichage du temps pour le fixer (affichage du temps total en haut)
        timerTextView.setText(String.format(Locale.getDefault(), "Temps total : %02d:%02d", minutes, seconds));

        // Le message final complet affiché dans questionTextView (inclut le score et le temps)
        String finalMessage = String.format(
                Locale.getDefault(),
                "Quiz terminé !\nVotre score : %d / %d\nTemps mis : %02d:%02d",
                score, questions.length, minutes, seconds
        );
        questionTextView.setText(finalMessage);

        // Masquer les boutons de choix
        for (Button button : choiceButtons) {
            button.setVisibility(View.GONE);
        }

        // Afficher les boutons de fin de quiz
        btnContinue.setVisibility(View.VISIBLE);
        btnQuit.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}