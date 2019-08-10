package edu.self.josephkandi.travelmantics.models;

import java.util.Objects;

public class Deal {
    private  String id;

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deal)) return false;
        Deal deal = (Deal) o;
        return getId().equals(deal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setId(String id) {
        this.id = id;
    }

    private String place;
    private String amount;
    private String description;
    private String placeImageUrl;

    public Deal() {
    }

    public Deal(String place, String amount, String description, String placeImageUrl) {
        this.place = place;
        this.amount = amount;
        this.description = description;
        this.placeImageUrl = placeImageUrl;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceImageUrl() {
        return placeImageUrl;
    }

    public void setPlaceImageUrl(String placeImageUrl) {
        this.placeImageUrl = placeImageUrl;
    }

    @Override
    public String toString() {
        return getPlace();
    }
}
