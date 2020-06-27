package org.jhipster.petclinic.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.jhipster.petclinic.domain.Visit} entity. This class is used
 * in {@link org.jhipster.petclinic.web.rest.VisitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /visits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VisitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter visitDate;

    private StringFilter description;

    private LongFilter petId;

    public VisitCriteria() {
    }

    public VisitCriteria(VisitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.visitDate = other.visitDate == null ? null : other.visitDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.petId = other.petId == null ? null : other.petId.copy();
    }

    @Override
    public VisitCriteria copy() {
        return new VisitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateFilter visitDate) {
        this.visitDate = visitDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getPetId() {
        return petId;
    }

    public void setPetId(LongFilter petId) {
        this.petId = petId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VisitCriteria that = (VisitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(visitDate, that.visitDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(petId, that.petId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        visitDate,
        description,
        petId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (visitDate != null ? "visitDate=" + visitDate + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (petId != null ? "petId=" + petId + ", " : "") +
            "}";
    }

}
