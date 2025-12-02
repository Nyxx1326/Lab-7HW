package com.example.productmanagement.controller;
import com.example.productmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showDashboard(Model model) {

        // Total inventory value
        model.addAttribute("totalValue", productService.calculateTotalValue());

        // Average price
        model.addAttribute("averagePrice", productService.calculateAveragePrice());

        // Low stock products (<10)
        model.addAttribute("lowStockProducts", productService.findLowStockProducts(10));

        // Recent 5 products
        model.addAttribute("recentProducts", productService.findRecentProducts(5));

        // Count by categories
        List<String> categories = List.of("Electronics", "Furniture", "Clothing", "Books", "Food", "Other");
        Map<String, Long> categoryCounts = new LinkedHashMap<>();
        for (String category : categories) {
            categoryCounts.put(category, productService.countByCategory(category));
        }
        model.addAttribute("categoryCounts", categoryCounts);

        return "dashboard";
    }
}
