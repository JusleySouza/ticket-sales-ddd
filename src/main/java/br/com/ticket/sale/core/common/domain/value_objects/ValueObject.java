package br.com.ticket.sale.core.common.domain.value_objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class ValueObject<T> implements Serializable {

    protected final T value;

    protected ValueObject(T value) {
        this.value = deepFreeze(value);
    }

    public T getValue() {
        return value;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }

        ValueObject<?> other = (ValueObject<?>) obj;

        return Objects.deepEquals(this.value, other.value);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        if (value == null) {
            return "null";
        }

        if (!(value instanceof Map) && !(value instanceof Iterable)) {
            try {
                return value.toString();
            } catch (Exception e) {
                return String.valueOf(value);
            }
        }

        return value.toString();
    }

    @SuppressWarnings("unchecked")
    private T deepFreeze(T obj) {
        if (obj instanceof Map<?, ?> map) {
            return (T) Collections.unmodifiableMap(map);
        }

        if (obj instanceof Collection<?> collection) {
            return (T) Collections.unmodifiableCollection(collection);
        }

        return obj;
    }
}
