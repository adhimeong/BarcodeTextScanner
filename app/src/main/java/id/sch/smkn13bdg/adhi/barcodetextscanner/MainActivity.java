package id.sch.smkn13bdg.adhi.barcodetextscanner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.text.Text;

public class MainActivity extends AppCompatActivity {
    private Button scanQrcodebtn, scantextbtn;
    private TextView result;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        scanQrcodebtn = (Button) findViewById(R.id.scanqrcodeebtn);
        scantextbtn = (Button) findViewById(R.id.scantextbtn);
        result = (TextView) findViewById(R.id.result);
        scanQrcodebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        scantextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScanTextActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                final String camtext = data.getStringExtra("camtext");

                if (barcode != null){
                    result.post(new Runnable() {

                        @Override
                        public void run() {
                            result.setText(barcode.displayValue);
                        }
                    });

                }else if(camtext != null){
                    result.post(new Runnable() {

                        @Override
                        public void run() {
                            result.setText(camtext);
                        }
                    });
                }

            }
        }

    }
}
