package cn.hiliang.demo.picasso;

/**
 * Created by liang on 11/26/14.
 */
final public class Data {

    static final String AVATAR_BASE = "https://avatars2.githubusercontent.com/u/";

    enum ANDROID_CN_PEOPLE {
        AARONPLAY("AaronPlay", "4213205"),
        DAIMAJIA("daimajia", "2503423"),
        HALZHANG("halzhang", "542297"),
        KESENHOO("kesenhoo", "1456490"),
        LIANG7("liang7", "1677220"),
        SINGWHATIWANNA("singwhatiwanna", "3346272"),
        STORMZHANG("stormzhang", "2267900"),
        TRINEA("Trinea", "1169522"),
        WAYLIFE("waylife", "3348620");

        private final People people;

        private ANDROID_CN_PEOPLE(String name, String id){
            this.people = new People(id, name, AVATAR_BASE+id);
        }

        public People getPeople(){
            return this.people;
        }
    }
}
