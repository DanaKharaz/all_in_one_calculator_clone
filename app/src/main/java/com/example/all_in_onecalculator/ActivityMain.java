package com.example.all_in_onecalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ActivityMain extends AppCompatActivity {

    DatabaseManager mainDB;
    ArrayList<String> db_operations = new ArrayList<>();
    ArrayList<String> db_sections = new ArrayList<>();
    ArrayList<Integer> db_favorites = new ArrayList<>();
    ArrayList<Button> btnOperations = new ArrayList<>();
    HashMap<String, Pair<Class<?>, Drawable>> activityDir = new HashMap<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OPERATIONS 1: retrieve data
        mainDB = new DatabaseManager(this);
        Cursor cursor = mainDB.readAllData();
        if (!(Objects.isNull(cursor))) {
            while (cursor.moveToNext()) {
                db_operations.add(cursor.getString(1));
                db_sections.add(cursor.getString(2));
                db_favorites.add(cursor.getInt(3));
                btnOperations.add(new Button(this, null, com.google.android.material.R.style.Widget_Material3_Button));
            }
        }
        else {
            Toast.makeText(this, "nothing :(", Toast.LENGTH_SHORT).show();
        }


        // OPERATIONS 2: all operations UI
        // define layouts, activities
        HashMap<String, Pair<LinearLayout, Integer>> hmSections = new HashMap<>();
        // !!! change to icon ids and classes to the right ones
        hmSections.put("Algebra",
                new Pair<>((LinearLayout) findViewById(R.id.linLayAlgebra), R.color.alg_prim));
        hmSections.put("Geometry",
                new Pair<>((LinearLayout) findViewById(R.id.linLayGeometry), R.color.geom_prim));
        hmSections.put("Unit converters",
                new Pair<>((LinearLayout) findViewById(R.id.linLayUnits), R.color.unit_prim));
        hmSections.put("Finance",
                new Pair<>((LinearLayout) findViewById(R.id.linLayFinance), R.color.fin_prim));
        hmSections.put("Health",
                new Pair<>((LinearLayout) findViewById(R.id.linLayHealth), R.color.health_prim));
        hmSections.put("Miscellaneous",
                new Pair<>((LinearLayout) findViewById(R.id.linLayMisc), R.color.misc_prim));
        initActivityDir();

        // populate layouts
        for (int i = 0; i < btnOperations.size(); i++) {
            // depends on section
            Objects.requireNonNull(hmSections.get(db_sections.get(i))).first.addView(btnOperations.get(i));
            btnOperations.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().
                    getDrawable(R.drawable.ic_android_black_24dp, getTheme()), null, null, null); // !!! change icons
            // with hmSections
            //btnOperations.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().
            //        getDrawable(hmSections.get(db_sections.get(i))).second, getTheme()), null, null, null);
            // with activityDir
            //btnOperations.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().
            //        activityDir.get(db_sections.get(index) + "-" + db_operations.get(index))).second, null, null, null);

            // on click
            btnOperations.get(i).setOnClickListener(v -> {
                int index = btnOperations.indexOf(v);
                Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Objects.requireNonNull(
                        activityDir.get(db_sections.get(index) + "-" + db_operations.get(index))).first);
                startActivity(intent); // go to needed activity
            });

            // general
            btnOperations.get(i).setText(styleBtnText(db_operations.get(i), db_sections.get(i)));
            btnOperations.get(i).setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_LabelLarge);
            btnOperations.get(i).setHeight(144);
            btnOperations.get(i).setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            btnOperations.get(i).setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            btnOperations.get(i).setPadding(48, 6, 24, 6); // left - from button edge to icon
            btnOperations.get(i).setCompoundDrawablePadding(24); // distance from icon edge to text
        }
    }

    public Spanned styleBtnText(String method, String type) {
        String html = "<big><font color='#000000'>" +
                method +
                "</font></big> <br/> <small><font color='#616161'>" +
                type +
                "</font></small>";
        return((Html.fromHtml(html, 0))); // !!! flags
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void initActivityDir() {
        activityDir.put("Algebra-Average", new Pair<>(ActivityAlgebraAverage.class, getDrawable(R.drawable.ic_alg_bg))); // !!! change to correct ones
        activityDir.put("Algebra-Combinations", new Pair<>(ActivityAlgebraCombinations.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-Equations", new Pair<>(ActivityAlgebraEquations.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-Fractions", new Pair<>(ActivityAlgebraFractions.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-GCF / LCM", new Pair<>(ActivityAlgebraGCFandLCM.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-Number Generator", new Pair<>(ActivityAlgebraNumberGenerator.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-Percentage", new Pair<>(ActivityAlgebraPercentage.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-Prime Checker", new Pair<>(ActivityAlgebraPrimeChecker.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-Proportion", new Pair<>(ActivityAlgebraProportion.class, getDrawable(R.drawable.ic_alg_bg)));
        activityDir.put("Algebra-Ratio", new Pair<>(ActivityAlgebraRatio.class, getDrawable(R.drawable.ic_alg_bg)));
        // .....
    }
}