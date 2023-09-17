package com.example.app_quan_l_danhsach;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.UserDatabase;
import model.User;

public class Update_data extends AppCompatActivity {
    private EditText edtname;
    private EditText edtaddress,edtyear;
    private Button btnupdate;
    private User mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        edtname = (EditText) findViewById(R.id.edtname);
        edtaddress = (EditText) findViewById(R.id.edtaddress);
        btnupdate = (Button) findViewById(R.id.btupdatev2);
        edtyear=(EditText) findViewById(R.id.edtyear);


        mUsers = (User) getIntent().getExtras().get("object_user");
        if(mUsers!=null){
            edtname.setText(mUsers.getName());
            edtaddress.setText(mUsers.getAddress());
            edtyear.setText(mUsers.getYear());
        }

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateuser();
            }
        });

    }

    private void updateuser() {
        String name=edtname.getText().toString().trim();
        String address=edtaddress.getText().toString().trim();
        String year=edtyear.getText().toString().trim();

        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(address)||TextUtils.isEmpty(year)){
            return;
        }

        //update

        mUsers.setName(name);
        mUsers.setAddress(address);
        mUsers.setYear(year);

        UserDatabase.getinstance(this).mUserDAO().updateuser(mUsers);

        Toast.makeText(this, "update success", Toast.LENGTH_SHORT).show();

        Intent intenresut=new Intent();
        setResult(Activity.RESULT_OK,intenresut);
        finish();
    }


    }
