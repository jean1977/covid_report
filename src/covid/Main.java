package covid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub

		
		

		
		String linkNazionale = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-andamento-nazionale/dpc-covid19-ita-andamento-nazionale.csv";
		String linkRegionale = "https://github.com/pcm-dpc/COVID-19/blob/master/dati-regioni/dpc-covid19-ita-regioni.csv?raw=true";
		
		URL urlRegionale = new URL(linkRegionale); 
		URL urlNazionale = new URL(linkNazionale);
		
		File fileRegione = new File("d:\\file_covid\\dpc-covid19-ita-regioni.csv");
		File fileNazione = new File("d:\\file_covid\\dpc-covid19-ita-andamento-nazionale.csv");
		
		/*
		 * E' necessario scaricare questo url https://www.agenas.gov.it/covid19/web/index.php?r=site%2Ftab2
		 * */
		
		File fileOccupazione = new File("d:\\file_covid\\Portale Covid-19.html");
		
		
		Map<String, PostiLetto> postiDisponibili = new HashMap<String, PostiLetto>();
		
		postiDisponibili = LeggiPostiLetto(fileOccupazione);
		String aggiornamentoDisponibili = LeggiAggiornamento(fileOccupazione);
		
		
		FileUtils.copyURLToFile(urlRegionale, fileRegione);
		FileUtils.copyURLToFile(urlNazionale, fileNazione);
		
		
		final String separatore = ",";
		final String fileRegioneProtCiv = "D:\\file_covid\\dpc-covid19-ita-regioni.csv";
		
		ArrayList<String> intestazioniValide = new ArrayList<String>();
		
		intestazioniValide.add("terapia_intensiva");	
		intestazioniValide.add("totale_ospedalizzati");	
		intestazioniValide.add("isolamento_domiciliare");	
		intestazioniValide.add("dimessi_guariti");
		intestazioniValide.add("deceduti");
		intestazioniValide.add("totale_casi");
		intestazioniValide.add("tamponi");
		intestazioniValide.add("casi_testati");
		intestazioniValide.add("ingressi_terapia_intensiva");	
		intestazioniValide.add("totale_positivi_test_molecolare");	
		intestazioniValide.add("totale_positivi_test_antigenico_rapido");	
		intestazioniValide.add("tamponi_test_molecolare");
		intestazioniValide.add("tamponi_test_antigenico_rapido");

		BufferedReader in = new BufferedReader (new InputStreamReader (new ReverseLineInputStream(new File("D:\\file_covid\\dpc-covid19-ita-andamento-nazionale.csv"))));
	    String ultimoNazionale = in.readLine();
	    String penUltimoNazionale = in.readLine();
	    
		String csvItaIntestazioni = "";
		//nazionale, recupero la linea delle intestazioni
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\file_covid\\dpc-covid19-ita-andamento-nazionale.csv"))) {
		    csvItaIntestazioni=br.readLine();
		}
		

		
		List<String> elencoIntestazioniNazionale = Arrays.asList(csvItaIntestazioni.split(separatore,-1));
		List<String> listPenUltimoNazionale = Arrays.asList(penUltimoNazionale.split(separatore,-1));
		List<String> listUltimoNazionale = Arrays.asList(ultimoNazionale.split(separatore,-1));
		
		System.out.println(ultimoNazionale.substring(0, 10));
		//String dataString = 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = sdf.parse(ultimoNazionale.substring(0, 10));
		String date=sdf2.format(currentDate );
		
		/*System.out.println(date);
		System.out.println(currentDate);
		
		*/
		
		   Locale.setDefault(Locale.ITALIAN);
		   SimpleDateFormat sdf3 = new SimpleDateFormat("dd MMMM yyyy"); 
		   String dataStringaFull = sdf3.format(currentDate);
		   System.out.println(dataStringaFull); 
		
		ArrayList<Integer> indexNazionaleValido = new ArrayList<Integer>();
		
		for(int i=0;i<elencoIntestazioniNazionale.size();i++)
		{
			if(intestazioniValide.contains(elencoIntestazioniNazionale.get(i)))
			{
				indexNazionaleValido.add(i);
			}
		}
		

		
		List<RigaDato> listaElementoNazionale = new ArrayList<RigaDato>();
		
		for (Iterator iterator = indexNazionaleValido.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			listaElementoNazionale.add(new RigaDato(elencoIntestazioniNazionale.get(integer),  Long.valueOf(listPenUltimoNazionale.get(integer)),  Long.valueOf(listUltimoNazionale.get(integer))));
			
		}
		
		System.out.println(Arrays.toString(listaElementoNazionale.toArray()));

		
		String fileRegioneLombardia = "D:\\file_covid\\lombardia.csv";
		
		File lombardia = new File(fileRegioneLombardia); 
		lombardia.delete();
		
		FileWriter erroriWriter = new FileWriter(fileRegioneLombardia);
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileRegioneProtCiv))) {
		    String line = br.readLine();
		    
			erroriWriter.write(line+"\n"); //intestazioni
		    while ((line = br.readLine()) != null) {
		    	if(line.toUpperCase().contains("LOMBARDIA"))
				{
					erroriWriter.write(line+"\n");
				}
		    }
		}
		
		erroriWriter.flush();
		erroriWriter.close();

		
		String csvRegioniIntestazioni = "";
		try (BufferedReader br = new BufferedReader(new FileReader(fileRegioneLombardia))) {
		    csvRegioniIntestazioni=br.readLine();
		}
		

		
		BufferedReader reg = new BufferedReader (new InputStreamReader (new ReverseLineInputStream(new File(fileRegioneLombardia))));
	    String ultimoRegionale = reg.readLine();
	    String penUltimoRegionale = reg.readLine();
	    
	    List<String> elencoIntestazioniRegionale = Arrays.asList(csvRegioniIntestazioni.split(separatore,-1));
	    List<String> listPenUltimoRegionale = Arrays.asList(penUltimoRegionale.split(separatore,-1));
		List<String> listUltimoRegionale = Arrays.asList(ultimoRegionale.split(separatore,-1));
		
		ArrayList<Integer> indexRegionaleValido = new ArrayList<Integer>();
		
		for(int i=0;i<elencoIntestazioniRegionale.size();i++)
		{
			if(intestazioniValide.contains(elencoIntestazioniRegionale.get(i)))
			{
				indexRegionaleValido.add(i);
			}
		}
		
		
		List<RigaDato> listaElementoRegionale = new ArrayList<RigaDato>();
		
		for (Iterator iterator = indexRegionaleValido.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			listaElementoRegionale.add(new RigaDato(elencoIntestazioniRegionale.get(integer),  Long.valueOf(listPenUltimoRegionale.get(integer)),  Long.valueOf(listUltimoRegionale.get(integer))));
			
		}
		
		System.out.println(Arrays.toString(listaElementoRegionale.toArray()));
		
		
		Map<String, PostiLetto> occupazioneOggiMap = new HashMap<String, PostiLetto>();
		Map<String, PostiLetto> occupazioneIeriMap = new HashMap<String, PostiLetto>();
		
		
		
		try(BufferedReader occupazioneOggi = new BufferedReader (new InputStreamReader (new ReverseLineInputStream(new File(fileRegioneProtCiv)))))
		{
		    String line = "" ;// occupazioneOggi.readLine();
		    
		    String dataOdierna =sdf.format(currentDate) ;
		    String dataIeri =sdf.format(addDays(currentDate, -1)); 
		    
		    /*if (line.substring(0, 10).equalsIgnoreCase(dataOdierna))
		    {
		    	String[] linea = line.split(separatore,-1);
		    	occupazioneOggiMap.put(linea[3], new Occupazione(linea[7],linea[6]));
		    }
		    
		    if (line.substring(0, 10).equalsIgnoreCase(dataIeri))
		    {
		    	String[] linea = line.split(separatore,-1);
		    	occupazioneIeriMap.put(linea[3], new Occupazione(linea[7],linea[6]));
		    }*/
		    
		    
		    
		    while ((line = occupazioneOggi.readLine()) != null) {
		    	//System.out.println(line);
		    	
		    	if (line.isEmpty())
		    	{
		    		
		    	}
		    	else
		    	{
				    if (dataOdierna.equalsIgnoreCase(line.substring(0, 10)))
				    {
				    	System.out.println(line);
				    	String[] linea = line.split(separatore,-1);
				    	occupazioneOggiMap.put(linea[3], new PostiLetto( linea[7],linea[6]));
				    }
				    
				    if (line.substring(0, 10).equalsIgnoreCase(dataIeri))
				    {
				    	System.out.println(line);
				    	String[] linea = line.split(separatore,-1);
				    	occupazioneIeriMap.put(linea[3], new PostiLetto( linea[7],linea[6]));
				    }
		    	}
			    

		    	
		    	//line = occupazioneOggi.readLine();		    
		    }
		   
		}
		
		
		
	     
		Map<String, Occupazioni> occupazionePosti = new TreeMap<String, Occupazioni>();

		
		
		/*
		postiDisponibili.put("Abruzzo",new PostiLetto(1324,181));
		postiDisponibili.put("Basilicata",new PostiLetto(362,88));
		postiDisponibili.put("Calabria",new PostiLetto(967,181));
		postiDisponibili.put("Campania",new PostiLetto(3528,520));
		postiDisponibili.put("Emilia-Romagna",new PostiLetto(7920,889));
		postiDisponibili.put("Friuli Venezia Giulia",new PostiLetto(1277,175));
		postiDisponibili.put("Lazio",new PostiLetto(6421,943));
		postiDisponibili.put("Liguria",new PostiLetto(1711,217));
		postiDisponibili.put("Lombardia",new PostiLetto(6616,1530));
		postiDisponibili.put("Marche",new PostiLetto(966,246));
		postiDisponibili.put("Molise",new PostiLetto(176,39));
		postiDisponibili.put("P.A. Bolzano",new PostiLetto(500,100));
		postiDisponibili.put("P.A. Trento",new PostiLetto(517,90));
		postiDisponibili.put("Piemonte",new PostiLetto(5824,628));
		postiDisponibili.put("Puglia",new PostiLetto(2722,482));
		postiDisponibili.put("Sardegna",new PostiLetto(1602,204));
		postiDisponibili.put("Sicilia",new PostiLetto(3576,867));
		postiDisponibili.put("Toscana",new PostiLetto(5033,570));
		postiDisponibili.put("Umbria",new PostiLetto(662,88));
		postiDisponibili.put("Valle d'Aosta",new PostiLetto(99,33));
		postiDisponibili.put("Veneto",new PostiLetto(6000,1000));*/

		
		
		
		

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);            
		nf.setGroupingUsed(false);
		//return  nf.format(risultato) + "%";

		
		for (Map.Entry<String, PostiLetto> entry : postiDisponibili.entrySet()) 
		{
			//System.out.println("-");
			System.out.print (entry.getKey());
			PostiLetto letto =  occupazioneOggiMap.get(entry.getKey());
			
			//System.out.println(" ti:" + nf.format(letto.calcolaPercentualeOccupazioneTi(entry.getValue()))+ " nonTi:" +nf.format(letto.calcolaPercentualeOccupazioneNonTi(entry.getValue()))) ; 
			;
			
			occupazionePosti.put(entry.getKey(), new Occupazioni(letto.calcolaPercentualeOccupazioneTi(entry.getValue())
																	,letto.calcolaIncrementoPercentualeTi(occupazioneIeriMap.get(entry.getKey()), letto) 
																	,letto.calcolaPercentualeOccupazioneNonTi(entry.getValue())
																	,letto.calcolaIncrementoPercentualeNonTi(occupazioneIeriMap.get(entry.getKey()), letto)
																	,letto.occupazioneTi(postiDisponibili.get(entry.getKey()))
																	,letto.occupazioneNonTi(postiDisponibili.get(entry.getKey())))); 
			
		}

		System.out.println("ieri");
		for (Map.Entry<String, PostiLetto> entry : occupazioneIeriMap.entrySet())
		{
			System.out.println(entry.getKey() + "         " + entry.getValue());
		}
		System.out.println("oggi");
		for (Map.Entry<String, PostiLetto> entry : occupazioneOggiMap.entrySet())
		{
			System.out.println(entry.getKey() + "         " + entry.getValue());
		}
		
		
		for (Map.Entry<String, Occupazioni> entry : occupazionePosti.entrySet())
		{
			System.out.println(entry.getKey() + "         " + entry.getValue());
		}
		
		
		for (Map.Entry<String, Occupazioni> entry : occupazionePosti.entrySet())
		{
			System.out.println(entry.getKey() + "         " + entry.getValue().getOccupazioneTi());
			System.out.println(entry.getKey() + "         " + entry.getValue().getOccupazioneNonTi());
			
		}
		



		
		
		String htmlFile = "D:\\file_covid\\report.html";
		File html = new File(htmlFile);
		html.delete();
		
		FileWriter htmlWriter = new FileWriter(htmlFile);
		
		htmlWriter.write("<html>"
				+ "<head>"
				+ "<style>"
				+ "td     {font-family: verdana; "
				+ "		   padding: 3px;"
				+ "}"
				+ ".vl {"
				+ "			  border-left: 6px solid green;"
				+ "			  height: 500px;"
				+ "		}"
				+ "</style>"
				+ "</head>"
				+ ""
				+ "<body>");

		htmlWriter.write("<table><tr><td>");
		
		htmlWriter.write("<table border='1'>");
		htmlWriter.write("\n");
		//htmlWriter.write("");
			htmlWriter.write("<tr>");
				htmlWriter.write("<td>");
					htmlWriter.write("&nbsp;");
				htmlWriter.write("</td>");
				htmlWriter.write("<td colspan='2' style=\"background-color:#66FF00\">");
					htmlWriter.write("Nazionale");
				htmlWriter.write("</td>");
				htmlWriter.write("<td colspan='2' style=\"background-color:#00FFFF\">");
					htmlWriter.write("Lombardia");
				htmlWriter.write("</td>");				
			htmlWriter.write("</tr>");
			htmlWriter.write("\n");
			htmlWriter.write("<tr>");
				htmlWriter.write("<td align='right'>" + date + "</td>");
				htmlWriter.write("<td style=\"background-color:#66FF00\">valore</td><td style=\"background-color:#66FF00\">variazione</td>"
						+ "<td style=\"background-color:#00FFFF\">valore</td><td style=\"background-color:#00FFFF\">variazione</td>");
			htmlWriter.write("</tr>");
			htmlWriter.write("\n");
			for (int i = 0; i < listaElementoRegionale.size(); i++) {
				RigaDato nazEle = listaElementoNazionale.get(i);
				RigaDato regEle = listaElementoRegionale.get(i);
				htmlWriter.write("<tr>");
						htmlWriter.write("<td style=\"background-color:#D3D3D3\"><b>");  
							htmlWriter.write(nazEle.getIntestazione());
							htmlWriter.write("</b></td><td align='right'>");
							htmlWriter.write(""+nazEle.getNuovoValore());
							htmlWriter.write("</td><td  align='right'>");
							htmlWriter.write(""+nazEle.getDifferenza());
							htmlWriter.write("</td><td align='right'>");
							htmlWriter.write(""+regEle.getNuovoValore());
							htmlWriter.write("</td><td align='right'>");
							htmlWriter.write(""+regEle.getDifferenza());
						htmlWriter.write("</td>");
				htmlWriter.write("</tr>");
				htmlWriter.write("\n");
			}
			
			
		htmlWriter.write("</table>");
		
		htmlWriter.write("<table><tr><td>Aggiornamento posti disponibili: <b>" + aggiornamentoDisponibili + " </b></td></tr>");
		htmlWriter.write("<tr><td>Aggiornamento altri dati: <b>" + dataStringaFull + "</b></td></tr></table>");
		
		htmlWriter.write("</td><td>");									
		
		htmlWriter.write("<table border=1>");
		htmlWriter.write("<tr>");
				htmlWriter.write("<td style=\"background-color:#D3D3D3\"><b>Regione</b></td><td style=\"background-color:#D3D3D3\"><b>Occupazione TI</b></td><td style=\"background-color:#D3D3D3\"><b>variazione TI</b></td><td style=\"background-color:#D3D3D3\"><b>Occupazione non TI</b></td><td style=\"background-color:#D3D3D3\"><b>variazione non TI</b></td></tr>");
				/*
				for (Map.Entry<String, Occupazioni> entry : occupazionePosti.entrySet())
				{
					htmlWriter.write("<tr>");
					htmlWriter.write("<td>" +entry.getKey() + "</td>");
					htmlWriter.write("<td align='right' style=\"background-color:" + entry.getValue().getColoreTi() + "\">" + entry.getValue().getOccupazioneTi() + "&nbsp;&nbsp;&nbsp;&nbsp;"+nf.format(entry.getValue().getPercentualeTI()) + "%</td>");
					htmlWriter.write("<td align='right'>" +nf.format(entry.getValue().getVariazionePercentualeTI()) + " <img src="+entry.getValue().getSegnovariazionePercentualeTI() +".jpg height=\"20\" width=\"20\"></td>");
					htmlWriter.write("<td align='right' style=\"background-color:" + entry.getValue().getColoreNonTi() +"\">" +entry.getValue().getOccupazioneNonTi() + "&nbsp;&nbsp;&nbsp;&nbsp;"+  nf.format(entry.getValue().getPercentualeNonTI()) + "%</td>");
					htmlWriter.write("<td align='right'>" +nf.format(entry.getValue().getVariazionePercentualeNonTI()) + " <img src=" + entry.getValue().getSegnovariazionePercentualeNonTI() +".jpg height=\"20\" width=\"20\"></td>");
					htmlWriter.write("</tr>");
					htmlWriter.write("\n");
				}*/
				
				for (Map.Entry<String, Occupazioni> entry : occupazionePosti.entrySet())
				{
					htmlWriter.write("<tr>");
					htmlWriter.write("<td>" +entry.getKey() + "</td>");
					
					htmlWriter.write("<td><table width=\"100%\"><tr><td align='left' style=\"background-color:" + entry.getValue().getColoreTi() + "\"><i>" + entry.getValue().getOccupazioneTi() + "</i></td><td align='right' style=\"background-color: "+ entry.getValue().getColoreTi() + "\"><b>"+nf.format(entry.getValue().getPercentualeTI()) + "%</b></td></tr></table></td>");
					
					htmlWriter.write("<td align='right'>" +nf.format(entry.getValue().getVariazionePercentualeTI()) + " <img src="+entry.getValue().getSegnovariazionePercentualeTI() +".jpg height=\"20\" width=\"20\"></td>");
					//htmlWriter.write("<td align='right' style=\"background-color:" + entry.getValue().getColoreNonTi() +"\">" +entry.getValue().getOccupazioneNonTi() + "&nbsp;&nbsp;&nbsp;&nbsp;"+  nf.format(entry.getValue().getPercentualeNonTI()) + "%</td>");
					
					htmlWriter.write("<td><table width=\"100%\"><tr><td align='left' style=\"background-color:" + entry.getValue().getColoreNonTi() + "\"><i>" + entry.getValue().getOccupazioneNonTi() + "</i></td><td align='right' style=\"background-color: "+ entry.getValue().getColoreNonTi() + "\"><b>"+nf.format(entry.getValue().getPercentualeNonTI()) + "%</b></td></tr></table></td>");
					
					htmlWriter.write("<td align='right'>" +nf.format(entry.getValue().getVariazionePercentualeNonTI()) + " <img src=" + entry.getValue().getSegnovariazionePercentualeNonTI() +".jpg height=\"20\" width=\"20\"></td>");
					htmlWriter.write("</tr>");
					htmlWriter.write("\n");
				}
									
												
												
			htmlWriter.write("</table>");

		
		
		htmlWriter.write("</td></tr>");
		//htmlWriter.write("<tr><td>&nbsp;</td><td>Aggiornamento posti disponibili: <b>" + aggiornamentoDisponibili + "</b></td></tr>");
		//htmlWriter.write("<tr><td>&nbsp;</td><td>Aggiornamento altri dati: <b>" + dataStringaFull + "</b></td></tr>");
		htmlWriter.write("</table>");
		htmlWriter.write("</body></html>");
		htmlWriter.close();
		

	}
	
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	public static Map<String, PostiLetto> LeggiPostiLetto(File file) throws IOException
	{
		String regione = "";
		
		Map<String, PostiLetto> postiDisponibili = new HashMap<String, PostiLetto>();
		Document doc = Jsoup.parse(file ,"UTF-8");
		Element tabella = doc.getElementById("tab2_body");
		Elements trs =tabella.getElementsByTag("tr");
		
		for(Element tr: trs)
		{
			//System.out.println(tr);
			regione = tr.getElementsByTag("th").get(0).text();
			if (regione.equalsIgnoreCase("Italia"))
			{
				continue;
			}
			System.out.println(regione);
			Elements td = tr.getElementsByTag("td");
			System.out.println(td);
			postiDisponibili.put(regione, new PostiLetto(td.get(3).text().replace(".", ""), td.get(1).text().replace(".","")));
		}
		
		return postiDisponibili;
	};

	public static String LeggiAggiornamento(File file) throws IOException
	{
		String regione = "";
		
		Map<String, PostiLetto> postiDisponibili = new HashMap<String, PostiLetto>();
		Document doc = Jsoup.parse(file ,"UTF-8");
		Elements aggiornamenti = doc.getElementsByClass("badge");
		
		String aggiornamento ="";
		
		for (Element agg : aggiornamenti)
		{
			if (agg.text().contains("Protezione"))
			{
				aggiornamento = agg.text();
				aggiornamento = aggiornamento.replace("Dati della Protezione Civile del ", "");
				aggiornamento = aggiornamento.substring(0, aggiornamento.length() - 9);
				break;
			}
		}
		
		
		return aggiornamento;
	};
	
	

}
