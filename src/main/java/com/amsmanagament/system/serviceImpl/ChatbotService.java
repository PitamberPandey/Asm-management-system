//package com.amsmanagament.system.serviceImpl;
//
//import com.amsmanagament.system.model.Inventory;
//import com.amsmanagament.system.repo.InventoryRepo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ChatbotService {
//
//    private final ChatClient chatClient;
//
//    @Autowired
//    private InventoryRepo inventoryRepo;
//
//    // ✅ FIX: correct constructor name + injection
//    public ChatbotService(ChatClient.Builder builder) {
//        this.chatClient = builder.build();
//    }
//
//    public String chat(Long farmerId, String message) {
//
//        String msg = message.toLowerCase();
//
//        // =========================
//        // 1. INVENTORY LIST
//        // =========================
//        if (msg.contains("inventory") || msg.contains("stock")) {
//
//            List<Inventory> list = inventoryRepo.findAll();
//
//            if (list.isEmpty()) {
//                return "📦 No inventory found.";
//            }
//
//            StringBuilder sb = new StringBuilder("📦 Inventory List:\n");
//
//            for (Inventory i : list) {
//                sb.append("• ")
//                        .append(i.getProduct().getProductName())
//                        .append(" → ")
//                        .append(i.getQuantity())
//                        .append("\n");
//            }
//
//            return sb.toString();
//        }
//
//        // =========================
//        // 2. SEARCH PRODUCT
//        // =========================
//        List<Inventory> search = inventoryRepo.searchByProductName(msg);
//
//        if (!search.isEmpty() && msg.length() > 2) {
//
//            StringBuilder sb = new StringBuilder("🔎 Search Results:\n");
//
//            for (Inventory i : search) {
//                sb.append("• ")
//                        .append(i.getProduct().getProductName())
//                        .append(" → ")
//                        .append(i.getQuantity())
//                        .append("\n");
//            }
//
//            return sb.toString();
//        }
//
//        // =========================
//        // 3. LOW STOCK
//        // =========================
//        if (msg.contains("low stock")) {
//
//            List<Inventory> list = inventoryRepo.findAll();
//
//            StringBuilder sb = new StringBuilder("⚠ Low Stock Items:\n");
//            boolean found = false;
//
//            for (Inventory i : list) {
//                if (i.getQuantity() < 5) {
//                    sb.append("• ")
//                            .append(i.getProduct().getProductName())
//                            .append(" → ")
//                            .append(i.getQuantity())
//                            .append("\n");
//                    found = true;
//                }
//            }
//
//            return found ? sb.toString() : "✅ No low stock items.";
//        }
//
//        // =========================
//        // 4. AI FALLBACK (IMPORTANT FIX)
//        // =========================
//        return chatClient
//                .prompt()
//                .system(systemSpec -> systemSpec.text("""
//                        You are Krishi Bazaar AI assistant.
//                        Help farmers with:
//                        - farming advice
//                        - crop management
//                        - selling products
//                        Keep answers simple and practical.
//                        """))
//                .user(message)
//                .call()
//                .content();
//    }
//
//
//}