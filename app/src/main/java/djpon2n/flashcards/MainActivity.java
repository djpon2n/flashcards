package djpon2n.flashcards;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    private SharedPreferences sharedPrefs;
    private String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);

        Set<String> stringSet = sharedPrefs.getStringSet(getString(R.string.history), new HashSet<String>());
        String[] stringArray = stringSet.toArray(new String[stringSet.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArray);
        ListView historyList = findViewById(R.id.historyList);
        historyList.setAdapter(adapter);
    }

    public void sendMessage(View view)
    {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void openFile(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode,  Intent resultData)
    {
        if (requestCode == READ_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                String path = resultData.getData().getPath();

                //add to history
                Set<String> originalHistroy  = sharedPrefs.getStringSet(getString(R.string.history), new HashSet<String>());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                if (!originalHistroy.contains(path))
                {
                    //make a copy of originalHistory
                    Set<String> newHistory = new HashSet<>(originalHistroy);
                    newHistory.add(path);
                    editor.putStringSet(getString(R.string.history), newHistory);
                }

                editor.apply();
            }
            else
            {
                Log.d(TAG, "onActivityResult: something went wrong");
                //cry
            }
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id)
    {

    }
}