package com.example.gestionemployee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide.")
    @Size(min = 3, max = 50, message = "Le nom doit contenir entre 3 et 50 caractères.")
    private String name;

    @NotBlank(message = "Le poste est obligatoire.")
    private String position;

    @NotNull(message = "Le salaire est obligatoire.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le salaire doit être supérieur à 0.")
    private double salary;
}
