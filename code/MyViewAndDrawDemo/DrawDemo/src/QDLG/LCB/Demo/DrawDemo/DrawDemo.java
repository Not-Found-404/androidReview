package QDLG.LCB.Demo.DrawDemo;

import android.app.Activity;
import android.os.Bundle;

public class DrawDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //创建自定义的View对象
        CustomView view=new CustomView(this);
        
        //设置Activity的显示内容为刚创建的View对象
        setContentView(view);        
    }
}