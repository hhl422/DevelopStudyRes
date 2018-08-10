package self.bobby.ui.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import self.bobby.ui.R;

/**
 * Created by hehongli on 2018/8/10.
 */

public class FruitAdapter extends ArrayAdapter {


    private ImageView fruitImage;
    private TextView fruitName;
    private final int resourceId;

    public FruitAdapter(Context context, int resource, List<Fruit> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    /**
     * 将list中的数据/对象信息传入对应的布局
     * @param position 根据list中的索引，查找出当前索引对应的数据实例
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = (Fruit) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//使用Inflater对象来将布局文件解析成一个View

        fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        fruitName = (TextView) view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());

        return view;
    }


}
