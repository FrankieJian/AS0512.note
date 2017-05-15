package com.example.g572_528r.as0512note;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.g572_528r.as0512note.date.Note;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddActivity extends AppCompatActivity {
    private Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if(BmobUser.getCurrentUser() == null){
            startActivity(new Intent(AddActivity.this, LoginActivity.class));
            return;
        }

        btnAdd = (Button) findViewById(R.id.btn_save);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd.setClickable(false);
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"107599.jpg";
                final BmobFile imgFile = new BmobFile(new File(filePath));
                imgFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            saveNote(imgFile);
                        }else{
                            btnAdd.setClickable(true);
                        }
                    }
                });
            }
        });
    }

    private void saveNote(BmobFile imgFile) {
        Note note = new Note();
        note.setTitle("记事标题");
        note.setContent("15软件技术 Frankie_LiAnG");
        note.setCreateDate("2017-05-12");
        note.setDel(false);
        note.setImg(imgFile);
        String phone = BmobUser.getCurrentUser().getMobilePhoneNumber();
        note.setPhone(phone);
        note.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

                if(e == null){
                    Log.i("ADD", "success");
                }else{
                    Log.i("ADD", "fail " + e.getMessage());
                }
            }
        });
    }
}
