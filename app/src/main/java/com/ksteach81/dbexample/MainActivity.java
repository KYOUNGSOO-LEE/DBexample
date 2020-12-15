package com.ksteach81.dbexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_INSERT = 1000;
    private MemoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, MemoActivity.class),
                        REQUEST_CODE_INSERT);
            }
        });

        ListView listView = findViewById(R.id.memo_list);

        Cursor cursor = getMemoCursor();
        mAdapter = new MemoAdapter(this, cursor);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                Cursor cursor = (Cursor) mAdapter.getItem(position);

                String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE));
                String contents = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS));

                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("contents", contents);

                startActivityForResult(intent, REQUEST_CODE_INSERT);
            }
        });
    }

    private Cursor getMemoCursor() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .query(MemoContract.MemoEntry.TABLE_NAME,
                        null,null,null,null,null,MemoContract.MemoEntry._ID + " DESC");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK) {
            mAdapter.swapCursor(getMemoCursor());
        }
    }

    private static class MemoAdapter extends CursorAdapter {

        public MemoAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
        }
    }
}
