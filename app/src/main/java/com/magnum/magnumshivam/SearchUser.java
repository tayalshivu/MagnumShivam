package com.magnum.magnumshivam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchUser extends AppCompatActivity {

    EditText name;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        name=findViewById(R.id.text);
        search=findViewById(R.id.button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = name.getText().toString().trim();
                Intent intent = new Intent(SearchUser.this,MainActivity.class);
                intent.putExtra("name",s);
                startActivity(intent);
            }
        });
    }
}