package com.example.app_quan_l_danhsach;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.UserAdapter;
import database.UserDatabase;
import model.User;

public class MainActivity extends AppCompatActivity {
    private EditText edtname;
    private EditText edtaddress,edtsearch,edtyear;
    private TextView txtdeleteall;
    private Button btnclickadd;
    private RecyclerView rcvitem;
    private UserAdapter mUserAdapter;
    private List<User> mUserList;
    private final int Requetcode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initid();

        mUserAdapter=new UserAdapter(new UserAdapter.undatedata() {
            @Override
            public void clickupdate(User user) {
                updateuser(user);
            }

            @Override
            public void deletedata(User user) {
                deleteuser(user);

            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcvitem.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvitem.addItemDecoration(itemDecoration);

        mUserList=new ArrayList<>();
        mUserAdapter.setdata(mUserList);
        rcvitem.setAdapter(mUserAdapter);

        btnclickadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddatauser();
            }
        });

        edtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_SEARCH){
                    searchuser();
                }

                return false;
            }
        });

        txtdeleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteall();
            }
        });





        loaddata();
    }
    private void loaddata() {
        mUserList=UserDatabase.getinstance(this).mUserDAO().getuserlist();
        mUserAdapter.setdata(mUserList);
    }


    private void initid() {
        edtname= (EditText) findViewById(R.id.edtname);
        edtaddress=(EditText) findViewById(R.id.edtaddress);
        btnclickadd=(Button) findViewById(R.id.btnadd);
        rcvitem=(RecyclerView) findViewById(R.id.rcvitem);
        edtyear=(EditText) findViewById(R.id.edtyear);
        edtsearch=(EditText) findViewById(R.id.edtsearch);
        txtdeleteall=(TextView) findViewById(R.id.deleteall);
    }


    private boolean isuserexist(User user){   //checktrung
        List<User> users;
        users= UserDatabase.getinstance(this).mUserDAO().checkuser(user.getName());
        return users!=null&&!users.isEmpty();

    }
    private void tatbanphim() {

        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    private void adddatauser() {
        String name=edtname.getText().toString().trim();
        String address=edtaddress.getText().toString().trim();
        String year=edtyear.getText().toString().trim();

        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(address)||TextUtils.isEmpty(year)){
            return;
        }

        User user=new User(name,address,year);
        if(isuserexist(user)){
            Toast.makeText(this, "User exist", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDatabase.getinstance(this).mUserDAO().inserttuser(user);
        Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();

        edtname.setText("");
        edtaddress.setText("");
        edtyear.setText("");



        tatbanphim();
        loaddata();
    }
    private void updateuser(User user) {
        Intent intent=new Intent(MainActivity.this,Update_data.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_user",user);
        intent.putExtras(bundle);
        startActivityForResult(intent,Requetcode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Requetcode&& resultCode==Activity.RESULT_OK){
            loaddata();
        }
    }

    private void  deleteuser(User user){

        new AlertDialog.Builder(this).setTitle("Confilm delete user")
                .setMessage("Are you sure")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        UserDatabase.getinstance(MainActivity.this).mUserDAO().deleteuser(user);
                        Toast.makeText(MainActivity.this, "delete user", Toast.LENGTH_SHORT).show();
                        loaddata();
                    }
                })
                .setNegativeButton("no",null).show();

    }
    private void searchuser() {
        String search =edtsearch.getText().toString().trim();

        mUserList=new ArrayList<>();
        mUserList=UserDatabase.getinstance(MainActivity.this).mUserDAO().searchuser(search);
        mUserAdapter.setdata(mUserList);
        tatbanphim();
    }
    private void deleteall() {
        new AlertDialog.Builder(this).setTitle("Confilm delete user")
                .setMessage("Are you sure")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        UserDatabase.getinstance(MainActivity.this).mUserDAO().deleteAll();
                        Toast.makeText(MainActivity.this, "delete all user", Toast.LENGTH_SHORT).show();
                        loaddata();
                    }
                })
                .setNegativeButton("no",null).show();

    }

}