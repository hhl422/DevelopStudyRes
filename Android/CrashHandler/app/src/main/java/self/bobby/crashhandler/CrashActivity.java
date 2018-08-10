package self.bobby.crashhandler;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by hehongli on 2018/8/8.
 */

public class CrashActivity extends Activity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button = new Button(this);
        button.setText("死亡按钮");

        setContentView(button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == button){
            throw new RuntimeException("自定义异常：这是自己抛出的异常");
        }
    }
}
