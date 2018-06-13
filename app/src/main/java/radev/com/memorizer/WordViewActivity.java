package radev.com.memorizer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import radev.com.memorizer.databinding.ActivityWordViewBinding;
import radev.com.memorizer.model.Translation;

public class WordViewActivity extends AppCompatActivity {

    public static final String RECENT_TRANSLATION="recentTranslation";

    ActivityWordViewBinding binding;

    static List<Translation> wordsMap = new ArrayList<Translation>();

    @Inject
    Settings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_view);
        MemorizerApp.getInstance().getComponent().inject(this);
        wordsMap = settings.getTranslationHistory();
        setData();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    private void setData() {

        if(getIntent()!=null){
            if(getIntent().getExtras()!=null){
                String source = getIntent().getExtras().getString(RECENT_TRANSLATION);
                for(Translation translation:wordsMap){
                    if(translation.getSource().equalsIgnoreCase(source)){
                        binding.wordSource.setText(source);
                        //TODO display translations in RecyclerView list with and adapter
                        List<String> translations = translation.getTranslationList();
                        String result="";
                        for(String word:translations){
                            result += word +"\n";
                        }
                        binding.wordTranslation.setText(result);
                        break;
                    }
                }
            }

        }
    }

}
