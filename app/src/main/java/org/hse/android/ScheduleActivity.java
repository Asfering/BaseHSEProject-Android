package org.hse.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hse.android.BaseActivity.ScheduleType;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    public static final String ARG_TYPE = "";
    public static final String ARG_MODE = "";
    public static final String ARG_ID = "";
    public static final int DEFAULT_ID = 0;
    private RecyclerView recyclerView;
    ViewHolder.ItemAdapter adapter;
    ScheduleType type;
    BaseActivity.ScheduleMode mode;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);

        type = (ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        mode = (BaseActivity.ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);

        TextView title = findViewById(R.id.titleSchedule);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ViewHolder.ItemAdapter(this::onScheduleItemClick);
        recyclerView.setAdapter(adapter);
        
        initData();


    }

    private void initData() {
        List<SheduleItem> list = new ArrayList<>();
        ScheduleItemHeader header = new ScheduleItemHeader();
        header.setTitle("Понедельник, 28 января");

        SheduleItem item = new SheduleItem();
        item.setStart("10:00");
        item.setEnd("11:00");
        item.setType("Практическое задание");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 503, Кочновской пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);

        item = new SheduleItem();
        item.setStart("12:00");
        item.setEnd("13:00");
        item.setType("Практическое задание");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 503, Кочновской пр-д, д.3");
        item.setTeacher("Пред. Гущим Михаил Иванович");
        list.add(item);
        adapter.setDataList(list);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private OnItemClick onItemClick;
        private TextView start;
        private TextView end;
        private TextView type;
        private TextView name;
        private TextView place;
        private TextView teacher;

        public ViewHolder(View itemView, Context context, OnItemClick onItemClick){
            super(itemView);
            this.context = context;
            this.onItemClick = onItemClick;
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);
            place = itemView.findViewById(R.id.place);
            teacher = itemView.findViewById(R.id.teacher);
        }

        public void bind(final SheduleItem data) {
            start.setText(data.getStart());
            end.setText(data.getEnd());
            type.setText(data.getType());
            name.setText(data.getName());
            place.setText(data.getPlace());
            teacher.setText(data.getTeacher());
        }

        public static class ViewHolderHeader extends RecyclerView.ViewHolder {
            private Context context;
            private OnItemClick onItemClick;
            private TextView title;

            public ViewHolderHeader(View itemView, Context context, OnItemClick onItemClick) {
                super(itemView);
                this.context = context;
                this.onItemClick = onItemClick;
                title = itemView.findViewById(R.id.Title);
            }

            public void bind(final ScheduleItemHeader data) {
                title.setText(data.getTitle());
            }
        }

        public final static class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
            private final static int TYPE_ITEM = 0;
            private final static int TYPE_HEADER = 1;

            private List<SheduleItem> dataList = new ArrayList<>();
            private OnItemClick onItemClick;

            public ItemAdapter(OnItemClick onItemClick) {
                this.onItemClick = onItemClick;
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);

                if (viewType == TYPE_ITEM) {
                    View contactView = inflater.inflate(R.layout.shedule_texts, parent, false);
                    return new ViewHolder(contactView, context, onItemClick);
                } else if (viewType == TYPE_HEADER){
                    View contactView = inflater.inflate(R.layout.schedule_title, parent, false);
                    return new ViewHolder(contactView, context, onItemClick);
                }
                throw new IllegalArgumentException("Invalid view type");
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                SheduleItem data = dataList.get(position);
                if(holder instanceof ViewHolder){
                    ((ViewHolder) holder).bind(data);
                } else if (holder instanceof ViewHolderHeader) {
                    ((ViewHolderHeader) holder).bind((ScheduleItemHeader) data);
                }
            }

            public int getItemViewType (int position){
                SheduleItem data = dataList.get(position);
                if (data instanceof ScheduleItemHeader) {
                    return TYPE_HEADER;
                }
                return TYPE_ITEM;
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        }
    }


}

