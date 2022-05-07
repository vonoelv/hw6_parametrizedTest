package org.example.domain;

import java.util.List;

public enum HeaderDropdown {
    PRODUCT("Product", List.of(
            "Mobile",
            "Actions",
            "Codespaces",
            "Packages",
            "Security",
            "Code review",
            "Issues",
            "Integrations",
            "GitHub Sponsors",
            "Customer stories"
    )),
    TEAM("Team", List.of()),
    ENTERPRISE("Enterprise", List.of()),
    EXPLORE("Explore", List.of()),
    MARKETPLACE("Marketplace", List.of()),
    PRICING("Pricing", List.of());


    public String dropdownName;
    public List<String> dropdownItems;

    private HeaderDropdown(String dropdownName, List<String> dropdownItems) {
        this.dropdownName = dropdownName;
        this.dropdownItems = dropdownItems;
    }
}
