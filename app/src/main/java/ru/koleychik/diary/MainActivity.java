package ru.koleychik.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;
    final String isPassword = "PASSWORD";
    final String isForget = "FORGET";
    final String isFirst = "IS COME FIRST";
    final String SECRET_WORD = "SECRET_WORD";
    Spinner spinner_forgetPasswordFirst;

    String[] forget_password_mas = {"nazwisko panieńskie matki", "miasto rodzinne", "nazwisko przyiaciela", "przyjęcie urodzinowe"};
    String pass;
    String sc_hint;
    String sc_word;

    Button btnMakeDialogFirst, btnMakeDialogSecond, btnMainContinue, btnForgetPassword;
    TextView textPassword, passwordIsntEquals, forgetPassword, textIsNotPassword, textForgetPassword, textIsNotSecretWord;
    EditText password1, password2, edit_text_forget_word_first, ET_forgetPassword;

    Dialog dialog_first_write_password, dialog_first_forget_password, dialogForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPassword = findViewById(R.id.textPassword);
        forgetPassword = findViewById(R.id.forgetPassword);
        btnMainContinue = findViewById(R.id.buttonMainContinue);
        textIsNotPassword = findViewById(R.id.textViewIsNotPassword);


        sPref = getPreferences(MODE_PRIVATE);
        String firstTime = sPref.getString(isFirst, null);
        pass = sPref.getString(isPassword, null);
        sc_hint = sPref.getString(SECRET_WORD, null);
        sc_word = sPref.getString(isForget, null);
        if(firstTime == null){
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(isFirst, "1");
            ed.apply();
            Toast.makeText(MainActivity.this, "first time", Toast.LENGTH_LONG).show();
            makeDialogFirstForgetPassword();
            makeDialogFirstWritePassword();
        }
        Toast.makeText(MainActivity.this, sc_word, Toast.LENGTH_LONG).show();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonMainContinue:
                        if(pass.equals(textPassword.getText().toString())){
                            Intent intent = new Intent(MainActivity.this, main_procses.class);
                            startActivity(intent);
                        }
                        else{
                            textIsNotPassword.setVisibility(View.VISIBLE);
                            textPassword.setText("");
                        }
                        break;
                    case R.id.forgetPassword:
                        makeDialogForgetPassword();
                }
            }
        };
        btnMainContinue.setOnClickListener(onClickListener);
        forgetPassword.setOnClickListener(onClickListener);
    }

    void  makeDialogForgetPassword(){
        dialogForgetPassword = new Dialog(this);
        dialogForgetPassword.setContentView(R.layout.forget_password);
        btnForgetPassword = dialogForgetPassword.findViewById(R.id.buttonContinueForgetPassword);
        textForgetPassword = dialogForgetPassword.findViewById(R.id.textForgetPassword);
        textIsNotSecretWord = dialogForgetPassword.findViewById(R.id.textIsNotSecretWord);
        ET_forgetPassword = dialogForgetPassword.findViewById(R.id.edit_textForgetPassword);

        textForgetPassword.setText("podpowiedź \" " + sc_hint + " \"");
        dialogForgetPassword.show();

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sc_word.equals(ET_forgetPassword.getText().toString())){
                    dialogForgetPassword.cancel();
                    makeDialogFirstWritePassword();
                }
                else{
                    textIsNotSecretWord.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void makeDialogFirstWritePassword(){
        dialog_first_write_password = new Dialog(this);
        dialog_first_write_password.setContentView(R.layout.dialog_password);

        password1 = dialog_first_write_password.findViewById(R.id.edit_text_passwordFirst);
        password2 = dialog_first_write_password.findViewById(R.id.edit_text_passwordReplaceFirst);

        passwordIsntEquals = dialog_first_write_password.findViewById(R.id.textViewPasswordIsntEquals);

        btnMakeDialogFirst = dialog_first_write_password.findViewById(R.id.buttonContinuePasswordFirst);

        dialog_first_write_password.show();
        btnMakeDialogFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_pass_1 = password1.getText().toString();
                String str_pass_2 = password2.getText().toString();
                if (!str_pass_1.equals(str_pass_2)){
                    passwordIsntEquals.setText(R.string.passworDontEquals);
                    passwordIsntEquals.setVisibility(View.VISIBLE);
                    password1.setText("");
                    password2.setText("");
                }
                else if (str_pass_1.equals("")){
                    passwordIsntEquals.setText(R.string.passworEmpty);
                    passwordIsntEquals.setVisibility(View.VISIBLE);
                    password1.setText("");
                    password2.setText("");
                }
                else{
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString(isPassword, str_pass_1);
                    ed.apply();
                    Toast.makeText(MainActivity.this, str_pass_1, Toast.LENGTH_LONG).show();
                    pass = str_pass_1;
                    dialog_first_write_password.cancel();
                }
            }
        });
    }

    void makeDialogFirstForgetPassword(){
        dialog_first_forget_password = new Dialog(this);
        dialog_first_forget_password.setContentView(R.layout.dialog_forget_password_first);
        edit_text_forget_word_first = dialog_first_forget_password.findViewById(R.id.edit_text_secret_word_first);
        btnMakeDialogSecond = dialog_first_forget_password.findViewById(R.id.buttonContinueForgetPasswordFirst);
        spinner_forgetPasswordFirst = dialog_first_forget_password.findViewById(R.id.spinner_forget_password);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, forget_password_mas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_forgetPasswordFirst.setAdapter(adapter);

        dialog_first_forget_password.show();

        btnMakeDialogSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = spinner_forgetPasswordFirst.getSelectedItem().toString();
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SECRET_WORD, word);
                ed.putString(isForget, edit_text_forget_word_first.getText().toString());
                ed.apply();
                dialog_first_forget_password.cancel();
                Intent intent = new Intent(MainActivity.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

}
