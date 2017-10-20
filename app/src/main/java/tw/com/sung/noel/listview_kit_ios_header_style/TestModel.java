package tw.com.sung.noel.listview_kit_ios_header_style;

/**
 * Created by noel on 2017/10/12.
 */

public class TestModel {
    private String name;
    private String age;
    private String height;
    private String weight;
    private String tag;

    public TestModel(String name, String age, String height, String weight, String tag) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.tag = tag;

    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
