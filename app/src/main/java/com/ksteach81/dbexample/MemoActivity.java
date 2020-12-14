package com.ksteach81.dbexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class MemoActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mContentsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        mTitleEditText = findViewById(R.id.title_edit);
        mContentsEditText = findViewById(R.id.contents_edit);
    }

    @Override
    public void onBackPressed() {
        String title = mTitleEditText.getText().toString();
        String contents = mContentsEditText.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS, contents);

        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();
        long newRowID = db.insert(MemoContract.MemoEntry.TABLE_NAME,
                null,
                contentValues);

        if (newRowID == -1) {
            Toast.makeText(this, "저장에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        }

        super.onBackPressed();
    }
}