package br.com.ticket.sale.core.common.domain.value_objects;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public abstract class ValueObject<T> implements Serializable {

    protected final T value;

    protected ValueObject(T value) {
        this.value = makeImmutable(value);
    }

    public final T getValue() {
        return value;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        ValueObject<?> other = (ValueObject<?>) obj;

        if (isArray(value)) {
            return Arrays.deepEquals(
                    new Object[]{value},
                    new Object[]{other.value}
            );
        }

        return Objects.equals(this.value, other.value);
    }

    @Override
    public final int hashCode() {
        if (isArray(value)) {
            return Arrays.deepHashCode(new Object[]{value});
        }
        return Objects.hashCode(value);
    }

    @SuppressWarnings("unchecked")
    private T makeImmutable(T obj) {

        if (obj == null) {
            return null;
        }

        if (obj instanceof Map<?, ?> map) {
            return (T) Collections.unmodifiableMap(new LinkedHashMap<>(map));
        }

        if (obj instanceof List<?> list) {
            return (T) Collections.unmodifiableList(new ArrayList<>(list));
        }

        if (obj instanceof Set<?> set) {
            return (T) Collections.unmodifiableSet(new LinkedHashSet<>(set));
        }

        if (obj instanceof Collection<?> collection) {
            return (T) Collections.unmodifiableCollection(new ArrayList<>(collection));
        }

        if (isArray(obj)) {
            int length = Array.getLength(obj);
            Object copy = Array.newInstance(obj.getClass().getComponentType(), length);
            System.arraycopy(obj, 0, copy, 0, length);
            return (T) copy;
        }

        return obj;
    }

    private boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
