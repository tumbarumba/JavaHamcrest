package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.hamcrest.core.IsIterableContaining.hasItems;

public final class IsIterableContainingTest {

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<?> matcher = hasItem(equalTo("irrelevant"));

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    matchesACollectionThatContainsAnElementForTheGivenMatcher() {
        final Matcher<Iterable<? extends String>> itemMatcher = hasItem(equalTo("a"));

        assertMatches("list containing 'a'", itemMatcher, asList("a", "b", "c"));
    }

    @Test public void
    doesNotMatchCollectionWithoutAnElementForGivenMatcher() {
        final Matcher<Iterable<? extends String>> matcher = hasItem(mismatchable("a"));

        assertMismatchDescription("mismatches were: [mismatched: b, mismatched: c]", matcher, asList("b", "c"));
        assertMismatchDescription("was empty", matcher, new ArrayList<String>());
    }

    @Test public void
    doesNotMatchNull() {
        assertDoesNotMatch("doesn't match null", hasItem(equalTo("a")), null);
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("a collection containing mismatchable: a", hasItem(mismatchable("a")));
    }

    @Test public void
    canMatchItemWhenCollectionHoldsSuperclass() { // Issue 24
        final Set<Number> s = new HashSet<>();
        s.add(2);

        assertMatches(new IsIterableContaining<>(new IsEqual<Number>(2)), s);
        assertMatches(IsIterableContaining.hasItem(2), s);
    }

    @SuppressWarnings("unchecked")
    @Test public void
    matchesMultipleItemsInCollection() {
        final Matcher<Iterable<? extends String>> matcher1 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("list containing all items", matcher1, asList("a", "b", "c"));

        final Matcher<Iterable<? extends String>> matcher2 = hasItems("a", "b", "c");
        assertMatches("list containing all items (without matchers)", matcher2, asList("a", "b", "c"));

        final Matcher<Iterable<? extends String>> matcher3 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("list containing all items in any order", matcher3, asList("c", "b", "a"));

        final Matcher<Iterable<? extends String>> matcher4 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("list containing all items plus others", matcher4, asList("e", "c", "b", "a", "d"));

        final Matcher<Iterable<? extends String>> matcher5 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertDoesNotMatch("not match list unless it contains all items", matcher5, asList("e", "c", "b", "d")); // 'a' missing
    }

    @Test public void
    reportsMismatchWithAReadableDescriptionForMultipleItems() {
        final Matcher<Iterable<? extends Integer>> matcher = hasItems(3, 4);

        assertMismatchDescription("a collection containing <4> mismatches were: [was <1>, was <2>, was <3>]",
                                  matcher, asList(1, 2, 3));
    }

    private static Matcher<String> mismatchable(final String string) {
        return new TypeSafeDiagnosingMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item, Description mismatchDescription) {
                if (string.equals(item))
                    return true;

                mismatchDescription.appendText("mismatched: " + item);
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("mismatchable: " + string);
            }
        };
    }

    @Test public void
    matchesPolymorphicTypes() {
        Collection<Dog> dogs = singleton(new Dog("Spot"));
        Animal spotAsAnimal = new Dog("Spot");
        assertMatches(hasItem(spotAsAnimal), dogs);
        Dog spotAsDog = new Dog("Spot");
        assertMatches(hasItem(spotAsDog), dogs);

        Collection<? extends Animal> animals = asList(
                new Dog("Fido"), new Cat("Whiskers"));
        Dog fido = new Dog("Fido");
        Matcher<Iterable<? extends Animal>> dogsMatcher = hasItem(fido);
        assertMatches(dogsMatcher, animals);

        Cat whiskers = new Cat("Whiskers");
        assertMatches(hasItem(whiskers), animals);

        Matcher<Iterable<? extends Animal>> iterableMatcher = hasItems(fido, whiskers);
        assertMatches(iterableMatcher, animals);
        assertMatches(not(hasItem(spotAsAnimal)), animals);
    }

    abstract static class Animal {
        private final String name;

        Animal(String name) {
            this.name = name;
        }

        public String name() {
            return this.name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            return name.equals(((Animal) obj).name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    static class Dog extends Animal {
        public Dog(String name) {
            super(name);
        }
    }

    static class Cat extends Animal {
        public Cat(String name) {
            super(name);
        }
    }
}
