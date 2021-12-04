package com.epam.training.ticketservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class RoomEntity {

    @Id
    private String name;

    private int chairRows;
    private int chairColumn;

    @OneToMany(mappedBy = "room")
    private List<ScreeningEntity> screeningEntities;

    public RoomEntity(String name, int chairRows, int chairColumn) {
        this.name = name;
        this.chairRows = chairRows;
        this.chairColumn = chairColumn;
    }

    public RoomEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChairRows() {
        return chairRows;
    }

    public void setChairRows(int chairRows) {
        this.chairRows = chairRows;
    }

    public int getChairColumn() {
        return chairColumn;
    }

    public void setChairColumn(int chairColumn) {
        this.chairColumn = chairColumn;
    }

    @Override
    public String toString() {
        return String.format("Room %s with %d seats, %d rows and %d columns",
                name, chairRows * chairColumn, chairRows, chairColumn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomEntity)) {
            return false;
        }
        RoomEntity that = (RoomEntity) o;
        return getChairRows() == that.getChairRows()
                && getChairColumn() == that.getChairColumn()
                && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getChairRows(), getChairColumn());
    }
}
