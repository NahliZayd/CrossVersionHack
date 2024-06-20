package zn.cvh.utils;

public class TimeUtil {
    private long lastMS;

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public long getLastMS() {
        return this.lastMS;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public void setLastMS(long currentMS) {
        this.lastMS = currentMS;
    }

    public long getTimeSinceReset() {
        return this.getCurrentMS() - this.lastMS;
    }
}
