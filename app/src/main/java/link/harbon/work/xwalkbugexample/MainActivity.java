package link.harbon.work.xwalkbugexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import org.xwalk.core.XWalkInitializer;
import org.xwalk.core.XWalkUpdater;
import org.xwalk.core.XWalkView;

public class MainActivity extends AppCompatActivity implements XWalkInitializer.XWalkInitListener,XWalkUpdater.XWalkBackgroundUpdateListener{

    XWalkInitializer mXWalkInitializer;
    XWalkUpdater mXWalkUpdater;
    ViewGroup mRootView;

    Handler mHandler = new Handler();
    //before run this app ,please remove codes contains xwalk, let xwalk downlaod apk and initialize first.
    // when you see baidu website,click any link,find a webpage contains text plain, then long click it to fire action mode.
    //then you will see the crash
    //I 'm using crosswalk 18 , a new mode called "download mode" based on share mode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mXWalkInitializer = new XWalkInitializer(this, this);
        mXWalkUpdater = new XWalkUpdater(this, this);
        mXWalkInitializer.initAsync();

        //please remove codes below before xwalk Initialize complete
        final XWalkView xWalkView = new XWalkView(this, this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        xWalkView.setLayoutParams(layoutParams);
        mRootView.addView(xWalkView);//
        // I don't know why ,if I load url immediately app will crash, so I  postDelayed 20000 ms
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                xWalkView.load("http://www.baidu.com", null);
            }
        }, 2000);



    }


    @Override
    public void onXWalkInitStarted() {

    }

    @Override
    public void onXWalkInitCancelled() {

    }

    @Override
    public void onXWalkInitFailed() {
        Log.i("xwalkbug", "xwalk init failed");
        mXWalkUpdater.updateXWalkRuntime();
    }

    @Override
    public void onXWalkInitCompleted() {

    }

    @Override
    public void onXWalkUpdateStarted() {

    }

    @Override
    public void onXWalkUpdateProgress(int i) {
        Log.i("xwalkbug", "progress:" + i);
    }

    @Override
    public void onXWalkUpdateCancelled() {

    }

    @Override
    public void onXWalkUpdateFailed() {

    }

    @Override
    public void onXWalkUpdateCompleted() {
        Log.i("xwalkbug", "xwalk update complete");
        mXWalkInitializer.initAsync();
    }
}
