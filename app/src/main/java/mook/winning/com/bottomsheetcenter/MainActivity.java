package mook.winning.com.bottomsheetcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.winning.bottomsheetlibrary.BottomSheet;
import com.winning.bottomsheetlibrary.OnItemSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.tvShow);
        final String[] strings = {"相册", "拍照"};
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BottomSheet.Builder(view).setTitle("选择照片")
                        .setListData(strings)
                        .setOnItemClickListener(new OnItemSelectListener() {
                    @Override
                    public void onSelect(int position) {
                        Toast.makeText(MainActivity.this, strings[position], Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
            }
        });
    }
}
