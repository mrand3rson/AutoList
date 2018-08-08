package com.example.autolist.types;

import android.content.Context;
import android.util.Log;

import com.example.autolist.R;
import com.example.autolist.pattern.models.Automobile;

import java.util.Comparator;

/**
 * Created by Andrei on 08.08.2018.
 */

public class SortTypes {
    private static final String LOG_TAG = "Sort_types";

    public final static int SORT_BY_PRICE_ASC = 1;
    public final static int SORT_BY_PRICE_DESC = 2;
    public final static int SORT_BY_DATE_ASC = 3;
    public final static int SORT_BY_DATE_DESC = 4;


    public static String[] getAllSortNames(Context context) {
        return new String[] {getSortName(context, SORT_BY_PRICE_ASC),
                getSortName(context, SORT_BY_PRICE_DESC),
                getSortName(context, SORT_BY_DATE_ASC),
                getSortName(context, SORT_BY_DATE_DESC),
        };
    }

    private static String getSortName(Context context, int sortType) {
        String result;
        switch (sortType) {
            case SORT_BY_PRICE_ASC: {
                result = context.getString(R.string.string_sort_price_asc);
                break;
            }
            case SORT_BY_PRICE_DESC: {
                result = context.getString(R.string.string_sort_price_desc);
                break;
            }
            case SORT_BY_DATE_ASC: {
                result = context.getString(R.string.string_sort_date_asc);
                break;
            }
            case SORT_BY_DATE_DESC: {
                result = context.getString(R.string.string_sort_date_desc);
                break;
            }
            default: {
                result = "Undefined";
                Log.w(LOG_TAG, "sort name undefined");
            }
        }
        return result;
    }

    public static Comparator<Automobile> getComparator(int sortType) {
        switch (sortType) {
            case SORT_BY_PRICE_ASC: {
                return (o1, o2) -> Integer.compare(o1.getPrice(), o2.getPrice());
            }
            case SORT_BY_PRICE_DESC: {
                return (o1, o2) -> Integer.compare(o2.getPrice(), o1.getPrice());
            }
            case SORT_BY_DATE_ASC: {
                return (o1, o2) -> Long.compare(o1.getId(), o2.getId());
            }
            case SORT_BY_DATE_DESC: {
                return (o1, o2) -> Long.compare(o2.getId(), o1.getId());
            }
            default: {
                Log.w(LOG_TAG, "comparator is null");
                return null;
            }
        }
    }
}
