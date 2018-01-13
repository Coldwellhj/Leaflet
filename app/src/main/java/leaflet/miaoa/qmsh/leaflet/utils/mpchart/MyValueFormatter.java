package leaflet.miaoa.qmsh.leaflet.utils.mpchart;


import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import leaflet.miaoa.qmsh.leaflet.utils.StringUtils;

/**
 * Created by JKWANG-PC on 2016/11/21.
 */

public class MyValueFormatter implements IValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return StringUtils.double2String(value, 2);
    }
}