package com.example.bd;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAuthor;
    private BookD bookD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        Button buttonAdd = findViewById(R.id.add);

        bookD = new BookD(this);

        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String author = editTextAuthor.getText().toString();
            Book book = new Book(0, name, author);
            bookD.addBook(book);
            finish();
        });
    }
}
