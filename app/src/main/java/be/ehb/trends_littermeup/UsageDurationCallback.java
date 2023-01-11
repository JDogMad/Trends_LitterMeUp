package be.ehb.trends_littermeup;

public interface UsageDurationCallback {
    void onSuccess(long diffHours, long diffDays);
    void onError(String errorMessage);
}
