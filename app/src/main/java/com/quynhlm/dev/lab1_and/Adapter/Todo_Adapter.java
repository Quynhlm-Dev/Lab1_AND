package com.quynhlm.dev.lab1_and.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.quynhlm.dev.lab1_and.Dao.TodoDao;
import com.quynhlm.dev.lab1_and.MainActivity;
import com.quynhlm.dev.lab1_and.Model.Todo_Model;
import com.quynhlm.dev.lab1_and.R;

import java.util.ArrayList;

public class Todo_Adapter extends RecyclerView.Adapter<Todo_Adapter.TodoViewHolder> {

    Context context;
    ArrayList<Todo_Model> list;

    TodoDao todoDao;

    EditText edttitle, edtcontact, edtdate, edttype, edtid;
    Button btnSubmit;

    public Todo_Adapter(Context context, ArrayList<Todo_Model> list) {
        this.context = context;
        this.list = list;
        todoDao = new TodoDao(context);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todolist, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.txttitle.setText(list.get(position).getTitle());
        holder.txtcontact.setText(list.get(position).getContact());
        holder.txtdate.setText(list.get(position).getDate());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(context, v);
//                MenuInflater inflater = popupMenu.getMenuInflater();
//                inflater.inflate(R.menu.popur_menu, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        int idex = item.getItemId();
//                        if (idex == R.id.menu_Delete) {
//                            DeleteItem(position);
//                            return true;
//                        }
//                        if (idex == R.id.menu_Update) {
//                            UpdateItem(position);
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show(); // Hiển thị menu popup
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txttitle, txtcontact, txtdate;
        ImageView imgclick;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtcontact = itemView.findViewById(R.id.txtcontent);
            txtdate = itemView.findViewById(R.id.txtdate);
            imgclick = itemView.findViewById(R.id.imglick);
            imgclick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ShowPopurMenu(v);
        }

        private void ShowPopurMenu(View v) {
            int position = getAdapterPosition();
            PopupMenu popupMenu = new PopupMenu(context, v);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.popur_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int idex = item.getItemId();
                    if (idex == R.id.menu_Delete) {
                        DeleteItem(position);
                        return true;
                    }
                    if (idex == R.id.menu_Update) {
                        UpdateItem(position);
                        return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }

    private void DeleteItem(int position) {
        Todo_Model todo_model = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure you want to delete this product?");
        builder.setIcon(R.drawable.baseline_delete_24);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean deleteRows = todoDao.delete_data(todo_model.getId());
                if (deleteRows) {
                    Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void UpdateItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.add_data, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        edtid = view.findViewById(R.id.edtid);
        edttitle = view.findViewById(R.id.edttitle);
        edtcontact = view.findViewById(R.id.edtcontact);
        edtdate = view.findViewById(R.id.edtdate);
        edttype = view.findViewById(R.id.edttype);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        //gan gia tri mac dinh
        Todo_Model todo_model = list.get(position);
        edtid.setText(String.valueOf(todo_model.getId()));
        edttitle.setText(todo_model.getTitle());
        edtcontact.setText(todo_model.getContact());
        edtdate.setText(todo_model.getDate());
        edttype.setText(todo_model.getType());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edtid.getText().toString());
                String title = edttitle.getText().toString();
                String contact = edtcontact.getText().toString();
                String date = edtdate.getText().toString();
                String type = edttype.getText().toString();
                if (title.trim().isEmpty() || contact.trim().isEmpty() || date.trim().isEmpty() || type.trim().isEmpty()) {
                    Toast.makeText(context, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Todo_Model todo_model = new Todo_Model(id, title, contact, date, type);
                    if (todoDao.update_data(todo_model)) {
                        Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show();
                        list.set(position, todo_model);
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Update not successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
}
