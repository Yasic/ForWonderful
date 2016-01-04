package com.tesmple.chromeprocessbar.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.dreams.DreamService;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tesmple.chromeprocessbar.R;
import com.tesmple.chromeprocessbar.TransformationMethod.ChangeInputTransformationMethod;
import com.tesmple.chromeprocessbar.Utils.SimpleImageViewUtil;

import org.w3c.dom.EntityReference;

/**
 * Created by ESIR on 2015/12/1.
 */
public class EditTextActivity extends AppCompatActivity {
    /**
     * 测试目标EditText
     */
    private EditText etEditText;

    private SimpleImageViewUtil simpleImageViewUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
        etEditText = (EditText)findViewById(R.id.et_EditText);
        simpleImageViewUtil = new SimpleImageViewUtil(this);

        SpannableString ss = new SpannableString("<img img>");
        Drawable testDrawable = getResources().getDrawable(R.drawable.headimg4);
        testDrawable.setBounds(0, 0, 300, 500);
        //Bitmap bitmap = ((BitmapDrawable)testDrawable).getBitmap();

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        etEditText.measure(w, h);
        int height = etEditText.getMeasuredHeight();
        int width = etEditText.getMeasuredWidth();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.layout_edittextinsertimg, null);
        ImageView ivInsertImg = (ImageView)view.findViewById(R.id.iv_insertimg);

        Log.i("width",width + "+" + simpleImageViewUtil.dp2pix(width));

        Bitmap bitmap1 = simpleImageViewUtil.getFitSampleBitmap(
                getResources(),
                R.drawable.cardslideview_img4,
                200, 300);
        ivInsertImg.setImageBitmap(bitmap1);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, simpleImageViewUtil.dp2pix(width) - 100, view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        ImageSpan imageSpan = new ImageSpan(this, bitmap, ImageSpan.ALIGN_BASELINE);
        ss.setSpan(imageSpan, 0, ("<img img>").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Editable editable = etEditText.getText();
        int index =  etEditText.getSelectionStart();
        //editable.insert(index,ss);
        editable.append(ss);
    }
}
