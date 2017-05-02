package com.xone.qrcode.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.User;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPhone, etPassword, etSurePassword;
    private Button btnGetCode;
    private TextView btnRegister;
    private Timer mTimer;
    private int mLeftTime = 60;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setActivityName("注册");

        initView();
        initClick();
    }

    private void initClick() {
        btnGetCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etSurePassword = (EditText) findViewById(R.id.etSurePassword);
        btnGetCode = (Button) findViewById(R.id.btnGetCode);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                doRegister();
                break;
            case R.id.btnGetCode:
                doGetCode();
                break;
        }
    }

    private void doRegister() {
        final String phoneNumber = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("手机号不能为空！");
            return;
        }

        if (phoneNumber.length() != 11) {
            showToast("手机号格式错误！");
            return;
        }

        final String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空！");
            return;
        }

        if (password.length() < 6) {
            showToast("密码长度不能低于6位！");
            return;
        }

        String smsCode = etSurePassword.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode)) {
            showToast("验证码不能为空！");
            return;
        }

        BmobSMS.verifySmsCode(phoneNumber, smsCode, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    doRegisterRequest(phoneNumber, password);
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }

    private void doRegisterRequest(String phoneNumber, String password) {
        User user = new User();
        user.setUsername(phoneNumber);
        user.setMobilePhoneNumber(phoneNumber);
        user.setPassword(password);

        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }

    private void doGetCode() {
        String phoneNumber = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("手机号不能为空");
            return;
        }

        if (phoneNumber.length() != 11) {
            showToast("手机号格式错误！");
            return;
        }

        btnGetCode.setClickable(false);
        btnGetCode.setEnabled(false);

        BmobSMS.requestSMSCode(phoneNumber, "恶意二维码识别系统", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {

                if (e == null) {
                    btnGetCode.setText("重新获取\n(60)s");
                    mTimer = new Timer();
                    mTimer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            mMainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mLeftTime--;
                                    btnGetCode.setText("重新获取\n(" + mLeftTime + ")s");
                                    if (0 == mLeftTime) {
                                        btnGetCode.setClickable(true);
                                        btnGetCode.setEnabled(true);
                                        btnGetCode.setText("验证");
                                        mLeftTime = 60;
                                        mTimer.cancel();
                                        mTimer = null;
                                    }
                                }
                            });
                        }
                    }, 1000, 1000);
                } else {
                    btnGetCode.setClickable(true);
                    btnGetCode.setEnabled(true);
                }

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mTimer) {
            mTimer.cancel();
        }
    }
}
