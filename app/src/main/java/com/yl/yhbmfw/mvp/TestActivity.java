package com.yl.yhbmfw.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.socks.library.KLog;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.widget.TableView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.ll)
    LinearLayout mLl;

    private TableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        EventTypeItem.EventMaterial mEventMaterial = new EventTypeItem.EventMaterial();
        mEventMaterial.setName("学历信息");
        mEventMaterial.setValue("起始时间,毕业院校,所学专业,学历,培养方式");
        mEventMaterial.setDefault_value("4");

        tableView= new TableView(this);
        tableView.setEventMaterial(mEventMaterial);
        mLl.addView(tableView);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        KLog.d("TestActivity",tableView.getValue());
    }
}
