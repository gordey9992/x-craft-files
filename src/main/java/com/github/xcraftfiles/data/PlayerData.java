package com.github.xcraftfiles.data;

import java.util.UUID;

public class PlayerData {
    private final UUID playerId;
    private int completedInvestigations;
    private int foundEvidence;
    
    public PlayerData(UUID playerId) {
        this.playerId = playerId;
        this.completedInvestigations = 0;
        this.foundEvidence = 0;
    }
    
    public UUID getPlayerId() { return playerId; }
    public int getCompletedInvestigations() { return completedInvestigations; }
    public int getFoundEvidence() { return foundEvidence; }
    
    public void addCompletedInvestigation() { completedInvestigations++; }
    public void addFoundEvidence() { foundEvidence++; }
}
