package covid;

import java.text.NumberFormat;
import java.util.Locale;

public class RigaDato {
	
	private String intestazione;
	private long vecchioValore;
	private long nuovoValore;
	public String getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
	public long getVecchioValore() {
		return vecchioValore;
	}
	public void setVecchioValore(long vecchioValore) {
		this.vecchioValore = vecchioValore;
	}
	public String getNuovoValore() {
		return getNuovoValoreForm(nuovoValore);
	}
	
	public String getNuovoValoreForm(long numberToFormat) {

		String numero = ""+numberToFormat;
		NumberFormat formattatore = NumberFormat.getNumberInstance();
		formattatore = NumberFormat.getInstance(Locale.ITALY);
		formattatore.setMaximumFractionDigits(2);
		numero = numero.replace(',','.');
		return formattatore.format(Double.parseDouble(numero ));

	}
	

	
	public void setNuovoValore(long nuovoValore) {
		this.nuovoValore = nuovoValore;
	}
	
	public String getDifferenza()
	{
		return getNuovoValoreForm( nuovoValore - vecchioValore);
	}
	
	public RigaDato(String intestazione, long vecchioValore, long nuovoValore) {
		super();
		this.intestazione = intestazione;
		this.vecchioValore = vecchioValore;
		this.nuovoValore = nuovoValore;
	}
	
	public String toString()
	{
		return "intestazione:" + intestazione + " vecchio:"+vecchioValore + " nuovo:"+nuovoValore;
	}

	
}
