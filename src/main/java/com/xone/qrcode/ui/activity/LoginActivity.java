package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPhone, etPassword;
    private TextView tvForget, btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActivityName("登录");
        setRightTextView("注册", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 200);
            }
        });

        initView();
        initClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode && 200 == requestCode) {
            finish();
        }
    }

    private void initClick() {
        tvForget.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvForget = (TextView) findViewById(R.id.tvForget);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvForget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btnLogin:
                doLogin();
                break;
        }
    }

    private void doLogin() {
        String phoneNumber = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("手机号不能为空");
            return;
        }

        if (phoneNumber.length() != 11) {
            showToast("手机号格式错误！");
            return;
        }

        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }

        User.loginByAccount(phoneNumber, password, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    finish();
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }
}