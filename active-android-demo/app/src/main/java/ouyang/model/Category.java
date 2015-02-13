package ouyang.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by zuxiang on 2015/2/8.
 */
@Table(name = "categorys")
public class Category extends Model {
    @Column(name = "Name")
    public String name;
    @Column(name="cid")
    public int id;

    @Column(name=BaseColumns._ID)
    public int ID;
    public Category(String name) {
        this.name = name;
    }

    public Category(){

    }

    public static Category getCategory(Cursor cursor){
        Category category = new Category();
        category.name = cursor.getString(cursor.getColumnIndex("Name"));
        category.id = cursor.getInt(cursor.getColumnIndex("cid"));
        return category;
    }
}
