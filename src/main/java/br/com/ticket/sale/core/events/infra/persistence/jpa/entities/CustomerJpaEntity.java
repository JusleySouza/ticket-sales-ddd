package br.com.ticket.sale.core.events.infra.persistence.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public CustomerJpaEntity(){}

    public CustomerJpaEntity(String id,String cpf,String name){
        this.id=id;
        this.cpf=cpf;
        this.name=name;
    }

    public String getId(){ return id; }

    public String getCpf(){ return cpf; }

    public String getName(){ return name; }

    public void setId(String id){ this.id=id; }

    public void setCpf(String cpf){ this.cpf=cpf; }

    public void setName(String name){ this.name=name; }

}
