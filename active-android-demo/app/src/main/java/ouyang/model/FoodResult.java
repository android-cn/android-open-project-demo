package ouyang.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by zuxiang on 2015/2/9.
 */
public class FoodResult {
    public static final String F1_JSON = "{\"rows\": [{\"pk_id\":1, \"cid\": 1,\"name\": \"海西青虾\",\"price\": 150.12},{\"pk_id\":2, \"cid\": 1,\"name\": \"海鲜套餐\",\"price\": 102.12}, {\"pk_id\":3,\"cid\": 1,\"name\": \"叉烧鸭肉套餐\",\"price\": 110.12},{\"pk_id\":4,\"cid\": 1,\"name\": \"鲜虾一盘\",\"price\": 10.12},{\"pk_id\":5,\"cid\": 1,\"name\": \"白切鸡套餐\",\"price\": 10.12},{\"pk_id\":6,\"cid\": 1,\"name\": \"红烧酱鸭\", \"price\": 160.12}, {\"pk_id\":7,\"cid\": 1,\"name\": \"叉烧鸭肉套餐\", \"price\": 110.12},{\"pk_id\":8,\"cid\": 1,\"name\": \"珍珠贝套餐\",\"price\": 107.12}]}";
    public static final String F2_JSON ="{\"rows\": [{\"cid\":2,\t\"pk_id\":11,\"name\": \"青椒肉丝\", \"price\": 150.12},{\"cid\":2,\"pk_id\":12,\"name\": \"荔枝肉\",\"price\": 102.12}, {\"cid\":2,\"pk_id\":13,\"name\": \"排骨套餐\",\"price\": 110.12},{\"cid\":2,\t\"pk_id\":14, \"name\": \"鸡排套餐\", \"price\": 10.12}, {\"cid\":2,\"pk_id\":15, \"name\": \"鸭腿套餐\",\"price\": 10.12 }, {\"cid\":2,\"pk_id\":16,\"name\": \"鸡翅套餐\",\"price\": 160.12} ]}";
    public static final String F3_JSON = "{\"rows\": [{\"cid\": 3,\"pk_id\":20,\"name\": \"兔肉套餐\", \"price\": 130.12},{\"cid\": 3,\"pk_id\":21,\"name\": \"带鱼套餐\", \"price\": 150.12 }, {\"cid\": 3,\"pk_id\":22,\"name\": \"筒骨套餐\",\"price\": 102.12 }, { \"cid\": 3,\"pk_id\":23,\"name\": \"叉烧鸡套餐\",\"price\": 110.12},{ \"name\": \"白切鸡套餐\",\"price\": 10.12}]}";
    private List<Food> rows;

    public static List<Food> getFoods(String json) {
        Gson gson = new Gson();
        FoodResult foodResult = gson.fromJson(json, FoodResult.class);
        return foodResult.rows;
    }
}
