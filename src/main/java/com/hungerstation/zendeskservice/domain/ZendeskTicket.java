package com.hungerstation.zendeskservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "zendesk_tickets",
        indexes = {
                @Index(name = "index_zendesk_tickets_on_related_to_type_and_related_to_id", columnList = "related_to_id,related_to_type"
                )
        }
)
public class ZendeskTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zendesk_reference_id")
    private String zendeskReferenceId;

    @NotNull
    @Column(name = "related_to_type")
    private String relatedToType;

    @NotNull
    @Column(name = "related_to_id")
    private Long relatedToId;

    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = this.updatedAt = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getZendeskReferenceId() {
        return zendeskReferenceId;
    }

    public void setZendeskReferenceId(String zendeskReferenceId) {
        this.zendeskReferenceId = zendeskReferenceId;
    }

    public String getRelatedToType() {
        return relatedToType;
    }

    public void setRelatedToType(String relatedToType) {
        this.relatedToType = relatedToType;
    }

    public Long getRelatedToId() {
        return relatedToId;
    }

    public void setRelatedToId(Long relatedToId) {
        this.relatedToId = relatedToId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZendeskTicket that = (ZendeskTicket) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(zendeskReferenceId, that.zendeskReferenceId) &&
                Objects.equals(relatedToType, that.relatedToType) &&
                Objects.equals(relatedToId, that.relatedToId) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zendeskReferenceId, relatedToType, relatedToId, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "ZendeskTicket{" +
                "zendeskReferenceId='" + zendeskReferenceId + '\'' +
                ", relatedToType='" + relatedToType + '\'' +
                ", relatedToId=" + relatedToId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
