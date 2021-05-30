package ru.mirea.starcev.loadermanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String> {

    public final String TAG = this.getClass().getSimpleName();
    private int LoaderID = 1234;
    TextView textEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEdit = (TextView) findViewById(R.id.textEdit);
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(MyLoader.ARG_WORD, String.valueOf(textEdit.getText()));
        getSupportLoaderManager().initLoader(LoaderID++, bundle, this);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        Toast.makeText(this, "onCreateLoader:" + i, Toast.LENGTH_SHORT).show();
        return new MyLoader(this, bundle);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Log.d(TAG, "onLoadFinished" + s);
        Toast.makeText(this, "onLoadFinished:" + s, Toast.LENGTH_SHORT).show();
        textEdit.setText(s);
    }

}