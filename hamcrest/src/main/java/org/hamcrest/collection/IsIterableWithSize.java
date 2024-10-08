package org.hamcrest.collection;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Iterator;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matches if iterable size satisfies a size matcher.
 *
 * @param <E> the iterable element type
 */
public class IsIterableWithSize<E> extends FeatureMatcher<Iterable<E>, Integer> {

    /**
     * Constructor, best called from {@link #iterableWithSize(int)} or
     * {@link #iterableWithSize(Matcher)}.
     * @param sizeMatcher checks the expected size of the iterable
     */
    public IsIterableWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an iterable with size", "iterable size");
    }

    @Override
    protected Integer featureValueOf(Iterable<E> actual) {
      int size = 0;
      for (Iterator<E> iterator = actual.iterator(); iterator.hasNext(); iterator.next()) {
        size++;
      }
      return size;
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that satisfies the specified
     * matcher.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
     *
     * @param <E>
     *     the matcher type.
     * @param sizeMatcher
     *     a matcher for the number of items that should be yielded by an examined {@link Iterable}
     * @return The matcher.
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsIterableWithSize<>(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that is equal to the specified
     * <code>size</code> argument.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
     *
     * @param <E>
     *     the matcher type.
     * @param size
     *     the number of items that should be yielded by an examined {@link Iterable}
     * @return The matcher.
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return iterableWithSize(equalTo(size));
    }

}
