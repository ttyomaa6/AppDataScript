package com.example.appdatascript;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Miner> weakMinersArraylist = new ArrayList<Miner>();
    ArrayList<Miner> averageMinersArraylist = new ArrayList<Miner>();
    ArrayList<Miner> strongMinersArraylist = new ArrayList<Miner>();
    private FirebaseModel firebaseModel = new FirebaseModel();
    public DatabaseReference reference;
    TextView textReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        firebaseModel.initAll();
        textReset=findViewById(R.id.textReset);
        textReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEverydayMining();
            }
        });
//        updateClothes();
//        firebaseModel.getReference().child("users")
//                .child("tyomaa6").child("nontifications")
//                .child("-MxuHf_f26Lr39Vx2Tx8").setValue(new Nontification("tyomaa6","не отправлено","запросодежда"
//                ,"","Jordan 4","https://firebasestorage.googleapis.com/v0/b/schooly-47238.appspot.com/o/clothes%2Fjordan.jpg?alt=media&token=823b2a10-1dcd-47c5-8170-b5a4fb155500",
//                "не просмотрено","-MxuHf_f26Lr39Vx2Tx8",0));
//        loadModelInBase();
//        InitWeakMiners();
//        InitAverageMiners();
//        InitStrongMiners();
//        SendDataInBase();
    }


    public void InitWeakMiners(){
        weakMinersArraylist.add(new Miner(5, 0, 120));
        weakMinersArraylist.add(new Miner(7, 0, 140));
        weakMinersArraylist.add(new Miner(9, 0, 165));
        weakMinersArraylist.add(new Miner(11, 0, 180));
        weakMinersArraylist.add(new Miner(13, 0, 200));
    }

    public void InitAverageMiners(){
        averageMinersArraylist.add(new Miner(20, 0, 300));
        averageMinersArraylist.add(new Miner(22, 0, 320));
        averageMinersArraylist.add(new Miner(26, 0, 340));
        averageMinersArraylist.add(new Miner(30, 0, 365));
        averageMinersArraylist.add(new Miner(32, 0, 380));
    }

    public void InitStrongMiners(){
        strongMinersArraylist.add(new Miner(36, 0, 420));
        strongMinersArraylist.add(new Miner(40, 0, 450));
        strongMinersArraylist.add(new Miner(42, 0, 475));
        strongMinersArraylist.add(new Miner(46, 0, 500));
        strongMinersArraylist.add(new Miner(50, 0, 525));
    }

    public void SendDataInBase(){
        firebaseModel.getReference("AppData/AllMiners/Weak").setValue(weakMinersArraylist);
        firebaseModel.getReference("AppData/AllMiners/Average")
                .setValue(averageMinersArraylist);
        firebaseModel.getReference("AppData/AllMiners/Strong")
                .setValue(strongMinersArraylist);
    }

    public void loadModelInBase(){
        ArrayList<Clothes> allClothes=new ArrayList<>();
//        allClothes.add(new Clothes("shoes", "https://firebasestorage.googleapis.com/v0/b/schooly-47238.appspot.com/o/clothes%2Fjordan.jpg?alt=media&token=823b2a10-1dcd-47c5-8170-b5a4fb155500"
//                , 100,"Jordan 1"));
        firebaseModel.getReference().child("AppData").child("Clothes").child("Shoes").setValue(allClothes);
        firebaseModel.getReference().child("AppData").child("Clothes").child("Clothes");
        firebaseModel.getReference().child("AppData").child("Clothes").child("Hats");
        firebaseModel.getReference().child("AppData").child("Clothes").child("Accessories");
        firebaseModel.getReference().child("AppData").child("Clothes").child("Popular");
    }

    public void updateClothes(){
        firebaseModel.getReference().child("AppData").child("Clothes").child("AllClothes")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot=task.getResult();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Clothes clothes = new Clothes();
                        clothes.setPurchaseToday(snap.child("purchaseToday").getValue(Long.class));
                        clothes.setCurrencyType(snap.child("currencyType").getValue(String.class));
                        clothes.setCreator(snap.child("creator").getValue(String.class));
                        clothes.setClothesPrice(snap.child("clothesPrice").getValue(Long.class));
                        clothes.setUid(snap.child("uid").getValue(String.class));
                        if(clothes.getCurrencyType().equals("dollar")){

                        }else {
                            firebaseModel.getUsersReference().child(clothes.getCreator())
                                    .child("money").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DataSnapshot snapshot1=task.getResult();
                                        Money s=new Money();
                                        s.setMoney(snapshot1.getValue(Long.class));
                                        firebaseModel.getUsersReference().child(clothes.getCreator())
                                                .child("money").setValue(clothes.getPurchaseToday()*clothes.getClothesPrice()+s.getMoney());
                                        String uid=firebaseModel.getUsersReference().child(clothes.getCreator())
                                                .child("nontifications").push().getKey();
                                        firebaseModel.getUsersReference().child(clothes.getCreator())
                                                .child("nontifications").child(uid)
                                                .setValue(new Nontification(clothes.getCreator(),"не отправлено","одеждаприбыль"
                                                        ,""," "," ","не просмотрено",uid,clothes.getPurchaseToday()*clothes.getClothesPrice()));
                                        firebaseModel.getReference().child("AppData").child("Clothes").child("AllClothes")
                                                .child(clothes.getUid()).child("purchaseToday").setValue(0);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    public void addEverydayMining(){
        firebaseModel.getUsersReference().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot=task.getResult();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        UserInformation userData = new UserInformation();
                        userData.setTodayMining(snap.child("todayMining").getValue(Double.class));
                        userData.setNick(snap.child("nick").getValue(String.class));
                        userData.setmoney(snap.child("money").getValue(Long.class));
                        userData.setMiningPremium(snap.child("miningPremium").getValue(Long.class));
                        if(userData.getMiningPremium()==0){
                            firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("money").setValue(userData.getmoney()+userData.getTodayMining());
                            String uid=firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("nontifications").push().getKey();
                            firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("nontifications").child(uid)
                                    .setValue(new Nontification(userData.getNick(),"не отправлено","майнинг"
                                            ,""," "," ","не просмотрено",uid,userData.getTodayMining()));
                            firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("todayMining").setValue(0);
                        }else{
                            firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("money").setValue(userData.getmoney()+userData.getTodayMining()*userData.getMiningPremium());
                            String uid=firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("nontifications").push().getKey();
                            firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("nontifications").child(uid)
                                    .setValue(new Nontification(userData.getNick(),"не отправлено","майнинг"
                                            ,""," "," ","не просмотрено",uid,userData.getTodayMining()*userData.getMiningPremium()));
                            firebaseModel.getUsersReference().child(userData.getNick())
                                    .child("todayMining").setValue(0);
                        }

                    }
                }
            }
        });

    }
}