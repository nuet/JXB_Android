package com.lenso.jixiangbao.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lenso.jixiangbao.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/19.
 */
public class RegisterActivity extends BaseActivity {
    @Bind(R.id.iv_register_agree)
    ImageView ivRegisterAgree;
    @Bind(R.id.et_register_psw)
    EditText etRegisterPsw;

    private static boolean BOXCHECKED = false;
    private static boolean EYECLICKED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_register_agree, R.id.btn_register, R.id.btn_eye})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_agree:
                if(!BOXCHECKED){
                    ivRegisterAgree.setImageResource(R.mipmap.checkbox_checked);
                    BOXCHECKED = true;
                }else{
                    ivRegisterAgree.setImageResource(R.mipmap.checkbox_unchecked);
                    BOXCHECKED = false;
                }
                break;
            case R.id.btn_register:
                showToast("btn_register clicked!");
                break;
            case R.id.btn_eye:
                if(!EYECLICKED){
                    etRegisterPsw.setInputType(InputType.TYPE_CLASS_TEXT);
                    etRegisterPsw.setSelection(etRegisterPsw.getText().length());
                    EYECLICKED = true;
                }else{
                    etRegisterPsw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etRegisterPsw.setSelection(etRegisterPsw.getText().length());
                    EYECLICKED = false;
                }
                break;
        }
    }
}
