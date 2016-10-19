package com.example.pgutierrezd.lab13;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Almacenar valores que un content resolver va a procesar
        ContentValues editedValues = new ContentValues();
        editedValues.put(BooksPro.TITLE, "Android 6 Avanzado");
        getContentResolver().update(
                Uri.parse(
                        "content://com.example.pgutierrezd.lab13.Books/books/2"),
                editedValues,
                null,
                null);

        getContentResolver().delete(
                Uri.parse("content://com.example.pgutierrezd.lab13.Books/books/2"),
                null, null);

        getContentResolver().delete(
                Uri.parse("content://com.example.pgutierrezd.lab13.Books/books"),
                null, null);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                ContentValues values = new ContentValues();
                values.put("title", ((EditText) findViewById(R.id.txtTitle)).getText().toString());
                values.put("isbn", ((EditText) findViewById(R.id.txtISBN)).getText().toString());
                Uri uri = getContentResolver().insert(
                        Uri.parse("content://com.example.pgutierrezd.lab13.Books/books"),
                        values);
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Button btnRetrieve = (Button) findViewById(R.id.btnRetrieve);
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //---retrieve the titles---
                Uri allTitles = Uri.parse(
                        "content://com.example.pgutierrezd.lab13.Books/books");
                Cursor c = managedQuery(allTitles, null, null, null, "title desc");
                if (c.moveToFirst()) {
                    do {
                        Log.v("ContentProviders",
                                c.getString(c.getColumnIndex(
                                        BooksPro._ID)) + ", " +
                                        c.getString(c.getColumnIndex(
                                                BooksPro.TITLE)) + ", " +
                                        c.getString(c.getColumnIndex(
                                                BooksPro.ISBN)));
                    } while (c.moveToNext());
                }
            }
        });
    }


}
