package com.example.easynotes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size
    private String name;

    @NotNull
    @Email
    @Size
    @Column(unique = true)
    private String email;

    @ElementCollection
    @CollectionTable(name = "company_phone_numbers", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "phone_number")
    private Set<String> phoneNumbers;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "company_addresses", joinColumns = @JoinColumn(name = "company_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "addressline1", column = @Column(name = "house_number")),
            @AttributeOverride(name = "addressline2", column = @Column(name = "street"))
    })
    private Set<Address> addresses = new HashSet<>();

    public Company(String name, String email, Set<String> phoneNumbers, Set<Address> addresses) {
        this.name = name;
        this.email = email;
        this.phoneNumbers = phoneNumbers;
        this.addresses = addresses;
    }



}
