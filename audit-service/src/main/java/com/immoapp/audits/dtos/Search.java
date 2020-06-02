package com.immoapp.audits.dtos;

public class Search {
    public String type;
    public Integer isParking;
    public Integer isLift;
    public Integer isGardien;
    public Integer isCheminee;
    public Integer nbrPieceMin;
    public Integer nbrPieceMax;
    public Integer nbrChambreMin;
    public Integer nbrChambreMax;
    public Long dateMiseEnLigneMin;
    public Long dateMiseEnLigneMax;
    public Long dateConstructionMin;
    public Long dateConstructionMax;
    public String codePostal;
    public String zone;
    public String ville;
    public Integer prixMin;
    public Integer prixMax;
    public Integer surfaceMin;
    public Integer surfaceMax;
    
    public Integer page;
    public Integer pageSize;
    
    public ProduitImmobilierDTO[] result;
    
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getIsParking() {
		return isParking;
	}
	public void setIsParking(Integer isParking) {
		this.isParking = isParking;
	}
	public Integer getIsLift() {
		return isLift;
	}
	public void setIsLift(Integer isLift) {
		this.isLift = isLift;
	}
	public Integer getIsGardien() {
		return isGardien;
	}
	public void setIsGardien(Integer isGardien) {
		this.isGardien = isGardien;
	}
	public Integer getIsCheminee() {
		return isCheminee;
	}
	public void setIsCheminee(Integer isCheminee) {
		this.isCheminee = isCheminee;
	}
	public Integer getNbrPieceMin() {
		return nbrPieceMin;
	}
	public void setNbrPieceMin(Integer nbrPieceMin) {
		this.nbrPieceMin = nbrPieceMin;
	}
	public Integer getNbrPieceMax() {
		return nbrPieceMax;
	}
	public void setNbrPieceMax(Integer nbrPieceMax) {
		this.nbrPieceMax = nbrPieceMax;
	}
	public Integer getNbrChambreMin() {
		return nbrChambreMin;
	}
	public void setNbrChambreMin(Integer nbrChambreMin) {
		this.nbrChambreMin = nbrChambreMin;
	}
	public Integer getNbrChambreMax() {
		return nbrChambreMax;
	}
	public void setNbrChambreMax(Integer nbrChambreMax) {
		this.nbrChambreMax = nbrChambreMax;
	}
	public Long getDateMiseEnLigneMin() {
		return dateMiseEnLigneMin;
	}
	public void setDateMiseEnLigneMin(Long dateMiseEnLigneMin) {
		this.dateMiseEnLigneMin = dateMiseEnLigneMin;
	}
	public Long getDateMiseEnLigneMax() {
		return dateMiseEnLigneMax;
	}
	public void setDateMiseEnLigneMax(Long dateMiseEnLigneMax) {
		this.dateMiseEnLigneMax = dateMiseEnLigneMax;
	}
	public Long getDateConstructionMin() {
		return dateConstructionMin;
	}
	public void setDateConstructionMin(Long dateConstructionMin) {
		this.dateConstructionMin = dateConstructionMin;
	}
	public Long getDateConstructionMax() {
		return dateConstructionMax;
	}
	public void setDateConstructionMax(Long dateConstructionMax) {
		this.dateConstructionMax = dateConstructionMax;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public Integer getPrixMin() {
		return prixMin;
	}
	public void setPrixMin(Integer prixMin) {
		this.prixMin = prixMin;
	}
	public Integer getPrixMax() {
		return prixMax;
	}
	public void setPrixMax(Integer prixMax) {
		this.prixMax = prixMax;
	}
	public Integer getSurfaceMin() {
		return surfaceMin;
	}
	public void setSurfaceMin(Integer surfaceMin) {
		this.surfaceMin = surfaceMin;
	}
	public Integer getSurfaceMax() {
		return surfaceMax;
	}
	public void setSurfaceMax(Integer surfaceMax) {
		this.surfaceMax = surfaceMax;
	}

	public ProduitImmobilierDTO[] getResult() {
		return result;
	}
	public void setResult(ProduitImmobilierDTO[] result) {
		this.result = result;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
    
    
}
