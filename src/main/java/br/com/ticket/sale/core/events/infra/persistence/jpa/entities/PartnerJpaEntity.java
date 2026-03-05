package br.com.ticket.sale.core.events.infra.persistence.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "partners")
public class PartnerJpaEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public PartnerJpaEntity() {}

    public PartnerJpaEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }
}
