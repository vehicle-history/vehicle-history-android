package pl.vehicle_history.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pl.vehicle_history.Search;
import pl.vehicle_history.SearchHistoryDb;
import pl.vehicle_history.historiapojazdu.R;

/**
 * Created by m4lysh on 2015-03-20.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private SearchHistoryDb searchHistoryDb;

    public HistoryAdapter(SearchHistoryDb searchHistoryDb) {
        this.searchHistoryDb = searchHistoryDb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row,
                viewGroup, false);

        return new ViewHolder(row);
    }

    @Override
    public int getItemCount() {
        return searchHistoryDb.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Search search = searchHistoryDb.getSearchAt(i);

        ((ImageView) viewHolder.historyRow.findViewById(R.id.history_icon)).setImageResource(
                R.drawable.ic_car_black_36dp);

        ((TextView) viewHolder.historyRow.findViewById(R.id.label)).setText(search.getLabel());
        ((TextView) viewHolder.historyRow.findViewById(R.id.plate)).setText(search.getPlate());
        ((TextView) viewHolder.historyRow.findViewById(R.id.vin)).setText(search.getVin());
        ((TextView) viewHolder.historyRow.findViewById(R.id.registration_date)).setText(search.getRegistrationDate());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View historyRow;

        public ViewHolder(View historyRow) {
            super(historyRow);
            this.historyRow = historyRow;
        }

    }

}
