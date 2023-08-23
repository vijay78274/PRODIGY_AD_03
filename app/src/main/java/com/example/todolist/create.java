package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        EditText title = findViewById(R.id.edtittle);
        EditText content = findViewById(R.id.edcontent);
        Button add = findViewById(R.id.myadd);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mytitle = title.getText().toString();
                String mycontent = content.getText().toString();
                long time = System.currentTimeMillis();

                realm.beginTransaction();
                Note note = realm.createObject(Note.class);
                note.setTittle(mytitle);
                note.setContent(mycontent);
                note.setTime(time);
                realm.commitTransaction();
                Toast.makeText(create.this,"Created",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}