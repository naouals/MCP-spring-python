package org.example.mcpserver.tools;


import jdk.jfr.Description;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockTools {
    private List<Company> companies = List.of(
            new Company("Maroc Telecom", "Telecom", 100, 1_000_000.0, "Morocco"),
            new Company("Google", "Search Engine", 400, 4_000_000.0, "USA"),
            new Company("Tesla", "Automobile", 200, 2_000_000.0, "USA")


    );

    @Tool(description = "Returns a list of all companies")
    public List<Company> getAllCompanies() {
        return companies;
    }

    @Tool(description = "Returns a company by its name")
    public Company getCompanyByName(String name) {
        return companies.stream()
                .filter(company -> company.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Company with name " + name + " not found"));
    }

    @Tool(description = "Returns the stock value of a company by its name")
    public Stock getStockByCompanyName(String companyName) {
        Company company = getCompanyByName(companyName);
        LocalDate date = LocalDate.now();
        double stockValue = Math.random() * 1000; // Simulating stock value
        return new Stock(company.name(), date, stockValue);
    }
}


record Company(String name, String activity, @Description("The turnover in Milliard MAD ") int employeesCount,
               double turnover, String country) {
}

record Stock(String companyName, LocalDate date, double stock) {
}