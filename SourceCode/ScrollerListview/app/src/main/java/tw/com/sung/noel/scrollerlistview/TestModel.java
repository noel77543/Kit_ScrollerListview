package tw.com.sung.noel.scrollerlistview;

/**
 * Created by noel on 2017/10/12.
 */

public class TestModel {
    private String name;
    private String tag;

    public TestModel(String name, String tag) {
        this.name = name;
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

}
