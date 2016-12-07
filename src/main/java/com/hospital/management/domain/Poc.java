package com.hospital.management.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Poc.
 */
@Entity
@Table(name = "poc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Poc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "chiefcomplaint")
    private String chiefcomplaint;

    @Column(name = "bp")
    private Long bp;

    @Column(name = "pulse")
    private Long pulse;

    @Column(name = "temperature")
    private Long temperature;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "drugs")
    private String drugs;

    @Column(name = "docnote")
    private String docnote;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "investigation")
    private String investigation;

    @Column(name = "patientname")
    private String patientname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChiefcomplaint() {
        return chiefcomplaint;
    }

    public Poc chiefcomplaint(String chiefcomplaint) {
        this.chiefcomplaint = chiefcomplaint;
        return this;
    }

    public void setChiefcomplaint(String chiefcomplaint) {
        this.chiefcomplaint = chiefcomplaint;
    }

    public Long getBp() {
        return bp;
    }

    public Poc bp(Long bp) {
        this.bp = bp;
        return this;
    }

    public void setBp(Long bp) {
        this.bp = bp;
    }

    public Long getPulse() {
        return pulse;
    }

    public Poc pulse(Long pulse) {
        this.pulse = pulse;
        return this;
    }

    public void setPulse(Long pulse) {
        this.pulse = pulse;
    }

    public Long getTemperature() {
        return temperature;
    }

    public Poc temperature(Long temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }

    public Long getWeight() {
        return weight;
    }

    public Poc weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getDrugs() {
        return drugs;
    }

    public Poc drugs(String drugs) {
        this.drugs = drugs;
        return this;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getDocnote() {
        return docnote;
    }

    public Poc docnote(String docnote) {
        this.docnote = docnote;
        return this;
    }

    public void setDocnote(String docnote) {
        this.docnote = docnote;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Poc diagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
        return this;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getInvestigation() {
        return investigation;
    }

    public Poc investigation(String investigation) {
        this.investigation = investigation;
        return this;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public String getPatientname() {
        return patientname;
    }

    public Poc patientname(String patientname) {
        this.patientname = patientname;
        return this;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Poc poc = (Poc) o;
        if(poc.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, poc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Poc{" +
            "id=" + id +
            ", chiefcomplaint='" + chiefcomplaint + "'" +
            ", bp='" + bp + "'" +
            ", pulse='" + pulse + "'" +
            ", temperature='" + temperature + "'" +
            ", weight='" + weight + "'" +
            ", drugs='" + drugs + "'" +
            ", docnote='" + docnote + "'" +
            ", diagnosis='" + diagnosis + "'" +
            ", investigation='" + investigation + "'" +
            ", patientname='" + patientname + "'" +
            '}';
    }
}
