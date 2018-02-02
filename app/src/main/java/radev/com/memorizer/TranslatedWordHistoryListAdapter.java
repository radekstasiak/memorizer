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

import java.util.List;

import radev.com.memorizer.databinding.WordHistoryListItemBinding;
import radev.com.memorizer.model.Translation;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class TranslatedWordHistoryListAdapter extends RecyclerView.Adapter<TranslatedWordHistoryListAdapter.ViewHolder> {
    private static final String TAG = "TranslatedWordList";
    private List<String> mDataSet;

    private Context mContext;

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
        String text = mDataSet.get(position);
        TextView translationSource = viewHolder.textView;
        RecyclerView translatedWordRecyclerView = viewHolder.mRecyclerView;
        translatedWordRecyclerView.setVisibility(View.GONE);
        translationSource.setText(text);





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
