package radev.com.memorizer;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import radev.com.memorizer.databinding.WordHistoryListItemBinding;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class WordHistoryListAdapter extends RecyclerView.Adapter<WordHistoryListAdapter.ViewHolder> {
    private static final String TAG = "WordHistoryListAdapter";
    private List<String> mDataSet;

    private Context mContext;

    public WordHistoryListAdapter(Context ctx){
        this.mContext = ctx;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        WordHistoryListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.word_history_list_item,
                        viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        String text = mDataSet.get(position);
        TextView translationSource = viewHolder.textView;

        final RecyclerView translatedWordRecyclerView = viewHolder.mRecyclerView;
        WordHistoryListAdapter adapter = new WordHistoryListAdapter(mContext);
        translatedWordRecyclerView.setAdapter(adapter);
        translatedWordRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.setData(Dashboard.wordsMap.get(text));
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





    }

    public void setData(List<String> data) {
        mDataSet = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }

        return mDataSet.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView textView;
        public RecyclerView mRecyclerView;

        public ViewHolder(WordHistoryListItemBinding binding) {
            super(binding.getRoot());
            textView = binding.textView;
            mRecyclerView = binding.recyclerView;
        }

    }
}
