package com.tesmple.chromeprocessbar.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.tesmple.chromeprocessbar.R;
import com.tesmple.chromeprocessbar.TransformationMethod.ChangeInputTransformationMethod;

/**
 * Created by ESIR on 2015/12/1.
 */
public class EditTextActivity extends AppCompatActivity {
    /**
     * 测试目标EditText
     */
    private EditText etEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
        etEditText = (EditText)findViewById(R.id.et_EditText);
        etEditText.setTransformationMethod(new ChangeInputTransformationMethod());
    }
}
