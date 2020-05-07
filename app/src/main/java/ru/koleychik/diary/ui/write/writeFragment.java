package ru.koleychik.diary.ui.write;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ru.koleychik.diary.DatabaseHelper;
import ru.koleychik.diary.R;

public class writeFragment extends Fragment {

    private writeViewModel galleryViewModel;
    EditText ET_head, ET_text;
    TextView TV_error;
    Button btnwrite;
    int id;

    Context context;

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(writeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_write, container, false);

        ET_head = root.findViewById(R.id.edit_text_activity_write1);
        ET_text = root.findViewById(R.id.edit_text_activity_write2);
        btnwrite = root.findViewById(R.id.buttonWrite);
        TV_error = root.findViewById(R.id.textViewError);

        context = getActivity();

        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

//        id = getid();

        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                String head_text = ET_head.getText().toString();
                String text_text = ET_text.getText().toString();
                cv.put(DatabaseHelper.COLUMN_THEME, head_text);
                cv.put(DatabaseHelper.COLUMN_TEXT, text_text);
                if(head_text.equals("") && text_text.equals("")){
                    TV_error.setVisibility(View.VISIBLE);
                }
                else {
                    sqLiteDatabase.insert(DatabaseHelper.TABLE_TEXT, null, cv);
                    ET_head.setText("");
                    ET_text.setText("");
                    TV_error.setVisibility(View.GONE);
                }
            }
        });

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_TEXT, null,null,null,null,null,null);
        if(cursor.moveToLast()){
            String str = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.show();
        }
        cursor.close();
        return root;
    }
}
