package com.stagora.utils.etudiant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.stagora.entities.employers.Stage;
import com.stagora.utils.employeur.ModaliteStage;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class SpecificationRechercheStage {

    // Méthode pour construire une spécification dynamique selon les critères fournis
    public static Specification<Stage> filtre(String intitule, String categorie, ModaliteStage modalite,String adresse) {
		// Implement the filtering logic here
		// Return the constructed Specification object
    	return (root, query, builder) -> {
    		List<Predicate> predicates = new ArrayList<>();

    	    // Étape 1 : Filtre sur le titre
    	    addLikePredicate(predicates, builder, root.get("intitule"), intitule);

    	    // Étape 2 : Filtre sur le domaine
    	    addLikePredicate(predicates, builder, root.get("categorie"), categorie);

    	    // Étape 3 : Filtre sur le type d'emploi
    	    if (modalite != null) {
    	        predicates.add(builder.equal(root.get("modalite"), modalite));
    	    } 

    	    // Étape 4 : Filtre sur le lieu
    	    addLikePredicate(predicates, builder, root.get("adresse"), adresse);

    	    // Étape 5 : Retourne tous les résultats si aucun prédicat n'est appliqué
            if (predicates.isEmpty()) {
                return builder.conjunction();  // Toujours vrai : aucune condition WHERE
            }

            // Combinaison des prédicats avec AND
            return builder.and(predicates.toArray(new Predicate[0]));
    	};
	} 
    
    
    private static void addLikePredicate(List<Predicate> predicates, CriteriaBuilder builder, Path<String> field, String value) {
        if (value != null && !value.isEmpty()) {
            predicates.add(builder.like(builder.lower(field), "%" + value + "%"));
        }
    }
}


/*
 *  root
Représentation de l'entité principale dans la requête.
Il s'agit de la racine de l'entité dans la clause FROM de la requête SQL.
Permet d'accéder aux champs de l'entité pour appliquer des conditions.
 * 
 * 
 * 
 * 2. query
Représentation de la requête complète.
Utilisé principalement pour modifier la structure de la requête, par exemple pour définir les ordres de tri (ORDER BY).
Moins utilisé que root et builder dans les filtres de base.
 * 
 * 
 * 
 * 3. builder
Construit les conditions (ou prédicats) de la requête.
C'est une fabrique de prédicats (Predicate), qui sont les conditions de la clause WHERE.
Utilisé pour combiner les conditions avec des opérateurs logiques (AND, OR, etc.).
 * 
 * 
 * */
