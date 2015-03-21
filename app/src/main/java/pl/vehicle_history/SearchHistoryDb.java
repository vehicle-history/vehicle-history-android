package pl.vehicle_history;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m4lysh on 2015-03-21.
 */
public class SearchHistoryDb {

    List<Search> tempValues = new ArrayList<>();

    private Context context;

    public SearchHistoryDb(Context context) {
        this.context = context;

        tempValues.add(new Search("Lexus LFA", "ABC123123", "AB1234123422", "18.02.1999"));
        tempValues.add(new Search("Kia Rio", "OPI876234", "YF234234234d", "28.11.2004"));
        tempValues.add(new Search("Ford Fiesta", "MB7899872", "IF9808908098", "06.05.2013"));
    }

    public void saveSearch(Search search) {
        //TODO implement
    }

    public Search getSearchAt(int index) {
        //TODO real implementation
        return tempValues.get(index);
    }

    public int getCount() {
        //TODO real implementation
        return tempValues.size();
    }

    public void clearDb() {
        //TODO implement
    }
}
