package com.example.prueba_firebase;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etAge;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los componentes
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        Button btnSave = findViewById(R.id.btnSave);

        // Inicializa Firestore
        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();

                if (validateInputs(name, age)) {
                    saveToFirestore(name, age);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String name, String age) {
        // Verificar si los campos están vacíos
        if (name.isEmpty()) {
            etName.setError("Name is required");
            return false;
        }
        if (age.isEmpty()) {
            etAge.setError("Age is required");
            return false;
        }
        // Verificar si la edad es un número válido
        try {
            Integer.parseInt(age);
        } catch (NumberFormatException e) {
            etAge.setError("Please enter a valid number");
            return false;
        }
        return true;
    }

    private void saveToFirestore(String name, String age) {
        // Crea un HashMap con los datos
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("age", Integer.parseInt(age));

        // Agrega los datos a Firestore
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
