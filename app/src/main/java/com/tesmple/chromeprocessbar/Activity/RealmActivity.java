package com.tesmple.chromeprocessbar.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tesmple.chromeprocessbar.R;
import com.tesmple.chromeprocessbar.RealmJaveBean.People;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by ESIR on 2016/1/2.
 */
public class RealmActivity extends AppCompatActivity {
    /**
     * 显示人员的textview
     */
    private TextView tvPeople;

    private String peopleInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        tvPeople = (TextView)findViewById(R.id.tv_people);
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder(getApplicationContext()).name("Peopel.realm").build());
        beginRealmTransaction(realm);
        requryFromRealm(realm);
    }

    private void beginRealmTransaction(Realm realm){
        /*realm.beginTransaction();
        People yasic = realm.createObject(People.class);
        yasic.setName("Yasic");
        yasic.setAge("20");
        yasic.setSex("Male");
        realm.commitTransaction();*/

        /*People esir = new People("Esir", "famale", "19");
        realm.beginTransaction();
        realm.copyToRealm(esir);
        realm.commitTransaction();*/


        realm.executeTransaction(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        People yasic = realm.createObject(People.class);
                        yasic.setName("Yasic");
                        yasic.setAge("20");
                        yasic.setSex("Male");
                    }},
                new Realm.Transaction.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i("error",e.toString());
                    }
                });
    }

    private void requryFromRealm(Realm realm){
        RealmResults<People> results1 =
                realm.where(People.class).findAll();

        if(results1.size() == 0) {
            peopleInfo = "Target not found!";
        }else {
            for (People c : results1) {
                Log.d("results", c.getName());
                peopleInfo = peopleInfo + c.getName() + "-" + c.getSex() + "-" + c.getAge() + "\n";
            }
        }
        tvPeople.setText(peopleInfo);
    }
}

