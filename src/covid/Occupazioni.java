package covid;

import java.text.NumberFormat;

public class Occupazioni {
	private double percentualeTI;
	private double variazionePercentualeTI;
	private double percentualeNonTI;
	private double variazionePercentualeNonTI;
	private String segnovariazionePercentualeNonTI;
	private String segnovariazionePercentualeTI;
	private String coloreTi;
	private String coloreNonTi;
	private String occupazioneTi;
	private String occupazioneNonTi;
	
	public double getPercentualeTI() {
		return percentualeTI;
	}
	public void setPercentualeTI(double percentualeTI) {
		this.percentualeTI = percentualeTI;
	}
	public double getVariazionePercentualeTI() {
		return variazionePercentualeTI;
	}
	public void setVariazionePercentualeTI(double variazionePercentualeTI) {
		this.variazionePercentualeTI = variazionePercentualeTI;
	}
	public double getPercentualeNonTI() {
		return percentualeNonTI;
	}
	public void setPercentualeNonTI(double percentualeNonTI) {
		this.percentualeNonTI = percentualeNonTI;
	}
	public double getVariazionePercentualeNonTI() {
		return variazionePercentualeNonTI;
	}
	public void setVariazionePercentualeNonTI(double variazionePercentualeNonTI) {
		this.variazionePercentualeNonTI = variazionePercentualeNonTI;
	}
	
	
	
	public String getColoreTi() {
		return coloreTi;
	}
	public void setColoreTi(String coloreTi) {
		this.coloreTi = coloreTi;
	}
	public String getColoreNonTi() {
		return coloreNonTi;
	}
	public void setColoreNonTi(String coloreNonTi) {
		this.coloreNonTi = coloreNonTi;
	}
	public String getSegnovariazionePercentualeNonTI() {
		return segnovariazionePercentualeNonTI;
	}
	public void setSegnovariazionePercentualeNonTI(String segnovariazionePercentualeNonTI) {
		this.segnovariazionePercentualeNonTI = segnovariazionePercentualeNonTI;
	}
	public String getSegnovariazionePercentualeTI() {
		return segnovariazionePercentualeTI;
	}
	public void setSegnovariazionePercentualeTI(String segnovariazionePercentualeTI) {
		this.segnovariazionePercentualeTI = segnovariazionePercentualeTI;
	}
	public Occupazioni(double percentualeTI, double variazionePercentualeTI, double percentualeNonTI,
			double variazionePercentualeNonTI,String occupazioneTi, String occupazioneNonTi) {
		super();
		this.percentualeTI = percentualeTI;
		this.variazionePercentualeTI = variazionePercentualeTI;
		this.percentualeNonTI = percentualeNonTI;
		this.variazionePercentualeNonTI = variazionePercentualeNonTI;
		this.occupazioneTi = occupazioneTi;
		this.occupazioneNonTi = occupazioneNonTi;
		
		if(variazionePercentualeTI>0)
		{
			segnovariazionePercentualeTI = "su";
		}
		else if(variazionePercentualeTI<0)
		{
			segnovariazionePercentualeTI = "giu";
		}
		else 
		{
			segnovariazionePercentualeTI = "bianco";
		}

		if(variazionePercentualeNonTI>0)
		{
			segnovariazionePercentualeNonTI = "su";
		}
		else if(variazionePercentualeNonTI<0)
		{
			segnovariazionePercentualeNonTI = "giu";
		}
		else 
		{
			segnovariazionePercentualeNonTI = "bianco";
		}

		
		
		/*
		 * giallo 10 e 15
		 * arancio 20 e 30
		 * rosso 30 e 40
		 * */
		
		if (percentualeTI>=30)
		{
			coloreTi = costanti.ROSSO;
		}
		else if (percentualeTI>=20)
		{
			coloreTi = costanti.ARANCIONE;
		}
		else if(percentualeTI>=10)
		{
			coloreTi = costanti.GIALLO;
		}
		

		if (percentualeNonTI>=40)
		{
			coloreNonTi = costanti.ROSSO;
		}
		else if (percentualeNonTI>=30)
		{
			coloreNonTi = costanti.ARANCIONE;
		}
		else if(percentualeNonTI>=15)
		{
			coloreNonTi = costanti.GIALLO;
		}

		
		
		
	}
	
	
	

	public String getOccupazioneTi() {
		return occupazioneTi;
	}
	public void setOccupazioneTi(String occupazioneTi) {
		this.occupazioneTi = occupazioneTi;
	}
	public String getOccupazioneNonTi() {
		return occupazioneNonTi;
	}
	public void setOccupazioneNonTi(String occupazioneNonTi) {
		this.occupazioneNonTi = occupazioneNonTi;
	}
	public String toString()
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);            
		nf.setGroupingUsed(false);
		//return  nf.format(risultato) + "%";
		return "percentualeTI:" + nf.format(percentualeTI) 
				+ " variazionePercentualeTI:" + nf.format(variazionePercentualeTI) 
				+ " percentualeNonTI:" + nf.format(percentualeNonTI) 
				+ " variazionePercentualeNonTi:" + nf.format(variazionePercentualeNonTI);
	}
	
	

}
