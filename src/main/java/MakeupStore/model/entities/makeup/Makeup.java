package MakeupStore.model.entities.makeup;

import java.math.BigDecimal;
import java.util.Objects;

public class Makeup {
    private Long id;
    private String brand;
    private BigDecimal price;
    private String cathegory;
    private String name;
    private Integer weightGr;
    private String imageUrl;
    private String description;

    public Makeup() {
    }

    public Makeup(Long id, String brand, String name, String cathegory, BigDecimal price, Integer weightGr, String imageUrl, String description) {
        this.id = id;
        this.brand = brand;
        this.price = price;
        this.weightGr = weightGr;
        this.name = name;
        this.cathegory = cathegory;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Makeup(Long id, String brand, BigDecimal price, String imageUrl) {
        this.id = id;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCathegory() {
        return cathegory;
    }
    public void setCathegory(String cathegory) {
        this.cathegory = cathegory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getWeightGr() {
        return weightGr;
    }
    public void setWeightGr(Integer weightGr) {
        this.weightGr = weightGr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Makeup phone = (Makeup) o;
        return id.equals(phone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
