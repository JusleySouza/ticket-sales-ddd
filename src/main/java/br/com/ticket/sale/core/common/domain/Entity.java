package br.com.ticket.sale.core.common.domain;

import java.util.Objects;

public abstract class Entity<ID> {

    protected ID id;

    public ID getId() {
        return id;
    }

    public abstract Object toJSON();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Entity<?> other = (Entity<?>) obj;

        if (this.id == null || other.id == null) {
            return false;
        }

        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
