/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package userinfo.example;

import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import dao.DaoMaster;
import dao.DaoMaster.DevOpenHelper;
import dao.DaoSession;
import dao.UserInfo;
import dao.UserInfoDao;

public class UserInfoActivity extends ListActivity {

    private SQLiteDatabase db;

    private EditText editTextName;
    private EditText editTextAge;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserInfoDao userInfoDao;

    private Cursor cursor;    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        userInfoDao = daoSession.getNoteDao();

        String textColumn = UserInfoDao.Properties.Text.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = db.query(userInfoDao.getTablename(), userInfoDao.getAllColumns(), null, null, null, null, orderBy);
        String[] from = { textColumn, UserInfoDao.Properties.Comment.columnName };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        addUiListeners();
    }

    protected void addUiListeners() {
    	editTextAge.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {            	
                if (actionId == EditorInfo.IME_ACTION_DONE && editTextName.getText().length()>0) {
                    addRecord();
                    return true;
                }
                return false;
            }
        });

        final View button = findViewById(R.id.buttonAdd);
        button.setEnabled(false);
        editTextAge.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = (s.length() != 0 && editTextName.getText().length()>0);
                button.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        
        editTextName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = (s.length() != 0 && editTextAge.getText().length()>0);
                button.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onMyButtonClick(View view) {
    	addRecord();
    }

    private void addRecord() {
        String nameText = editTextName.getText().toString();
        String ageText = editTextAge.getText().toString();
        editTextName.setText("");
        editTextAge.setText("");

        UserInfo userinfo = new UserInfo(null, nameText, ageText, new Date());
        userInfoDao.insert(userinfo);
        Log.d("DaoExample", "Inserted new userinfo, ID: " + userinfo.getId());

        cursor.requery();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	userInfoDao.deleteByKey(id);
        Log.d("DaoExample", "Deleted note, ID: " + id);
        cursor.requery();
    }

}