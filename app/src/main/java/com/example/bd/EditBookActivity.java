package com.example.bd;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAuthor;
    private Button buttonSave;
    private BookD bookD;
    private long bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        buttonSave = findViewById(R.id.save);

        bookD = new BookD(this);

        bookId = getIntent().getLongExtra("bookId", -1);
        if (bookId != -1) {
            Book book = bookD.getBookById(bookId);
            if (book != null) {
                editTextName.setText(book.getBook_Name());
                editTextAuthor.setText(book.getBook_Author());
            }
        }

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String author = editTextAuthor.getText().toString();
            if (!name.isEmpty() && !author.isEmpty()) {
                Book book = new Book((int) bookId, name, author);
                bookD.updateBook(book);
                finish();
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }
}