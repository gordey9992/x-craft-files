package com.github.xcraftfiles.data;

import java.util.UUID;

public class InvestigationData {
    private final UUID investigationId;
    private final String caseName;
    private final UUID assignedAgent;
    private int foundEvidence;
    private boolean completed;
    
    public InvestigationData(UUID investigationId, String caseName, UUID assignedAgent) {
        this.investigationId = investigationId;
        this.caseName = caseName;
        this.assignedAgent = assignedAgent;
        this.foundEvidence = 0;
        this.completed = false;
    }
    
    public UUID getInvestigationId() { return investigationId; }
    public String getCaseName() { return caseName; }
    public UUID getAssignedAgent() { return assignedAgent; }
    public int getFoundEvidence() { return foundEvidence; }
    public boolean isCompleted() { return completed; }
    
    public void addEvidence() { foundEvidence++; }
    public void complete() { completed = true; }
}
