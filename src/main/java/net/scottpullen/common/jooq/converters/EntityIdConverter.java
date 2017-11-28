package net.scottpullen.common.jooq.converters;

import net.scottpullen.common.entities.EntityId;
import net.scottpullen.users.entities.UserId;
import org.jooq.Converter;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class EntityIdConverter<T extends EntityId> implements Converter<Object, T> {

    private final Class<T> toClass;

    public EntityIdConverter(Class<T> toClass) {
        this.toClass = toClass;
    }

    @Override
    public T from(Object t) {
        if(t == null) {
            return null;
        }

        T o = null;

        try {
            o = toClass.getConstructor(String.class).newInstance(t + "");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
        }

        return o;
    }

    @Override
    public UUID to(T u) {
        return u == null ? null : u.getValue();
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<T> toType() {
        return toClass;
    }
}
