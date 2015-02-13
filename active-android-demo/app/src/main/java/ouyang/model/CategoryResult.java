package ouyang.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by zuxiang on 2015/2/9.
 */
public class CategoryResult {
    public static final String CATEGORY_JSON = "{\"rows\": [ {\"id\":1, \"name\": \"IT一族\"},{\"id\":2,\"name\": \"高富帅\" },{ \"id\":3,\"name\": \"白富美\" }]}";
    public List<Category> rows;

    public static List<Category> getCategorys(String json) {
        Gson gson = new Gson();
        CategoryResult categoryResult = gson.fromJson(json, CategoryResult.class);
        return categoryResult.rows;
    }
}
