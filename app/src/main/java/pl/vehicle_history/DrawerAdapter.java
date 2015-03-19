package pl.vehicle_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pl.vehicle_history.historiapojazdu.R;

/**
 * Created by Cuthbert on 2015-03-19.
 */
public class DrawerAdapter extends BaseAdapter {

    private enum DrawerOption {
        FIND_VEHICLE(R.string.title_section_find_vehicle, R.drawable.calendar),
        SEARCH_HISTORY(R.string.title_section_about, R.drawable.calendar),
        OPTIONS(R.string.title_section_options, R.drawable.calendar),
        ABOUT(R.string.title_section_about, R.drawable.calendar);

        private int labelResId;

        private int iconResId;
        DrawerOption(int labelResId, int iconResId) {
            this.labelResId = labelResId;
            this.iconResId = iconResId;
        }

        public int getLabelResId() {
            return labelResId;
        }

        public int getIconResId() {
            return iconResId;
        }

    }

    private Context context;

    public DrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return DrawerOption.values().length;
    }

    @Override
    public DrawerOption getItem(int position) {
        return DrawerOption.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.drawer_row, parent, false);

        bindItemToRow(getItem(position), row);

        return row;
    }

    private void bindItemToRow(DrawerOption item, View row) {
        ((TextView) row.findViewById(R.id.drawer_label)).setText(item.getLabelResId());
        ((ImageView) row.findViewById(R.id.drawer_icon)).setImageResource(item.getIconResId());
    }
}
