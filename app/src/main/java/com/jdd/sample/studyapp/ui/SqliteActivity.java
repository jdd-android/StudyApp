package com.jdd.sample.studyapp.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.persistence.sql.DbContants;
import com.jdd.sample.studyapp.persistence.sql.PersonDbHelper;
import com.jdd.sample.studyapp.utils.CommonUtils;

public class SqliteActivity extends AppCompatActivity {

    private TextView mQueryResultTv;

    private EditText mNameEt;
    private EditText mAgeEt;

    private PersonDbHelper mDbHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("SqliteActivity");

        mQueryResultTv = findViewById(R.id.tv_query_result);
        mNameEt = findViewById(R.id.et_key);
        mAgeEt = findViewById(R.id.et_value);

        findViewById(R.id.btn_insert).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_delete).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_update).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_query).setOnClickListener(mOnClickListener);

        mDbHelper = new PersonDbHelper(this);
        mDb = mDbHelper.getWritableDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁，关闭数据库
        mDb.close();
    }

    private View.OnClickListener mOnClickListener = (v) -> {
        int id = v.getId();
        switch (id) {
            case R.id.btn_insert:
                ContentValues values = new ContentValues();
                values.put(DbContants.TablePerson.COLUMN_NAME, mNameEt.getText().toString());
                values.put(DbContants.TablePerson.COLUMN_AGE, CommonUtils.string2int(mAgeEt.getText().toString()));
                mDb.insert(DbContants.TablePerson.TABLE_NAME, "", values);
                break;

            case R.id.btn_delete:
                mDb.delete(DbContants.TablePerson.TABLE_NAME,
                        DbContants.TablePerson.COLUMN_NAME + "=?",
                        new String[]{mNameEt.getText().toString()});
                break;

            case R.id.btn_update:
                values = new ContentValues();
                values.put(DbContants.TablePerson.COLUMN_AGE, CommonUtils.string2int(mAgeEt.getText().toString()));
                mDb.update(DbContants.TablePerson.TABLE_NAME, values,
                        DbContants.TablePerson.COLUMN_NAME + "=?",
                        new String[]{mNameEt.getText().toString()});
                break;

            case R.id.btn_query:
                Cursor cursor = mDb.query(DbContants.TablePerson.TABLE_NAME,
                        null,
                        DbContants.TablePerson.COLUMN_NAME + "=?",
                        new String[]{mNameEt.getText().toString()},
                        null,
                        null,
                        null);
                if (cursor == null) {
                    Toast.makeText(this, "cursor is null", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder sb = new StringBuilder();

                while (cursor.moveToNext()) {
                    int personId = cursor.getInt(cursor.getColumnIndex(DbContants.TablePerson.COLUMN_ID));
                    int personAge = cursor.getInt(cursor.getColumnIndex(DbContants.TablePerson.COLUMN_AGE));
                    String personName = cursor.getString(cursor.getColumnIndex(DbContants.TablePerson.COLUMN_NAME));

                    sb.append("personId=").append(personId).append("  personName=").append(personName).append("  personAge=").append(personAge).append("\n");
                }
                cursor.close();

                mQueryResultTv.setText(sb.toString());
                break;

            default:
                break;
        }
    };
}
