package com.example.bd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookCardActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewAuthor;
    private Button buttonDelete;
    private Button buttonEdit;
    private BookD bookD;
    private long bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_card);

        textViewName = findViewById(R.id.textViewName);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonEdit = findViewById(R.id.buttonEdit);

        bookD = new BookD(this);

        bookId = getIntent().getLongExtra("bookId", -1);
        if (bookId != -1) {
            Book book = bookD.getBookById(bookId);
            if (book != null) {
                textViewName.setText(book.getBook_Name());
                textViewAuthor.setText(book.getBook_Author());
            }
        }

        buttonDelete.setOnClickListener(v -> {
            if (bookId != -1) {
                bookD.deleteBookById(bookId);
                finish();
            }
        });

        buttonEdit.setOnClickListener(v -> {
            if (bookId != -1) {
                Intent intent = new Intent(BookCardActivity.this, EditBookActivity.class);
                intent.putExtra("bookId", bookId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bookId != -1) {
            Book book = bookD.getBookById(bookId);
            if (book != null) {
                textViewName.setText(book.getBook_Name());
                textViewAuthor.setText(book.getBook_Author());
            }
        }
    }
}