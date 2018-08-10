package self.bobby.ui.GridView;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import self.bobby.ui.R;

/**
 * Created by hehongli on 2018/8/10.
 */

public class GridViewActivity extends Activity{
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        gridView = (GridView) findViewById(R.id.gridview);
        //初始化数据
        initData();

        /**
         * SimpleAdapter
         * 第一个参数 context  表示访问整个android应用程序接口，基本上所有的组件都需要
         * 第二个参数 data     表示生成一个Map(String ,Object)列表选项
         * 第三个参数 resource 表示界面布局的id  表示该文件作为列表项的组件
         * 第四个参数 from     表示该Map对象的哪些key对应value来生成列表项
         * 第五个参数 to       表示来填充的组件 Map对象key对应的资源一依次填充组件 顺序有对应关系
         *
         * 注意的是map对象可以key找不到 但组件的必须要有资源填充
         * 因为 找不到key也会返回null 其实就相当于给了一个null资源
         * 下面的程序中如果 new String[] { "name", "head", "desc","name" } new int[] {R.id.name,R.id.head,R.id.desc,R.id.head}
         * 这个head的组件会被name资源覆盖
         */
//        String[] from={"img","text"};
//        int[] to={R.id.img,R.id.text};
//        adapter=new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);
        adapter=new SimpleAdapter(this, dataList, R.layout.gridview_item, new String[]{"img","text"}, new int[]{R.id.img,R.id.text});

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                AlertDialog.Builder builder= new AlertDialog.Builder(GridViewActivity.this);
                builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();
            }
        });
    }

    void initData() {
        //图标
        int icno[] = {R.drawable.camera, R.drawable.computer, R.drawable.map,
                R.drawable.microphone, R.drawable.open, R.drawable.photo, R.drawable.radio,
                R.drawable.setting, R.drawable.talk};
        //图标下的文字
        String name[] = {"camera", "computer", "map", "microphone", "open", "photo", "radio", "setting", "talk"};

        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }
}
