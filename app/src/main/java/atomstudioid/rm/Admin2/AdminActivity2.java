package atomstudioid.rm.Admin2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import atomstudioid.rm.R;

public class AdminActivity2 extends AppCompatActivity {

    ListView list;
    Button addbtn;
    UserAdapter adapter;
    ArrayList<User> users;
    String initialTitle;
    List<String> keyArray = new ArrayList<String>();

    Firebase mFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_admin2);


        try{
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        }catch(Exception e){}
        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://chatc2.firebaseio.com").child("user");
        setContentView(R.layout.activity_main);
        list=(ListView) findViewById(R.id.list);
        addbtn=(Button) findViewById(R.id.addbtn);
        users=new ArrayList<User>();
        adapter=new UserAdapter(this, users);
        list.setAdapter(adapter);
        addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1) {
                formulir(null, -1);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int itempos, long p4) {
                formulir((User) p1.getItemAtPosition(itempos), itempos);
            }
        });
        // firebase data listener
        mFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                keyArray.add(dataSnapshot.getKey());
                users.add(user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot p1, String p2) {
                User u=p1.getValue(User.class);
                users.set(keyArray.indexOf(p1.getKey()), u);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot p1) {
                users.remove(keyArray.indexOf(p1.getKey()));
                keyArray.remove(p1.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot p1, String p2) {
                // TODO: Implement this method
            }

            @Override
            public void onCancelled(FirebaseError p1) {
                // TODO: Implement this method
            }
        });
    }
    private void formulir(final User inputuser, final int pos){
        View v = LayoutInflater.from(this).inflate(R.layout.form_dialog, null);
        final EditText nama = (EditText) v.findViewById(R.id.form_nama);
        final EditText alamat = (EditText) v.findViewById(R.id.form_alamat);
        final RadioGroup gender=(RadioGroup) v.findViewById(R.id.form_gender);
        if(inputuser!=null){
            nama.setText(inputuser.getNama());
            alamat.setText(inputuser.getAlamat());
            gender.check(inputuser.getGender());
            initialTitle="Perbarui";
        }else{
            initialTitle="Tambahkan";
        }
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(initialTitle+" Pengguna");
        dlg.setView(v);
        dlg.setPositiveButton(initialTitle, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface p1, int p2) {
                if(nama.getText().toString().length()<2||alamat.getText().toString().length()<2){
                    Toast.makeText(AdminActivity2.this, "Data tidak valid", Toast.LENGTH_LONG).show();
                    return;
                }

                User user=new User(nama.getText().toString(), gender.getCheckedRadioButtonId(), alamat.getText().toString());
                if(inputuser==null){
                    mFirebase.push().setValue(user);
                }else{
                    mFirebase.child(keyArray.get(pos)).setValue(user);
                }

                Toast.makeText(AdminActivity2.this, "Pengguna berhasil di"+(initialTitle.toLowerCase()), Toast.LENGTH_SHORT).show();
            }
        });
        if(inputuser!=null){
            dlg.setNeutralButton("Hapus", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface p1, int p2) {
                    mFirebase.child(keyArray.get(pos)).removeValue();
                }
            });
        }
        dlg.setNegativeButton("Batal", null);
        dlg.show();
    }

    @Override
    protected void onDestroy() {
        mFirebase=null;
        super.onDestroy();
    }

}
