package com.tp10.bean;

import com.tp10.model.Produit;
import com.tp10.service.ProduitService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProduitBean implements Serializable {

    @Inject
    private ProduitService service;

    private Produit produit = new Produit();
    private List<Produit> produits;

    public List<Produit> getProduits() {
        if (produits == null) {
            produits = service.getProduits();
        }
        return produits;
    }

    public void save() {
        service.createProduit(produit);
        if (produits != null) {
            produits.add(produit);
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Article ajouté !"));
        produit = new Produit();
    }

    public void delete(Long id) {
        service.deleteProduit(id);
        if (produits != null) {
            produits.removeIf(p -> p.getId().equals(id));
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Supprimé", "Article supprimé !"));
    }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
}