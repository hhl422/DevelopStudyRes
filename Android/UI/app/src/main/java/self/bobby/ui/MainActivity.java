package self.bobby.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import self.bobby.ui.GridView.GridViewActivity;
import self.bobby.ui.ListView.ListViewActivity;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Intent intent;

    private String[] data = { "ListViewActivity","GridViewActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * ArrayAdapter
         *  第一个参数是上下文，就是当前的Activity,
         *  第二个参数表明我们数组中每一条数据的布局是这个view，将每一条数据都显示在这个 view上面,
         *  第三个参数就是我们要显示的数据。
         * /
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView = findViewById(R.id.list_main);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this, GridViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
