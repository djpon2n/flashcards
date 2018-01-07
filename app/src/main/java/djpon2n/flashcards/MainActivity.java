package djpon2n.flashcards;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    private SharedPreferences sharedPrefs;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        //TODO populate history here?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
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
                TextView asdf = findViewById(R.id.textView2);
                String path = resultData.getData().getPath();
                asdf.setText(path);

                //add to history
                Set<String> history  = sharedPrefs.getStringSet(getString(R.string.history), new HashSet<String>());
                if (!history.contains(path))
                {
                    history.add(path);
                }

                SharedPreferences.Editor editor = sharedPrefs.edit();



                editor.apply();
            }
            else
            {
                //cry
            }
        }
    }
}