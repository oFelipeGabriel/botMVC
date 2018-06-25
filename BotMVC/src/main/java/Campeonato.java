import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengrad.telegrambot.model.Update;

public class Campeonato {
	String url_camp = "http://globoesporte.globo.com/futebol/campeonato/";
	String url;
	String msg;
	
	public String searchArtilheiros(Update update) throws IOException{
		msg = "";
		url = "";
		Document doc = Jsoup.connect(url_camp).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
				System.out.println(t.text());
			}
		}
		System.out.println(url);
		doc = Jsoup.connect(url).get();
		int cont = 0;
		Elements posicao = doc.getElementsByClass("jogador");
		for(Element pos:posicao) {
			if(cont<25) {
				String sNome = "";
				String sTime = "";
				Elements nome = pos.getElementsByClass("jogador-foto");
				for(Element n:nome) {
					sNome = n.getElementsByTag("img").attr("alt"); 
				}
				Elements time = pos.getElementsByClass("jogador-escudo");
				for(Element t:time) {
					sTime = t.getElementsByTag("img").attr("alt"); 
				}
				Elements gols = pos.getElementsByClass("jogador-gols");
				msg += (cont+1)+" - "+sNome+" - "+sTime+" - "+gols.text()+" gols\n";	
				cont++;
			}
			
		}
		System.out.println(msg);
		return msg;		
	}
	
	public String searchClassificacao(Update update) throws IOException{
		url = "";
		msg = "";
		Document doc = Jsoup.connect(url_camp).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
			}
		}
		doc = Jsoup.connect(url).get();
		Elements tipo = doc.getElementsByClass("gui-text-section-title tabela-header-titulo");
		for(Element t:tipo) {
		if(t.text().equals("Tabela")){	
			System.out.println(t.text());
			Elements times = doc.getElementsByClass("tabela-body-linha");
			Elements time = doc.getElementsByClass("tabela-times-time-nome");
			Elements posicao = doc.getElementsByClass("tabela-times-posicao");
			Elements pontos = doc.getElementsByClass("tabela-pontos-ponto");
			for(int x=0;x<times.size()/2;x++) {				
				msg += posicao.eq(x).text()+" - "+time.eq(x).text()+" - "+pontos.eq(x).text()+"\n";
				}
			
		}
		
		else if(t.text().equals("CONFRONTOS")) {
			Elements local = doc.getElementsByClass("placar-jogo-informacoes");
			Elements timeM = doc.getElementsByClass("placar-jogo-equipes-escudo-mandante");
			Elements timeV = doc.getElementsByClass("placar-jogo-equipes-escudo-visitante");
			Elements placarM = doc.getElementsByClass("placar-jogo-equipes-placar-mandante");
			Elements placarV = doc.getElementsByClass("placar-jogo-equipes-placar-visitante");
			for(int k=0;k<timeM.size();k++) {
				msg += local.eq(k).text()+"\n"+timeM.eq(k).attr("title")+" "+placarM.eq(k).text()+" X "
						+placarV.eq(k).text()+" "+timeV.eq(k).attr("title")+"\n";
			}
		}
		}
		System.out.println("msg: "+msg);
		return msg;
	}
}
