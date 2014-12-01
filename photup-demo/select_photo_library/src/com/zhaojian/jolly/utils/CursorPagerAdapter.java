/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhaojian.jolly.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public abstract class CursorPagerAdapter extends PagerAdapter {

    /**
     * This field should be made private, so it is hidden from the SDK. {@hide
     *
     *
     * }
     */
    protected boolean mDataValid;
    /**
     * This field should be made private, so it is hidden from the SDK. {@hide
     *
     *
     * }
     */
    protected boolean mAutoRequery;
    /**
     * This field should be made private, so it is hidden from the SDK. {@hide
     *
     *
     * }
     */
    protected Cursor mCursor;
    /**
     * This field should be made private, so it is hidden from the SDK. {@hide
     *
     *
     * }
     */
    protected Context mContext;
    /**
     * This field should be made private, so it is hidden from the SDK. {@hide
     *
     *
     * }
     */
    protected int mRowIDColumn;
    /**
     * This field should be made private, so it is hidden from the SDK. {@hide
     *
     *
     * }
     */
    protected ChangeObserver mChangeObserver;
    /**
     * This field should be made private, so it is hidden from the SDK. {@hide
     *
     *
     * }
     */
    protected DataSetObserver mDataSetObserver;

    /**
     * If set the adapter will call requery() on the cursor whenever a content change notification is
     * delivered. Implies {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     *
     * @deprecated This option is discouraged, as it results in Cursor queries being performed on the
     *             application's UI thread and thus can cause poor responsiveness or even Application
     *             Not Responding errors. As an alternative, use {@link android.app.LoaderManager} with
     *             a {@link android.content.CursorLoader}.
     */
    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 0x01;

    /**
     * If set the adapter will register a content observer on the cursor and will call {@link
     * #onContentChanged()} when a notification comes in. Be careful when using this flag: you will
     * need to unset the current Cursor from the adapter to avoid leaks due to its registered
     * observers. This flag is not needed when using a CursorAdapter with a {@link
     * android.content.CursorLoader}.
     */
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 0x02;

    /**
     * Constructor that always enables auto-requery.
     *
     * @param c       The cursor from which to get the data.
     * @param context The context
     * @deprecated This option is discouraged, as it results in Cursor queries being performed on the
     *             application's UI thread and thus can cause poor responsiveness or even Application
     *             Not Responding errors. As an alternative, use {@link android.app.LoaderManager} with
     *             a {@link android.content.CursorLoader}.
     */
    @Deprecated
    public CursorPagerAdapter(Context context, Cursor c) {
        init(context, c, FLAG_AUTO_REQUERY);
    }

    /**
     * Constructor that allows control over auto-requery. It is recommended you not use this, but
     * instead {@link #CursorAdapter(Context, Cursor, int)}. When using this constructor, {@link
     * #FLAG_REGISTER_CONTENT_OBSERVER} will always be set.
     *
     * @param c           The cursor from which to get the data.
     * @param context     The context
     * @param autoRequery If true the adapter will call requery() on the cursor whenever it changes so
     *                    the most recent data is always displayed. Using true here is discouraged.
     */
    public CursorPagerAdapter(Context context, Cursor c, boolean autoRequery) {
        init(context, c, autoRequery ? FLAG_AUTO_REQUERY : FLAG_REGISTER_CONTENT_OBSERVER);
    }

    /**
     * Recommended constructor.
     *
     * @param c       The cursor from which to get the data.
     * @param context The context
     * @param flags   Flags used to determine the behavior of the adapter; may be any combination of
     *                {@link #FLAG_AUTO_REQUERY} and {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public CursorPagerAdapter(Context context, Cursor c, int flags) {
        init(context, c, flags);
    }

    /**
     * @deprecated Don't use this, use the normal constructor. This will be removed in the future.
     */
    @Deprecated
    protected void init(Context context, Cursor c, boolean autoRequery) {
        init(context, c, autoRequery ? FLAG_AUTO_REQUERY : FLAG_REGISTER_CONTENT_OBSERVER);
    }

    void init(Context context, Cursor c, int flags) {
        if ((flags & FLAG_AUTO_REQUERY) == FLAG_AUTO_REQUERY) {
            flags |= FLAG_REGISTER_CONTENT_OBSERVER;
            mAutoRequery = true;
        } else {
            mAutoRequery = false;
        }
        boolean cursorPresent = c != null;
        mCursor = c;
        mDataValid = cursorPresent;
        mContext = context;
        mRowIDColumn = cursorPresent ? c.getColumnIndexOrThrow("_id") : -1;
        if ((flags & FLAG_REGISTER_CONTENT_OBSERVER) == FLAG_REGISTER_CONTENT_OBSERVER) {
            mChangeObserver = new ChangeObserver();
            mDataSetObserver = new MyDataSetObserver();
        } else {
            mChangeObserver = null;
            mDataSetObserver = null;
        }

        if (cursorPresent) {
            if (mChangeObserver != null) {
                c.registerContentObserver(mChangeObserver);
            }
            if (mDataSetObserver != null) {
                c.registerDataSetObserver(mDataSetObserver);
            }
        }
    }

    /**
     * Returns the cursor.
     *
     * @return the cursor.
     */
    public Cursor getCursor() {
        return mCursor;
    }

    /**
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    /**
     * @see android.widget.ListAdapter#getView(int, View, ViewGroup)
     */

    @Override
    public Object instantiateItem(View container, int position) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }

        View v = newView(mContext, mCursor, (ViewGroup) container);
        bindView(v, mContext, mCursor);

        ((ViewPager) container).addView(v);

        return v;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the correct
     *                position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    public abstract View newView(Context context, Cursor cursor, ViewGroup parent);

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the correct
     *                position.
     */
    public abstract void bindView(View view, Context context, Cursor cursor);

    /**
     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be closed.
     *
     * @param cursor The new cursor to be used
     */
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor. Unlike {@link #changeCursor(Cursor)}, the
     * returned old Cursor is <em>not</em> closed.
     *
     * @param newCursor The new cursor to be used.
     * @return Returns the previously set Cursor, or null if there was not one. If the given new Cursor
     *         is the same instance is the previously set Cursor, null is also returned.
     */
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        if (oldCursor != null) {
            if (mChangeObserver != null) {
                oldCursor.unregisterContentObserver(mChangeObserver);
            }
            if (mDataSetObserver != null) {
                oldCursor.unregisterDataSetObserver(mDataSetObserver);
            }
        }
        mCursor = newCursor;
        if (newCursor != null) {
            if (mChangeObserver != null) {
                newCursor.registerContentObserver(mChangeObserver);
            }
            if (mDataSetObserver != null) {
                newCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            mRowIDColumn = -1;
            mDataValid = false;
            // notify the observers about the lack of a data set
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    /**
     * <p> Converts the cursor into a CharSequence. Subclasses should override this method to convert
     * their results. The default implementation returns an empty String for null values or the default
     * String representation of the value. </p>
     *
     * @param cursor the cursor to convert to a CharSequence
     * @return a CharSequence representing the value
     */
    public CharSequence convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    /**
     * Called when the {@link ContentObserver} on the cursor receives a change notification. The
     * default implementation provides the auto-requery logic, but may be overridden by sub classes.
     *
     * @see ContentObserver#onChange(boolean)
     */
    @SuppressWarnings("deprecation")
    protected void onContentChanged() {
        if (mAutoRequery && mCursor != null && !mCursor.isClosed()) {
            mDataValid = mCursor.requery();
        }
    }

    private class ChangeObserver extends ContentObserver {

        public ChangeObserver() {
            super(new Handler());
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            onContentChanged();
        }
    }

    private class MyDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            mDataValid = false;
            notifyDataSetChanged();
        }
    }

}
