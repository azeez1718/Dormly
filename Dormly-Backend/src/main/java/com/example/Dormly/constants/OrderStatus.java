package com.example.Dormly.constants;

public enum OrderStatus {
    PENDING,      // Order initiated - user initiated a payment.
    CONFIRMED,    // stripe payment response was a success, otherwise we cancel order
    COMPLETED,    // Transaction finalized; only set when we release escrow funds and buyer gets items
    CANCELLED     // Transaction cancelled.
}
