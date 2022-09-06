package org.almondiz.almondiz.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.common.TimeStamped;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Store_Table")
public class Store extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private String storeName;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Long ownerId;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Long categoryId;

    private double lati;
    private double longi;
    private String address;
    private String contact;

}
