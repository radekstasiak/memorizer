package radev.com.memorizer;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import radev.com.memorizer.apiTranslator.ApiTranslatorService;
import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import radev.com.memorizer.databinding.ActivityDashboardBinding;
import radev.com.memorizer.model.Translation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements Callback<String> {
    RecyclerView mRecycler;
    ActivityDashboardBinding binding;
    WordHistoryListAdapter mAdapter;

    @Inject
    ApiTranslatorService mApiService;

    @Inject
    Settings mSettings;

    TextView tv;
    EditText mProvideWordEt;
    Button mNextBtn;

    static List<Translation> wordsMap = new ArrayList<Translation>();
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmMgr = (AlarmManager) getApplication().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplication(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplication(), 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, currentTime.getHours());
        calendar.set(Calendar.MINUTE, currentTime.getMinutes());
        calendar.set(Calendar.SECOND, currentTime.getSeconds() + 30);

        Toast.makeText(this, calendar.getTime().toString(), Toast.LENGTH_SHORT).show();

        int intervalMillis = 1000 * 60 * 60 * 8; //5 sec
        //int intervalMillis = 1000 * 60; //5 sec
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intervalMillis, alarmIntent);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        ButterKnife.bind(this);
        MemorizerApp.getInstance().getComponent().inject(this);

        mRecycler = binding.recyclerView;
        mProvideWordEt = binding.enterWordEt;
        mNextBtn = binding.nextBtn;

        tv = binding.textView;
        tv.setText(mSettings.getUrl());
        mAdapter = new WordHistoryListAdapter(mSettings);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        wordsMap = mSettings.getTranslationHistory();
        mAdapter.setData(wordsMap);

        mNextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<String> optonParams = new ArrayList<String>();
                optonParams.add("t");
                //optonParams.add("at");
                // optonParams.add("rm");
                optonParams.add("bd");
                // optonParams.add("md");
                // optonParams.add("ss");
                // optonParams.add("ex");
                //  optonParams.add("rw");
                Call<String> callback2 = mApiService.getFullTranslation("gtx", "en", "pl", optonParams, mProvideWordEt.getText().toString());
                // callback.enqueue(Dashboard.this);
                callback2.enqueue(Dashboard.this);
            }
        });


    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        response.body();

        try {
            JSONArray obja = new JSONArray(response.body());
            JSONArray array = (JSONArray) ((JSONArray) ((JSONArray) obja.get(1)).get(0)).get(1);
            List<String> translationList = new ArrayList<String>();
            Translation translation = new Translation();
            translation.setSource(mProvideWordEt.getText().toString());
            translation.setTimestamp(System.currentTimeMillis());
            for (int i = 0; i < array.length(); i++) {
                translationList.add((String) array.get(i));
            }
            translation.setTranslationList(translationList);
            wordsMap.add(translation);
            mAdapter.setData(wordsMap);
            mSettings.saveTranslationHistory(wordsMap);
            mProvideWordEt.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.d("FAILURE", t.getMessage());
    }
}
