package ouyang.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by zuxiang on 2015/2/8.
 */
@Table(name = "foods")
public class Food extends Model implements Serializable {
    @Column(name = "Name")
    public String name;
    @Column(name = "Price")
    public float price;
    @Column(name = BaseColumns._ID)
    public int ID;
    @Column(name = "pk_id")
    public int pk_id;

    @Column(name = "cid")
    public int cid;

    public Food(String foodName, float price) {
        this.name = foodName;
        this.price = price;
    }

    public Food() {

    }

    public static Food getFood(Cursor cursor) {
        Food food = new Food();
        food.cid = cursor.getInt(cursor.getColumnIndex("cid"));
        food.name = cursor.getString(cursor.getColumnIndex("Name"));
        food.price = cursor.getFloat(cursor.getColumnIndex("Price"));
        food.pk_id = cursor.getInt(cursor.getColumnIndex("pk_id"));
        return food;
    }
}
