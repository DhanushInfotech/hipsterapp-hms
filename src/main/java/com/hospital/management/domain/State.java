package com.hospital.management.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A State.
 */
@Entity
@Table(name = "state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "state")
    private String state;

    @ManyToOne
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public State state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public State country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        if(state.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "State{" +
            "id=" + id +
            ", state='" + state + "'" +
            '}';
    }
}
