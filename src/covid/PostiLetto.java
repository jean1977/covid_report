package covid;

import java.text.NumberFormat;

public class PostiLetto {
	//private String regione;
	
	private int ti;
	private int nonTi;
	public int getTi() {
		return ti;
	}
	public void setTi(int ti) {
		this.ti = ti;
	}
	public int getNonTi() {
		return nonTi;
	}
	public void setNonTi(int nonTi) {
		this.nonTi = nonTi;
	}
	
	
	public PostiLetto(int nonTi, int ti) {
		super();
		this.ti = ti;
		this.nonTi = nonTi;
	}
	
	public PostiLetto(String ti, String nonTi) {
		super();
		this.ti = Integer.valueOf(ti);
		this.nonTi = Integer.valueOf(nonTi);
	}
	public String toString()
	{
		return " ti=" + getTi() + ", non ti=" + getNonTi();
	}

	
	
	
	
	public double calcolaPercentualeOccupazioneTi(PostiLetto disponibili)
	{
		return percentualeOccupazioneTi(disponibili);
	}
	
	private double percentualeOccupazioneTi(PostiLetto disponibili) {
		double risultato = (Double.valueOf(this.getTi()) /  Double.valueOf(disponibili.getTi()))*100;
		
		
		if (Double.isNaN(risultato))
		{
			return 0;
		}
		
		 risultato = (double) Math.round(risultato * 100) / 100;
		
		return risultato;
	}

	public double calcolaPercentualeOccupazioneNonTi(PostiLetto disponibili)
	{
		return percenutaleOccupazioneNonTi(disponibili);
	}
	
	private double percenutaleOccupazioneNonTi(PostiLetto disponibili) {
		double risultato = (Double.valueOf(this.getNonTi()) /  Double.valueOf(disponibili.getNonTi()))*100;
		if (Double.isNaN(risultato))
		{
			return 0;
		}
		risultato = (double) Math.round(risultato * 100) / 100;
		return risultato;
	}
	
	double calcolaIncrementoPercentualeTi(PostiLetto ieri, PostiLetto oggi)
	{
		return oggi.getTi() - ieri.getTi();
		//return percentualeOccupazioneTi(ieri) - percentualeOccupazioneTi(oggi);
	}

	double calcolaIncrementoPercentualeNonTi(PostiLetto ieri, PostiLetto oggi)
	{
		return  oggi.getNonTi() - ieri.getNonTi();
		//return percenutaleOccupazioneNonTi(ieri) - percenutaleOccupazioneNonTi(oggi);
	}
	
	public String occupazioneTi(PostiLetto disponibili)
	{
		return this.getTi()+"/"+disponibili.getTi();
	}

	public String occupazioneNonTi(PostiLetto disponibili)
	{
		return this.getNonTi()+"/"+disponibili.getNonTi();
	}

	

}
