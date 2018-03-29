
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Jogos{
	String msg = "";
	 public String ultimo() throws IOException {
		 Document doc = Jsoup.connect("http://globoesporte.globo.com/futebol/times/palmeiras").get();
		 int cont = 0;		 
		 Elements ultimo = doc.getElementsByClass("anterior");
		 for(Element jogo:ultimo) {
			 if(cont==6) {
			 Elements mand = jogo.getElementsByClass("sigla-mandante");
			 Elements resMand = jogo.getElementsByClass("numero-placar-mandante");
			 Elements resVis = jogo.getElementsByClass("numero-placar-visitante");
			 Elements vis = jogo.getElementsByClass("sigla-visitante");			
			 msg += mand.attr("title")+" "+resMand.text()+" x "+resVis.text()+" "+vis.attr("title")+"\n";
			 }
			 cont++;
		 }
		 cont = 0;
		 return msg;
	 }
	 public String proximo() throws IOException {
		 msg = "";
		 Document doc = Jsoup.connect("http://globoesporte.globo.com/futebol/times/palmeiras").get();
		 int cont = 0;
		 Elements proximo = doc.getElementsByClass("proximo");
		 for(Element p:proximo) {
			if(cont<1) {
			Elements mand = p.getElementsByClass("sigla-mandante");
			Elements resMand = p.getElementsByClass("numero-placar-mandante");
			Elements resVis = p.getElementsByClass("numero-placar-visitante");
			Elements vis = p.getElementsByClass("sigla-visitante");			
			msg += mand.attr("title")+" "+resMand.text()+" x "+resVis.text()+" "+vis.attr("title")+"\n";
		
			}
			cont++;
		 }
		 cont = 0;
		 return msg;
}
}
