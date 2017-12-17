package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.yl.library.base.activity.BaseActivity;
import com.yl.library.utils.DateUtils;
import com.yl.library.utils.ImageManager;
import com.yl.library.utils.KeyBoardUtils;
import com.yl.library.utils.PictureSelectorUtils;
import com.yl.library.widget.EditTextGroup;
import com.yl.library.widget.TextViewGroup;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.User;
import com.yl.yhbmfw.mvp.contract.ProfileContract;
import com.yl.yhbmfw.mvp.presenter.ProfilePresenter;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity<ProfileContract.Presenter> implements ProfileContract.View {

    private static final int REQUEST_CODE_IMAGE = 201;

    @BindView(R.id.iv_head)
    ImageView mIvHead;

    @BindView(R.id.et_nickname)
    EditTextGroup mEtNickname;

    @BindView(R.id.tv_sex)
    TextViewGroup mTvSex;

    @BindView(R.id.tv_birthday)
    TextViewGroup mTvBirthday;

    @BindView(R.id.et_email)
    EditTextGroup mEtEmail;

    private OptionsPickerView mOptionsPickerView;
    private TimePickerView mTimePickerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initMVP() {
        mPresenter = new ProfilePresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("个人中心");
        setTextRight("提交");
    }

    @Override
    protected void initData() {
        User user = App.getInstance().getConfig().getUser();
        if (user != null) {
            ImageManager.getInstance().loadCircleImage(this, user.getPhoto(), mIvHead);
            mEtNickname.setTextRight(user.getNickname());
            mTvSex.setTextRight(user.getSex());
            mTvBirthday.setTextRight(user.getBirthday());
            mEtEmail.setTextRight(user.getEmail());
        }
    }

    @Override
    public void onTextViewRightClicked() {
        super.onTextViewRightClicked();
        mPresenter.submit();
    }

    @OnClick(R.id.iv_head)
    public void onIvHeadClicked() {
        PictureSelectorUtils.getImageSingleOption(this, REQUEST_CODE_IMAGE);
    }

    @OnClick(R.id.tv_sex)
    public void onTvSexClicked() {
        KeyBoardUtils.closeSoftInput(this);
        mOptionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mTvSex.setTextRight(mPresenter.getSex().get(options1));
            }
        }).build();
        mOptionsPickerView.setPicker(mPresenter.getSex());
        if (!mOptionsPickerView.isShowing()) {
            mOptionsPickerView.show();
        }
    }

    @OnClick(R.id.tv_birthday)
    public void onTvBirthdayClicked() {
        KeyBoardUtils.closeSoftInput(this);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();

        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvBirthday.setTextRight(DateUtils.date2YMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setRangDate(startDate, endDate)
                .setDate(endDate)
                .isCenterLabel(false)
                .build();
        if (!mTimePickerView.isShowing()) {
            mTimePickerView.show();
        }
    }

    @Override
    public String getNickname() {
        return mEtNickname.getTextRight();
    }

    @Override
    public String getSex() {
        return mTvSex.getTextRight();
    }

    @Override
    public String getBirthday() {
        return mTvBirthday.getTextRight();
    }

    @Override
    public String getEmail() {
        return mEtEmail.getTextRight();
    }

    @Override
    public void setHeadImage(String path) {
        ImageManager.getInstance().loadCircleImage(this, path, mIvHead);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_IMAGE) {
            mPresenter.setHeadImage(data);
        }
    }
}
