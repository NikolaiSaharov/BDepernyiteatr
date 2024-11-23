package com.example.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookD {

    private DatabaseHelper databaseHelper;

    public BookD(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void addBook(Book book) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, book.getBook_Name());
        values.put(DatabaseHelper.COLUMN_AUTHOR, book.getBook_Author());
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String[] columns = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_AUTHOR
        };

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR))
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }

    public int deleteBookById(long bookId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
        return result;
    }

    public int updateBook(Book book) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, book.getBook_Name());
        values.put(DatabaseHelper.COLUMN_AUTHOR, book.getBook_Author());
        int result = db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(book.getID_Book())});
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public Book getBookById(long bookId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_AUTHOR
        };
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Book book = null;
        if (cursor.moveToFirst()) {
            book = new Book(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR))
            );
        }

        cursor.close();
        db.close();
        return book;
    }
}