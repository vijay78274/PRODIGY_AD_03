package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmMap;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    RealmResults<Note> notelist;

    public MyAdapter(Context context, RealmResults<Note> notelist) { // constructor
        this.context = context;
        this.notelist = notelist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = notelist.get(position);
        assert note != null;
        holder.txttitle.setText(note.getTittle());
        holder.txtcontent.setText(note.getContent());
        String mytime = DateFormat.getDateInstance().format(note.time);
        holder.txtdate.setText(mytime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenu().add("Delete");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Delete")){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{ //finding views id

        TextView txttitle;
        TextView txtcontent;
        TextView txtdate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtcontent = itemView.findViewById(R.id.txtcontent);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtdate = itemView.findViewById(R.id.txtdate);
        }
    }
}
