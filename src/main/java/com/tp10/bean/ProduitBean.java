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
    private Produit selected = new Produit();
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

    // Populate the edit dialog with the selected product
    public void editSelected(Produit p) {
        // Copy fields so the dialog works on a detached copy
        this.selected = new Produit();
        this.selected.setId(p.getId());
        this.selected.setName(p.getName());
        this.selected.setPrice(p.getPrice());
    }

    public void update() {
        service.updateProduit(selected);
        // Refresh the matching entry in the local list
        if (produits != null) {
            for (int i = 0; i < produits.size(); i++) {
                if (produits.get(i).getId().equals(selected.getId())) {
                    produits.set(i, selected);
                    break;
                }
            }
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Modifié", "Article modifié !"));
        selected = new Produit();
    }

    public void delete(Long id, String name) {
        service.deleteProduit(id);
        if (produits != null) {
            produits.removeIf(p -> p.getId().equals(id));
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Supprimé",
                        "Article \"" + name + "\" supprimé !"));
    }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public Produit getSelected() { return selected; }
    public void setSelected(Produit selected) { this.selected = selected; }
}