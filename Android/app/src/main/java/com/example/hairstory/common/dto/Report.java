package com.example.hairstory.common.dto;

public class Report {
    String branchName;
    double amount;

    public Report(String branchName, double amount) {
        this.branchName = branchName;
        this.amount = amount;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
