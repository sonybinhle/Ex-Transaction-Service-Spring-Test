package xeasony.models.dtos;

public class TotalAmountDTO {
    private double amount;
    public TotalAmountDTO(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
