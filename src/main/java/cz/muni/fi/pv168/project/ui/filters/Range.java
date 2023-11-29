package cz.muni.fi.pv168.project.ui.filters;

/**
 * Represents data object to hold two integers values representing range.
 *
 * @author Marek Eibel
 */
public class Range {

    private int min;
    private int max;

    /**
     * Creates Range object holding `value` in range [minimalValue, maximalValue].
     *
     * @param minimalValue minimal value
     * @param maximalValue maximal value
     */
    public Range(int minimalValue, int maximalValue) {

        this.min = minimalValue;
        this.max = maximalValue;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isValueInRange(int value) {
        return (value >= min && value <= max);
    }

    @Override
    public String toString() {
        return "<" + min + "," + max + ">";
    }
}

