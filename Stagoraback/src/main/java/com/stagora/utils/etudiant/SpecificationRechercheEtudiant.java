package com.stagora.utils.etudiant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.stagora.entities.students.Etudiant;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
public class SpecificationRechercheEtudiant {

    public static Specification<Etudiant> filtre(String nom, String competence, String formation, String experience) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtre par nom
            if (nom != null && !nom.isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
            }

            // Filtre par compétence
            if (competence != null && !competence.isEmpty()) {
                predicates.add(builder.like(builder.lower(root.join("competences").get("intitule")), "%" + competence.toLowerCase() + "%"));
            }

            // Filtre par formation
            if (formation != null && !formation.isEmpty()) {
                predicates.add(builder.like(builder.lower(root.join("formation").get("intitule")), "%" + formation.toLowerCase() + "%"));
            }

            // Filtre par expérience 
            if (experience != null && !experience.isEmpty()) {
                predicates.add(builder.like(builder.lower(root.join("experiences").get("intitulePoste")), "%" + experience.toLowerCase() + "%"));
            }


            return predicates.isEmpty() ? builder.conjunction() : builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
