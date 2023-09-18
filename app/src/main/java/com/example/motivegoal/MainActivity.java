package com.example.motivegoal;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AllGoalItem> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>(); // Arraylist로 초기화

        // CustomAdapter 초기화
        adapter = new CustomAdapter(this, arrayList);
        // RecyclerView에 어댑터 설정
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("AllGoalItem");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("FirebaseData", "DataSnapshot: " + dataSnapshot.toString());
                // 데이터베이스에서 가져온 데이터를 초기화
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AllGoalItem item = snapshot.getValue(AllGoalItem.class);
                    item.setKey(snapshot.getKey()); // Firebase 키를 설정
                    arrayList.add(item); // 담은 데이터를 배열리스트에 넣고 뷰로 보낼 준비
                }

                // 어댑터에게 데이터가 변경되었음을 알립니다.
                adapter.setItems(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        // 나머지 코드...
        ImageButton moveButton = findViewById(R.id.imageButton2);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, plusbuttonpg.class);
                intent.putExtra("parentActivity", "MainActivity");
                startActivity(intent);
            }
        });

        Button goalTodayButton = findViewById(R.id.goal_today_btn);
        goalTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
            }
        });

        Button goalAllButton = findViewById(R.id.goal_all_btn);
        goalAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MainActivity로 다시 넘어가지 않고 현재 Activity를 유지합니다.
            }
        });

        // ItemTouchHelper 등록 코드는 그대로 사용 가능합니다.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}













