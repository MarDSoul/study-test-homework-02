package study.test;

public enum Workload {
    VERY_HIGH(1.6f),
    HIGH(1.4f),
    NOT_VERY_HIGH(1.2f),
    NORMAL(1.0f);

    private final float multiplier;

    Workload(float multiplier) {
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }
}
