package ru.koleychik.diary.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.koleychik.diary.DatabaseHelper;
import ru.koleychik.diary.R;
import ru.koleychik.diary.ui.write.writeFragment;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.NumberViewHolder> {

    List<String> nameList = new ArrayList<>();
    List<String>textList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    Context context;

    public void getContext(Context context_main) {
        context = context_main;
        getListString();
    }

    void getListString() {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_TEXT, null,null,null,null,null,null);
        while (cursor.moveToNext()){
            textList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT)));
            nameList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_THEME)));
        }
        cursor.close();
    }

    @NonNull
    @Override
    public NumbersAdapter.NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_all_words, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumbersAdapter.NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return textList.size();
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {
        TextView tv_num, tv_name, tv_text;
        ImageView imgThreeDots;
        Dialog dialog = new Dialog(context);
        Button btnChange, btnDelete;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            Toast toast = Toast.makeText(context, String.valueOf(textList.size()), Toast.LENGTH_SHORT);
            toast.show();
            tv_num = itemView.findViewById(R.id.textViewNumberItem);
            tv_name = itemView.findViewById(R.id.textViewNameItem);
            tv_text = itemView.findViewById(R.id.textViewTextItem);
            imgThreeDots = itemView.findViewById(R.id.imageThreeDots);
            dialog.setContentView(R.layout.dialog_all_actions);
            btnChange = dialog.findViewById(R.id.buttonDialogChange);
            btnDelete = dialog.findViewById(R.id.buttonDialogDelete);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.buttonDialogChange:
                            Intent intent = new Intent(context, writeFragment.class);
                            context.startActivity(intent);
                            dialog.cancel();
                            break;
                    }
                }
            };
            btnChange.setOnClickListener(onClickListener);
            btnDelete.setOnClickListener(onClickListener);

            imgThreeDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                }
            });
        }

        void bind(int listIndex){
            tv_num.setText(String.valueOf(listIndex + 1));
            tv_name.setText(nameList.get(listIndex));
            tv_text.setText(textList.get(listIndex));
        }
    }
}
