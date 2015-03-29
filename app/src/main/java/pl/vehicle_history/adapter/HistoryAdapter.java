package pl.vehicle_history.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pl.vehicle_history.Search;
import pl.vehicle_history.database.SearchHistoryDb;
import pl.vehicle_history.historiapojazdu.R;

/**
 * Created by m4lysh on 2015-03-20.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private SearchHistoryDb searchHistoryDb;

    private OnHistoryItemClickListener onHistoryItemClickListener;

    public HistoryAdapter(SearchHistoryDb searchHistoryDb, OnHistoryItemClickListener onHistoryItemClickListener) {
        this.searchHistoryDb = searchHistoryDb;
        this.onHistoryItemClickListener = onHistoryItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row,
                viewGroup, false);

        return new ViewHolder(row);
    }

    @Override
    public int getItemCount() {
        return searchHistoryDb.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Search search = searchHistoryDb.getSearchAt(position);

        ((ImageView) viewHolder.historyRow.findViewById(R.id.history_icon)).setImageResource(R.drawable.ic_car_black_36dp);

        ((TextView) viewHolder.historyRow.findViewById(R.id.label)).setText(search.getLabel());
        ((TextView) viewHolder.historyRow.findViewById(R.id.plate)).setText(search.getPlate());
        ((TextView) viewHolder.historyRow.findViewById(R.id.vin)).setText(search.getVin());
        ((TextView) viewHolder.historyRow.findViewById(R.id.registration_date)).setText(search.getRegistrationDate());

        viewHolder.historyRow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onHistoryItemClickListener.onHistoryItemClick(search);
            }
        });
    }

    public interface OnHistoryItemClickListener {
        void onHistoryItemClick(Search search);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View historyRow;

        public ViewHolder(View historyRow) {
            super(historyRow);
            this.historyRow = historyRow;
        }

    }

}
