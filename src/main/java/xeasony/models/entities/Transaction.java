package xeasony.models.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import xeasony.validations.TransactionParentId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Transaction {
    @JsonIgnore
    private Long id;
    @NotNull
    private Double amount;
    @NotNull
    @Size(min = 1, max = 50)
    private String type;
    @TransactionParentId
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonGetter("parent_id")
    public Long getParentId() {
        return parentId;
    }

    @JsonSetter("parent_id")
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
