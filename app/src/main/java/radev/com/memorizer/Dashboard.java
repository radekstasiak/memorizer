package radev.com.memorizer;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;

import radev.com.memorizer.apiTranslator.ApiTranslatorService;
import radev.com.memorizer.apiTranslator.TimePickerFragment;
import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import radev.com.memorizer.databinding.ActivityDashboardBinding;
import radev.com.memorizer.model.Language;
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

    @Inject
    AlarmScheduler alarmScheduler;

    @Inject
    TimePickerFragment timerPickerFragment;
    TextView tv;

    EditText mProvideWordEt;
    Button mNextBtn;

    static List<Translation> wordsMap = new ArrayList<Translation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        MemorizerApp.getInstance().getComponent().inject(this);


        setSupportActionBar((Toolbar) binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.toolbar.findViewById(R.id.timePickerIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
        ((TextView) binding.toolbar.findViewById(R.id.toolbar_text)).setText(getResources().getString(R.string.app_name));

        mRecycler = binding.recyclerView;
        mProvideWordEt = binding.enterWordEt;
        mNextBtn = binding.nextBtn;
        setupLanguagePickers();

        mAdapter = new WordHistoryListAdapter(mSettings);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        wordsMap = mSettings.getTranslationHistory();
        mAdapter.setData(wordsMap);
        mNextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mProvideWordEt.getText().toString().isEmpty()) {
                    List<String> optonParams = new ArrayList<String>();
                    optonParams.add("t");
                    //optonParams.add("at");
                    // optonParams.add("rm");
                    optonParams.add("bd");
                    // optonParams.add("md");
                    // optonParams.add("ss");
                    // optonParams.add("ex");
                    //  optonParams.add("rw");
                    Language languageFrom = Language.valueOf(binding.languageFrom.getSelectedItem().toString());
                    Language languageTo = Language.valueOf(binding.languageTo.getSelectedItem().toString());
                    mSettings.saveFromToLanguages(languageFrom, languageTo);
                    Call<String> callback2 = mApiService.getFullTranslation("gtx", languageFrom.getLanguageCode(), languageTo.getLanguageCode(), optonParams, mProvideWordEt.getText().toString());
                    // callback.enqueue(Dashboard.this);
                    callback2.enqueue(Dashboard.this);

                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWordExercisePopup();
            }
        });
    }

    private void openWordExercisePopup() {
        if (!wordsMap.isEmpty()) {
            int randomWordPosition = ThreadLocalRandom.current().nextInt(0, wordsMap.size());
            final Translation translation = wordsMap.get(randomWordPosition);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(translation.getSource() + " to " + translation.getLanguageTo().getLanguageCode().toUpperCase());

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Ready!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String answer = input.getText().toString();
                    if (!answer.isEmpty()) {
                        for (String translationWord : translation.getTranslationList()) {
                            if (translationWord.toLowerCase().equals(answer.toLowerCase())) {
                                openWordExerciseResultPopup(translation, true);
                                dialog.cancel();
                                return;
                            }
                        }
                        openWordExerciseResultPopup(translation, false);
                    }
                }
            });
            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }


    private void openWordExerciseResultPopup(Translation translation, boolean isAnswerCorrect) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isAnswerCorrect ? "CORRECT!" : "Whoops...");

        final TextView input = new TextView(this);
        input.setText(translation.getSource() + "\n" + TextUtils.join(", ", translation.getTranslationList()));
        builder.setView(input);
        builder.setPositiveButton("Give me next one!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openWordExercisePopup();
                dialog.cancel();
                return;
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void setupLanguagePickers() {
        Spinner languageFromSpinner = binding.languageFrom;
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Arrays.asList(Language.ENGLISH.name(), Language.DUTCH.name(), Language.POLISH.name()));
        languageFromSpinner.setAdapter(adapterFrom);
        Spinner languageToSpinner = binding.languageTo;
        ArrayAdapter<String> adapterTo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Arrays.asList(Language.POLISH.name(), Language.ENGLISH.name(), Language.DUTCH.name()));
        languageToSpinner.setAdapter(adapterTo);

        restoreLastLanguagesUsed(languageFromSpinner, adapterFrom, languageToSpinner, adapterTo);
    }

    private void restoreLastLanguagesUsed(Spinner languageFromSpinner, ArrayAdapter<String> adapterFrom, Spinner languageToSpinner, ArrayAdapter<String> adapterTo) {
        List<Language> fromToLanguages = mSettings.getFromToLanguages();
        if (!fromToLanguages.isEmpty()) {
            int positionFromLanguage = adapterFrom.getPosition(fromToLanguages.get(0).name());
            languageFromSpinner.setSelection(positionFromLanguage);
            int positionToLanguage = adapterTo.getPosition(fromToLanguages.get(1).name());
            languageToSpinner.setSelection(positionToLanguage);
        }
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        response.body();

        try {
            JSONArray obja = new JSONArray(response.body());
            JSONArray array = null;
            if (obja.get(1) instanceof JSONArray) {
                array = (JSONArray) ((JSONArray) ((JSONArray) obja.get(1)).get(0)).get(1);
            } else {
                array = new JSONArray(Arrays.asList(((JSONArray) ((JSONArray) obja.get(0)).get(0)).get(0)));
            }
            List<String> translationList = new ArrayList<String>();
            Translation translation = new Translation();
            translation.setSource(mProvideWordEt.getText().toString());
            translation.setTimestamp(System.currentTimeMillis());
            translation.setLanguageTo(Language.valueOf(binding.languageTo.getSelectedItem().toString()));
            translation.setLanguageFrom(Language.valueOf(binding.languageFrom.getSelectedItem().toString()));
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

    public void showTimePickerDialog() {
        timerPickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void setupAlarmSwitch(){

        ((Switch)binding.toolbar.findViewById(R.id.switchForActionBar)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){

                }else{

                }
            }
        });


    }


}
