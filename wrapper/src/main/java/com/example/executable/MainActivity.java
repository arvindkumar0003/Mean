package com.example.executable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

public class MainActivity extends Activity {
    private Handler handler = new Handler(Looper.myLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startProcess();
    }

    @SuppressLint("SetTextI18n")
    private void addText(final String s) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.outputView);
                textView.setText(textView.getText() + s);
            }
        });
    }

    private String getExceptionText(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void startProcess() {
        String nativeDir = getApplicationInfo().nativeLibraryDir;
        Log.d("XXX", "native dir: " + nativeDir);
        try {
            final Process process = new ProcessBuilder().redirectErrorStream(true).command(nativeDir + "/libexecutable.so").start();
            final BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            new Thread() {
                @Override
                public void run() {
                    try {
                        String line;
                        while ((line = processReader.readLine()) != null) {
                            final String finalLine = line;
                            addText(finalLine + "\n");
                        }
                        addText("\nProcess exited - code: " + process.waitFor());
                    } catch (IOException | InterruptedException e) {
                        addText("\n" + getExceptionText(e));
                    }
                }
            }.start();
        } catch (IOException e) {
            addText(getExceptionText(e));
        }
    }
}
