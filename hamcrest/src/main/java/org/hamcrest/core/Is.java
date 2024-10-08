package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Decorates another Matcher, retaining the behaviour but allowing tests
 * to be slightly more expressive.
 *
 * For example:  assertThat(cheese, equalTo(smelly))
 *          vs.  assertThat(cheese, is(equalTo(smelly)))
 *
 * @param <T> the matched value type
 */
public class Is<T> extends BaseMatcher<T> {

    private final Matcher<T> matcher;

    /**
     * Constructor, best called from {@link #is(Object)}, {@link #is(Matcher)}, or {@link #isA(Class)}.
     * @param matcher the matcher to wrap
     */
    public Is(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object arg) {
        return matcher.matches(arg);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is ").appendDescriptionOf(matcher);
    }

    @Override
    public void describeMismatch(Object item, Description mismatchDescription) {
        matcher.describeMismatch(item, mismatchDescription);
    }

    /**
     * Decorates another Matcher, retaining its behaviour, but allowing tests
     * to be slightly more expressive.
     * For example:
     * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
     * instead of:
     * <pre>assertThat(cheese, equalTo(smelly))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param matcher
     *     the matcher to wrap.
     * @return The matcher.
     */
    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return new Is<>(matcher);
    }

    /**
     * A shortcut to the frequently used <code>is(equalTo(x))</code>.
     * For example:
     * <pre>assertThat(cheese, is(smelly))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param value
     *     the value to check.
     * @return The matcher.
     */
    public static <T> Matcher<T> is(T value) {
        return is(equalTo(value));
    }

    /**
     * A shortcut to the frequently used <code>is(instanceOf(SomeClass.class))</code>.
     * For example:
     * <pre>assertThat(cheese, isA(Cheddar.class))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(instanceOf(Cheddar.class)))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param type
     *     the type to check.
     * @return The matcher.
     */
    public static <T> Matcher<T> isA(Class<?> type) {
        return is(IsInstanceOf.<T>instanceOf(type));
    }

}
