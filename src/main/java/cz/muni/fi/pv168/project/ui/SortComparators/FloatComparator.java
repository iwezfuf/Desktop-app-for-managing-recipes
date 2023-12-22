package cz.muni.fi.pv168.project.ui.SortComparators;

import java.util.Comparator;

public class FloatComparator implements Comparator<Float> {
    @Override
    public int compare(Float o1, Float o2) {
        return o1.compareTo(o2);
    }
}
