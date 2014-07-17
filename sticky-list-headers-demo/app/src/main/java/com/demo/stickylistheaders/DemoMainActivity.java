package com.demo.stickylistheaders;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 */
public class DemoMainActivity extends ActionBarActivity {

    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,ContactsContract.Contacts.SORT_KEY_ALTERNATIVE};
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int SORT_KEY_ALTERNATIVE_INDEX = 1;
    StickyListHeadersListView mList;
    private List<Contact> mContacts=new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        mList = (StickyListHeadersListView)findViewById(R.id.list);
        mList.setAreHeadersSticky(true);
        AsyncTask<Void,Void,Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fetchPhoneContacts();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mList.setAdapter(new DemoListAdapter(DemoMainActivity.this,mContacts));
            }
        };
        asyncTask.execute();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_header_sticky) {
            if(mList.areHeadersSticky()){
                mList.setAreHeadersSticky(false);
                item.setTitle(R.string.action_enable_header_sticky);
            }else {
                mList.setAreHeadersSticky(true);
                item.setTitle(R.string.action_disable_header_sticky);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**得到手机通讯录联系人信息**/
    private void fetchPhoneContacts() {
        ContentResolver resolver = getContentResolver();

        // 获取手机联系人
//        this.grantUriPermission();
        Cursor phoneCursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,PHONES_PROJECTION, null, null, ContactsContract.Contacts.SORT_KEY_ALTERNATIVE);


        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                Contact contact = new Contact();
                contact.name = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                contact.sort_key = phoneCursor.getString(SORT_KEY_ALTERNATIVE_INDEX);;
                mContacts.add(contact);
            }
            phoneCursor.close();
            phoneCursor = null;
        }
    }

}
