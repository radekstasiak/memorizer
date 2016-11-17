package radev.com.memorizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import radev.com.memorizer.apiTranslator.ApiTranslatorService;
import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements Callback<String> {
    @Inject
    ApiTranslatorService mApiService;

    @Inject
    Settings mSettings;

    @BindView(R.id.textView)
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        MemorizerApp.getInstance().getComponent().inject(this);
        tv.setText(mSettings.getUrl());

        Call<String> callback = mApiService.getSimpleTranslation("gtx","en","pl","t","build");
        List<String> optonParams = new ArrayList<String>();
        optonParams.add("t");
        optonParams.add("bd");

        Call<String> callback2 = mApiService.getFullTranslation("gtx","en","pl",optonParams,"build");
        callback.enqueue(Dashboard.this);
        callback2.enqueue(Dashboard.this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        response.body();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.d("FAILURE",t.getMessage());
    }
}
