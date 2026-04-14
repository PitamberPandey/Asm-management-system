package com.amsmanagament.system.model;

public enum NotificationAction {

    NEW_MESSAGE("New message received 📩", "💬", "#1877f2"),
    PRODUCT_DISCOUNT("New discount available 💸", "💸", "#28a745"),
    PRICE_DROP("Price dropped 🔻", "🔻", "#ff9800"),
    ORDER_STATUS("Order status updated 📦", "📦", "#6f42c1"),
    SYSTEM_ALERT("System alert ⚠️", "⚠️", "#dc3545");

    private final String message;
    private final String icon;
    private final String color;

    NotificationAction(String message, String icon, String color) {
        this.message = message;
        this.icon = icon;
        this.color = color;
    }

    public String getMessage() { return message; }
    public String getIcon() { return icon; }
    public String getColor() { return color; }
}