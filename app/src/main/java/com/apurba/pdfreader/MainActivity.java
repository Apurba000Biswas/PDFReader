package com.apurba.pdfreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int PDF_READ_REQ_CODE = 1001;

    private PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , PackageManager.PERMISSION_GRANTED);

        pdfView = findViewById(R.id.pdfView);
    }

    private void selectPdfFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, PDF_READ_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == PDF_READ_REQ_CODE && resultCode == RESULT_OK) {
            processPdf(intent.getData());
        }
    }

    private void processPdf(Uri targetUri){

        File file = new File(targetUri.getPath());
        Log.d("Test", "Is file : " + targetUri.getAuthority());
        Log.d("Test", "Is file : " + file.isFile());
        Log.d("Test", "Is Directory : " + file.isDirectory());
        Log.d("Test", "Is Hidden : " + file.isHidden());


        pdfView.fromUri(targetUri).enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();

    }


    public void readButtonOnClick(View view){
        selectPdfFromGallery();
    }
}
