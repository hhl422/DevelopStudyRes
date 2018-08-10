package self.bobby.ui.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import self.bobby.ui.R;

/**
 * Created by hehongli on 2018/8/10.
 */

public class ListViewActivity extends Activity implements AdapterView.OnItemClickListener{
    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        initFruits(); // 初始化水果数据
        FruitAdapter adapter = new FruitAdapter(ListViewActivity.this, R.layout.list_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(ListViewActivity.this, fruitList.get(i).getName(),Toast.LENGTH_SHORT);//须在主线程
        Log.d("hhl",fruitList.get(i).getName());
    }


    private void initFruits() {
        Fruit apple = new Fruit("Apple", R.drawable.apple);
        fruitList.add(apple);
        Fruit kiwi = new Fruit("Kiwi", R.drawable.kiwi);
        fruitList.add(kiwi);
        Fruit lemon = new Fruit("Lemon", R.drawable.lemon);
        fruitList.add(lemon);
        Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon);
        fruitList.add(watermelon);
        Fruit pear = new Fruit("Pear", R.drawable.pear);
        fruitList.add(pear);
        Fruit grape = new Fruit("Grape", R.drawable.grapes);
        fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple);
        fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry);
        fruitList.add(strawberry);
        Fruit cherry = new Fruit("Cherry", R.drawable.cherry);
        fruitList.add(cherry);
    }

}
