package com.example.task02;

public class DiscountBill extends Bill {
    private final int discountPercent;

    public DiscountBill(int discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Скидка может быть от 0 до 100");
        }
        this.discountPercent = discountPercent;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public long getDiscount() {
        return super.getPrice() * discountPercent / 100;
    }

    @Override
    public long getPrice() {
        return Math.abs(super.getPrice() - getDiscount());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Счет к оплате (скидка ").append(discountPercent).append("%)\n");

        String base = super.toString();
        int sumIndex = base.lastIndexOf("Сумма к оплате:");
        if (sumIndex != -1) {
            sb.append(base, 0, sumIndex);
        }
        sb.append("Сумма без скидки: ").append(super.getPrice()).append('\n');
        sb.append("Скидка: ").append(getDiscount()).append('\n');
        sb.append("Сумма к оплате: ").append(getPrice());
        return sb.toString();
    }
}