package com.misoca.android.stamp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(android.R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View targetView = findViewById(R.id.stamp);
                targetView.setDrawingCacheEnabled(true);

                // Viewのキャッシュを取得
                Bitmap cache = targetView.getDrawingCache();
                Bitmap screenShot = Bitmap.createBitmap(cache);
                targetView.setDrawingCacheEnabled(false);

                FileOutputStream fos = null;
                try {
                    // 出力ファイル名を指定
                    File file = new File(MainActivity.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/stamp.png");
                    // 指定したファイル名が無ければ作成する。
                    file.getParentFile().mkdir();
                    file.createNewFile();
                    fos = new FileOutputStream(file, false);
                    // 画像のフォーマットと画質と出力先を指定して保存
                    screenShot.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    Toast.makeText(MainActivity.this, "Export Success!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Export Failure!", Toast.LENGTH_SHORT).show();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException ie) {
                            fos = null;
                        }
                    }
                }
            }
        });
    }
}
