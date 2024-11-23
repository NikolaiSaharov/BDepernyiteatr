package com.example.bd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookSelectionDialog.BookSelectionListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookD bookD;
    private boolean isDeleteAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookD = new BookD(this);
        List<Book> bookList = bookD.getAllBooks();
        bookAdapter = new BookAdapter(bookList, this); // Передаем контекст в конструктор BookAdapter
        recyclerView.setAdapter(bookAdapter);

        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        fabAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        Button btnDeleteBook = findViewById(R.id.btn_delete_book);
        btnDeleteBook.setOnClickListener(v -> {
            isDeleteAction = true;
            showBookSelectionDialog();
        });

        Button btnEditBook = findViewById(R.id.btn_edit_book);
        btnEditBook.setOnClickListener(v -> {
            isDeleteAction = false;
            showBookSelectionDialog();
        });
    }

    private void showBookSelectionDialog() {
        List<Book> bookList = bookD.getAllBooks();
        if (bookList.isEmpty()) {
            // Показываем сообщение, если список книг пуст
            Toast.makeText(this, "Список книг пуст", Toast.LENGTH_SHORT).show();
            return;
        }
        BookSelectionDialog dialog = new BookSelectionDialog(bookList);
        dialog.show(getSupportFragmentManager(), "bookSelectionDialog");
    }

    @Override
    public void onBookSelected(long bookId) {
        if (bookId != -1) {
            if (isDeleteAction) {
                bookD.deleteBookById(bookId);
                List<Book> bookList = bookD.getAllBooks();
                bookAdapter.setBookList(bookList);
                bookAdapter.notifyDataSetChanged();
            } else {
                Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
                intent.putExtra("bookId", bookId);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Book> bookList = bookD.getAllBooks();
        bookAdapter.setBookList(bookList);
        bookAdapter.notifyDataSetChanged();
    }
}