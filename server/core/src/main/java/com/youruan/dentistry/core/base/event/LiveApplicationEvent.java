package com.youruan.dentistry.core.base.event;

public abstract class LiveApplicationEvent<T> {

    private final T source;

    public LiveApplicationEvent(T source) {
        this.source = source;
    }

    public T getSource() {
        return source;
    }

}
