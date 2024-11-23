package com.example.bd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.List;

public class BookSelectionDialog extends DialogFragment {

    public interface BookSelectionListener {
        void onBookSelected(long bookId);
    }

    private List<Book> bookList;
    private BookSelectionListener listener;

    public BookSelectionDialog(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (BookSelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BookSelectionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите книгу");

        String[] bookNames = new String[bookList.size()];
        for (int i = 0; i < bookList.size(); i++) {
            bookNames[i] = bookList.get(i).getBook_Name();
        }

        builder.setItems(bookNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onBookSelected(bookList.get(which).getID_Book());
            }
        });

        return builder.create();
    }
}