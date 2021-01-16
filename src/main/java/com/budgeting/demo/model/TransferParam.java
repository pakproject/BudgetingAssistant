package com.budgeting.demo.model;

public class TransferParam {
    private String source;
    private String target;
    private Double amount;

    public TransferParam() {
    }

    public TransferParam(String source, String target, Double amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
