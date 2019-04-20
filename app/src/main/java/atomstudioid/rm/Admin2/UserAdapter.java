package atomstudioid.rm.Admin2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import atomstudioid.rm.R;

public class UserAdapter extends ArrayAdapter<User>
{
    HashMap<Integer, Drawable> gender_icon;
    public UserAdapter(Context ctx, ArrayList<User> users){
        super(ctx, 0, users);
        gender_icon=new HashMap<Integer, Drawable>();
        gender_icon.put(R.id.form_gender_female, ctx.getDrawable(R.drawable.ic_gender_female));
        gender_icon.put(R.id.form_gender_male, ctx.getDrawable(R.drawable.ic_gender_male));
    }
    public static class ViewHolder{
        TextView nama;
        TextView alamat;
        ImageView gender;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User pengguna=getItem(position);
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            holder.nama=(TextView) convertView.findViewById(R.id.nama);
            holder.alamat=(TextView) convertView.findViewById(R.id.alamat);
            holder.gender=(ImageView) convertView.findViewById(R.id.gender);
            convertView.setTag(holder);
        }else{
            holder=(UserAdapter.ViewHolder) convertView.getTag();
        }
        holder.nama.setText(pengguna.getNama());
        holder.alamat.setText(pengguna.getAlamat());
        holder.gender.setImageDrawable(gender_icon.get(pengguna.getGender()));
        return convertView;
    }
}

