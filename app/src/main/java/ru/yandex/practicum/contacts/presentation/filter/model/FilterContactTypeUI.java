package ru.yandex.practicum.contacts.presentation.filter.model;
import ru.yandex.practicum.contacts.presentation.base.ListDiffInterface;

import androidx.annotation.NonNull;

public class FilterContactTypeUI implements ListDiffInterface<FilterContactTypeUI> {

    private final FilterContactType contactType;
    private final boolean selected;

    public FilterContactTypeUI(@NonNull FilterContactType contactType, boolean selected) {
        this.contactType = contactType;
        this.selected = selected;
    }

    public FilterContactType getContactType() {
        return contactType;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterContactTypeUI that = (FilterContactTypeUI) o;

        if (selected != that.selected) return false;
        return contactType == that.contactType;
    }

    @Override
    public int hashCode() {
        int result = contactType.hashCode();
        result = 31 * result + (selected ? 1 : 0);
        return result;
    }
    @Override
    public boolean theSameAs(FilterContactTypeUI other) {
        return this.getContactType() == other.getContactType();
    }

}
