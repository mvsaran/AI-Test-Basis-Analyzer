package com.testbasis.analyzer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TestBasisOutput {

    @JsonProperty("entities")
    private List<String> entities;

    @JsonProperty("actions")
    private List<String> actions;

    @JsonProperty("validations")
    private List<String> validations;

    @JsonProperty("flows")
    private List<String> flows;

    @JsonProperty("edge_cases")
    private List<String> edgeCases;

    public List<String> getEntities() { return entities; }
    public void setEntities(List<String> entities) { this.entities = entities; }

    public List<String> getActions() { return actions; }
    public void setActions(List<String> actions) { this.actions = actions; }

    public List<String> getValidations() { return validations; }
    public void setValidations(List<String> validations) { this.validations = validations; }

    public List<String> getFlows() { return flows; }
    public void setFlows(List<String> flows) { this.flows = flows; }

    public List<String> getEdgeCases() { return edgeCases; }
    public void setEdgeCases(List<String> edgeCases) { this.edgeCases = edgeCases; }
}
