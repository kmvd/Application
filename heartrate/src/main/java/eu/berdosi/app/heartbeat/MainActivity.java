package eu.berdosi.app.heartbeat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.Surface;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;


@Route(path = "/heartrate/MainActivity") //注解表示该页面的路由路径，需要跳转页面则增加
public class MainActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final CameraService cameraService = new CameraService(this);
    private OutputAnalyzer analyzer;

    private final int REQUEST_CODE_CAMERA = 0;
    public static final int MESSAGE_UPDATE_REALTIME = 1;
    public static final int MESSAGE_UPDATE_FINAL = 2;

    private static final int MENU_INDEX_EXPORT_RESULT = 1;
    private static final int MENU_INDEX_EXPORT_DETAILS = 2;

    private boolean justShared = false;

    @SuppressLint("HandlerLeak")
    private final Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == MESSAGE_UPDATE_REALTIME) {
                ((TextView) findViewById(R.id.textView)).setText(msg.obj.toString());
            }

            if (msg.what == MESSAGE_UPDATE_FINAL) {
                ((EditText) findViewById(R.id.editText)).setText(msg.obj.toString());

                // make sure menu items are enabled when it opens.
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.getMenu().getItem(MENU_INDEX_EXPORT_RESULT).setVisible(true);
                toolbar.getMenu().getItem(MENU_INDEX_EXPORT_DETAILS).setVisible(true);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);

        TextureView cameraTextureView = findViewById(R.id.textureView2);
        SurfaceTexture previewSurfaceTexture = cameraTextureView.getSurfaceTexture();

        // justShared is set if one clicks the share button.
        if ((previewSurfaceTexture != null) && !justShared) {
            // this first appears when we close the application and switch back
            // - TextureView isn't quite ready at the first onResume.
            Surface previewSurface = new Surface(previewSurfaceTexture);

            // show warning when there is no flash
            if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Snackbar.make(findViewById(R.id.constraintLayout), getString(R.string.noFlashWarning), Snackbar.LENGTH_LONG);
            }

            cameraService.start(previewSurface);
            analyzer.measurePulse(cameraTextureView, cameraService);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraService.stop();
        if (analyzer != null) analyzer.stop();
        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CODE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Snackbar.make(
                        findViewById(R.id.constraintLayout),
                        getString(R.string.cameraPermissionRequired),
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i("MENU", "menu is being prepared");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onPrepareOptionsMenu(menu);
    }

    public void onClickNewMeasurement(MenuItem item) {
        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);

        // clear prior results
        char[] empty = new char[0];
        ((EditText) findViewById(R.id.editText)).setText(empty, 0, 0);
        ((TextView) findViewById(R.id.textView)).setText(empty, 0, 0);

        TextureView cameraTextureView = findViewById(R.id.textureView2);
        SurfaceTexture previewSurfaceTexture = cameraTextureView.getSurfaceTexture();

        if (previewSurfaceTexture != null) {
            // this first appears when we close the application and switch back
            // - TextureView isn't quite ready at the first onResume.
            Surface previewSurface = new Surface(previewSurfaceTexture);
            cameraService.start(previewSurface);
            analyzer.measurePulse(cameraTextureView, cameraService);
        }
    }

    public void onClickExportResult(MenuItem item) {
        final Intent intent = getTextIntent((String) ((TextView) findViewById(R.id.textView)).getText());
        justShared = true;
        startActivity(Intent.createChooser(intent, getString(R.string.send_output_to)));
    }

    public void onClickExportDetails(MenuItem item) {
        final Intent intent = getTextIntent(((EditText) findViewById(R.id.editText)).getText().toString());
        justShared = true;
        startActivity(Intent.createChooser(intent, getString(R.string.send_output_to)));
    }

    private Intent getTextIntent(String intentText) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(
                Intent.EXTRA_SUBJECT,
                String.format(
                        getString(R.string.output_header_template),
                        new SimpleDateFormat(
                                getString(R.string.dateFormat),
                                Locale.getDefault()
                        ).format(new Date())
                ));
        intent.putExtra(Intent.EXTRA_TEXT, intentText);
        return intent;
    }
}
