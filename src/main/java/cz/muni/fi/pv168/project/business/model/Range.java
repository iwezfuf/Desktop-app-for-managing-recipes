package cz.muni.fi.pv168.project.business.model;

/**
 * @author Marek Eibel
 */
public class Range {

    private int min;
    private int max;
    private int value;

    /**
     * Create Range object holding `value` in range [minimalValue, maximalValue].
     *
     * @param minimalValue
     * @param maximalValue
     */
    Range(int minimalValue, int maximalValue, int value) {
        this.min = minimalValue;
        this.max = maximalValue;

        // TODO make check here
        this.value = value;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
