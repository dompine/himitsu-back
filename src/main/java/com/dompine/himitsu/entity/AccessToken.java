package com.dompine.himitsu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 4905341467868727399L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accestokenId;
    private String Access_token;

    private Double Expires_in;
}
