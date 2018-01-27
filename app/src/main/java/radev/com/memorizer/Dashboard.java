package radev.com.memorizer;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import radev.com.memorizer.apiTranslator.ApiTranslatorService;
import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import radev.com.memorizer.databinding.ActivityDashboardBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements Callback<String> {


    List<String> mData = Arrays.asList("Hello", "world");
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
    static HashMap<String, List<String>> wordsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        ButterKnife.bind(this);
        MemorizerApp.getInstance().getComponent().inject(this);

        mRecycler = binding.recyclerView;
        mProvideWordEt = binding.enterWordEt;
        mNextBtn = binding.nextBtn;

        tv = binding.textView;
        tv.setText(mSettings.getUrl());
        mAdapter = new WordHistoryListAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);


        //Call<String> callback = mApiService.getSimpleTranslation("gtx","en","pl","t",mProvideWordEt.getText().toString());


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
                Call<String> callback2 = mApiService.getFullTranslation("gtx","en","pl",optonParams,mProvideWordEt.getText().toString());
                // callback.enqueue(Dashboard.this);
                callback2.enqueue(Dashboard.this);
            }
        });



    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        response.body();

        try {
            JSONArray obja= new JSONArray(response.body());

            JSONArray array = (JSONArray) ((JSONArray)((JSONArray)obja.get(1)).get(0)).get(1);
            List<String> translationList = new ArrayList<String>();
            for(int i=0; i<array.length();i++){
                //JSONObject jsonObject = (JSONObject) array.get(i);
                translationList.add((String) array.get(i));

            }
            wordsMap.put(mProvideWordEt.getText().toString(),translationList);
            mAdapter.setData(new ArrayList<String>(wordsMap.keySet()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.d("FAILURE",t.getMessage());
    }
}
