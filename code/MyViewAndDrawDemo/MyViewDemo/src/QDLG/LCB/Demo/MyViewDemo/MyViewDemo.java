package QDLG.LCB.Demo.MyViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyViewDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);
        
        //找到新建的View对象
        final MyView myview=(MyView)findViewById(R.id.myview);
        
        //按钮监听器
        Button btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener() {	
        	String newText="更新显示!";
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myview.getText()==newText) //切换文本显示
					myview.setText("测试文本!");
				else
					myview.setText(newText);
			}
		});
    }
}