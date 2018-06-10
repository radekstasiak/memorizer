package radev.com.memorizer;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import radev.com.memorizer.app.Settings;
import radev.com.memorizer.databinding.WordHistoryListItemBinding;
import radev.com.memorizer.model.Translation;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class WordHistoryListAdapter extends RecyclerView.Adapter<WordHistoryListAdapter.ViewHolder> {
    private static final String TAG = "WordHistoryListAdapter";
    private List<Translation> mDataSet;

    private Context mContext;
    private Settings mSettings;

    public WordHistoryListAdapter(Settings settings){
        mSettings = settings;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        WordHistoryListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.word_history_list_item,
                        viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        String text = mDataSet.get(position).getSource();
        TextView translationSource = viewHolder.textView;

        final RecyclerView translatedWordRecyclerView = viewHolder.mRecyclerView;

        TranslatedWordHistoryListAdapter adapter = new TranslatedWordHistoryListAdapter();
        translatedWordRecyclerView.setAdapter(adapter);
        translatedWordRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.setData(mDataSet.get(position).getTranslationList());
        translationSource.setText(text);
        translationSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(translatedWordRecyclerView.getVisibility()== View.VISIBLE){
                    translatedWordRecyclerView.setVisibility(View.GONE);
                } else {
                    translatedWordRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataSet(position);
            }
        });
    }

    public void updateDataSet(int position){
        if(this.mDataSet!=null){
            mDataSet.remove(position);
            mSettings.saveTranslationHistory(mDataSet);
            notifyDataSetChanged();
        }
    }
    public void setData(List<Translation> data) {
        mDataSet = data;
        Collections.sort(mDataSet);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }

        return mDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public  TextView textView;
        public RecyclerView mRecyclerView;
        public Button mDeleteBtn;
        public ViewHolder(WordHistoryListItemBinding binding) {
            super(binding.getRoot());
            textView = binding.word;
            mRecyclerView = binding.recyclerView;
            mDeleteBtn = binding.deleteBtn;
            mDeleteBtn.setVisibility(View.VISIBLE);
            binding.translatedLanguage.setVisibility(View.VISIBLE);
        }
    }
}
