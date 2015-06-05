package io.vehiclehistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import io.vehiclehistory.R;

/**
 * Created by Cuthbert on 2015-03-19.
 */
public class DrawerAdapter extends BaseAdapter {

    public enum DrawerOption {
        FIND_VEHICLE(R.string.title_section_find_vehicle, R.drawable.ic_file_find_grey600_24dp, true),
        EXAMPLE(R.string.title_section_example, R.drawable.ic_file_find_grey600_24dp, false),
        SEARCH_HISTORY(R.string.title_section_history, R.drawable.ic_history_grey600_24dp, true),
        OPTIONS(R.string.title_section_options, R.drawable.ic_settings_grey600_24dp, true),
        RATE(R.string.title_section_rate, R.drawable.ic_star_grey600_24dp, false),
        ABOUT(R.string.title_section_about, R.drawable.ic_information_grey600_24dp, true);

        private int labelResId;

        private int iconResId;

        private boolean selectable;

        DrawerOption(int labelResId, int iconResId, boolean selectable) {
            this.labelResId = labelResId;
            this.iconResId = iconResId;
            this.selectable = selectable;
        }

        public int getLabelResId() {
            return labelResId;
        }

        public int getIconResId() {
            return iconResId;
        }

        public boolean isSelectable() {
            return selectable;
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
