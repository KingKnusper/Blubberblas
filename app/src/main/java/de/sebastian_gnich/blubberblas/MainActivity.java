package de.sebastian_gnich.blubberblas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibLeft, ibRight;
    TextView tvCount;
    BlubberView blubberView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibLeft = (ImageButton) findViewById(R.id.btn_left);
        ibRight = (ImageButton) findViewById(R.id.btn_right);
        tvCount = (TextView) findViewById(R.id.tv_count);
        blubberView = (BlubberView) findViewById(R.id.blubber);

        ibLeft.setOnClickListener(this);
        ibRight.setOnClickListener(this);

        tvCount.setText(""+blubberView.getCount());
    }

    @Override
    public void onClick(View v)
    {
        int newCount = blubberView.getCount();

        switch (v.getId())
        {
            case R.id.btn_left:
                newCount--;
                break;
            case R.id.btn_right:
                newCount++;
                break;
        }
        if (newCount > 0 && newCount <= 400)
        {
            blubberView.setCount(newCount);
            tvCount.setText(""+blubberView.getCount());
        }
    }
}
