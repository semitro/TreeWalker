package smt.business;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface TriConsumer<T,U,S > {
    void accept(T t, U u, S s);
}
