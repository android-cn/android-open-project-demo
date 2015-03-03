package org.adw.samples.discreteseekbar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity implements View.OnClickListener{
    private final static int TRACK = 0;
    private final static int PROGRESS = 1;
    private final static int FLOATER_START = 2;
    private final static int FLOATER_END = 3;
    @InjectView(R.id.sample_seekbar)
    DiscreteSeekBar sbTarget;
    @InjectView(R.id.rg_position)
    RadioGroup rgPosition;//modify the color of different parts
    @InjectView(R.id.rbt_track)
    RadioButton rbtTrack;
    @InjectView(R.id.rbt_progress_bar)
    RadioButton rbtProgress;
    @InjectView(R.id.rbt_floater_end)
    RadioButton rbtFloaterEnd;
    @InjectView(R.id.rbt_floater_start)
    RadioButton rbtFloaterStart;
    @InjectView(R.id.red_discrete)
    DiscreteSeekBar sbRed;
    @InjectView(R.id.green_discrete)
    DiscreteSeekBar sbGreen;
    @InjectView(R.id.blue_discrete)
    DiscreteSeekBar sbBlue;
    @InjectView(R.id.btn_set_color)
    Button btnSetColor;
    @InjectView(R.id.min_discrete)
    DiscreteSeekBar sbMin;
    @InjectView(R.id.max_discrete)
    DiscreteSeekBar sbMax;
    @InjectView(R.id.btn_set_value)
    Button btnSetValue;
    @InjectView(R.id.edt_regex)
    EditText edtRegex;
    @InjectView(R.id.btn_set_regex)
    Button btnSetRegex;

    int colorPos;
    int iFloaterStartColor = -1;
    int iFloaterEndColor = -1;
    int iColor = 0xffffffff;

    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        ctx = this;

        rgPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == rbtTrack.getId()){
                    colorPos = TRACK;
                }else if(checkedId == rbtProgress.getId()){
                    colorPos = PROGRESS;
                }else if(checkedId == rbtFloaterStart.getId()){
                    colorPos = FLOATER_START;
                }else if(checkedId == rbtFloaterEnd.getId()){
                    colorPos = FLOATER_END;
                }
            }
        });
        rbtProgress.setChecked(true);

        sbRed.setOnProgressChangeListener(colorListener);
        sbGreen.setOnProgressChangeListener(colorListener);
        sbBlue.setOnProgressChangeListener(colorListener);

        btnSetColor.setOnClickListener(this);
        btnSetRegex.setOnClickListener(this);
        btnSetValue.setOnClickListener(this);


        /**
         * 初始化
         */
        sbTarget.setThumbColor(0xffFF8877, 0xff00ff00);
        sbTarget.setScrubberColor(0xffff0000);
        sbTarget.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value * 100;
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_set_color:
                switch (colorPos){
                    case TRACK:
                        /**
                         * 只能在xml中设置
                         */
                        Toast.makeText(ctx, "no API for track color", Toast.LENGTH_SHORT).show();
                        break;
                    case PROGRESS:
                        sbTarget.setScrubberColor(iColor);
                        break;
                    case FLOATER_START:
                        iFloaterStartColor = iColor;
                        if(iFloaterEndColor == -1) {
                            //这个接口有耦合，不爽
                            sbTarget.setThumbColor(iColor, iColor);
                        }else{
                            sbTarget.setThumbColor(iColor, iFloaterEndColor);
                        }
                        break;
                    case FLOATER_END:
                        iFloaterEndColor = iColor;
                        if(iFloaterStartColor == -1) {
                            sbTarget.setThumbColor(iColor, iColor);
                        }else{
                            sbTarget.setThumbColor(iFloaterStartColor, iColor);
                        }
                        break;
                }
                break;
            case R.id.btn_set_value:
                /**
                 * sbMin 会看到数字显示不全的BUG，因为0>-1000，但是floater的宽度是由Max设置的，所以....
                 */
                sbTarget.setMin(sbMin.getProgress());
                sbTarget.setMax(sbMax.getProgress());
                break;
            case R.id.btn_set_regex:
                /**
                 * 这个接口只能显示数字，如果我想用seekbar显示时间就没有办法了。
                 */
                sbTarget.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
                    @Override
                    public int transform(int value) {
                        try {
                            float multiple = Float.parseFloat(edtRegex.getText().toString());
                            return (int)(value * multiple);
                        }catch (Exception e) {
                            return value;
                        }
                    }
                });
        }
    }

    DiscreteSeekBar.OnProgressChangeListener colorListener = new DiscreteSeekBar.OnProgressChangeListener() {
        @Override
        public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            if(seekBar.getId() == sbRed.getId()){
                iColor = 0xff000000 + (value<<16) + (sbGreen.getProgress()<<8) + (sbBlue.getProgress());
            }else if(seekBar.getId() == sbGreen.getId()){
                iColor = 0xff000000 + (sbRed.getProgress()<<16) + (value<<8) + (sbBlue.getProgress());
            }else{
                iColor = 0xff000000 + (sbRed.getProgress()<<16) + (sbGreen.getProgress()<<8) + (value);
            }

            btnSetColor.setTextColor(iColor);
        }

        @Override
        public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

        }
    };
}
