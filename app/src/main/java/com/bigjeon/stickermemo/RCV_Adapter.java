package com.bigjeon.stickermemo;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RCV_Adapter extends RecyclerView.Adapter<RCV_Adapter.ViewHolder> {

    private ArrayList<Memo_Text> mList;
    private Context context;
    private DBHelper dbHelper;

    public RCV_Adapter(ArrayList<Memo_Text> list, Context context){

        this.mList = list;
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public RCV_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_memo, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull RCV_Adapter.ViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        holder.writedate.setText(mList.get(position).getWriteDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView writedate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ListView_Title);
            writedate = itemView.findViewById(R.id.ListView_WriteDate);
            //메모 롱클릭시 삭제하시겠습니까 표출
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int curPos = getAdapterPosition();
                    Memo_Text memo_text = mList.get(curPos);

                    String[] strChoice = {"삭제"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("메모를 삭제하시겠습니까?");
                    builder.setItems(strChoice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            if (position == 0){
                                   String beforeTime = memo_text.getWriteDate();
                                   dbHelper.deleteTodo(beforeTime);
                                   mList.remove(curPos);
                                   notifyItemRemoved(curPos);
                                   Toast.makeText(context,"제거되었습니다.", Toast.LENGTH_LONG).show();
                                   UpdateWidget();
                            }
                        }
                    });
                    builder.show();
                    return false;
                }
            });
            //메모 클릭시 체인지 텍스트 화면으로 이동
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition();
                    Memo_Text memo_text = mList.get(curPos);
                    Intent intent = new Intent(context, Show_Selected_Memo.class);
                    intent.putExtra("id", memo_text.getId());
                    intent.putExtra("title", memo_text.getTitle());
                    intent.putExtra("text", memo_text.getText());
                    intent.putExtra("writeDate", memo_text.getWriteDate());
                    context.startActivity(intent);
                }
            });

        }

    }

    public void addMemo(Memo_Text _memo){
        mList.add(0, _memo);
        notifyItemInserted(0);
    }

    public void UpdateWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, ConfigWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.Memo_List);
    }
}
