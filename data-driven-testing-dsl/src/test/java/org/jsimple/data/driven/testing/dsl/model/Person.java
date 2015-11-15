package org.jsimple.data.driven.testing.dsl.model;

import java.math.BigDecimal;

public class Person {
    private String firstName;
    private String lastName;
    private BigDecimal value;

    public Person() {
    }

    public Person(String firstName, String lastName, BigDecimal value) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.value = value;
    }

    private Person(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        value = builder.value;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        return !(value != null ? !value.equals(person.value) : person.value != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", value=" + value +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private BigDecimal value;

        public Builder() {
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}