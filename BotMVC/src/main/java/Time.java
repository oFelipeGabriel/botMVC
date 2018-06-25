import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengrad.telegrambot.model.Update;

public class Time {
	String url_times = "http://globoesporte.globo.com/futebol/times/";
	String url = "";
		
	
	public String searchUltimoJogo(Update update) throws IOException{
		String msg = "";
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
			}
		}
		msg = url;
		/*
		doc = Jsoup.connect(url).get();
		Elements jogos = doc.getElementsByClass("jogo anterior");
		if(jogos == null){ msg = "Não há jogos previstos";}
		else{
			for(Element jg:jogos){							
				Elements jogoDia = jg.getElementsByClass("ge-game-info-dia");
				Elements jogoCamp = jg.getElementsByClass("ge-game-info-campeonato");
				Elements jogoHora = jg.getElementsByClass("ge-game-info-hora");
				Elements jogoMandante = jg.getElementsByClass("sigla sigla-mandante");
				Elements jogoMandantePlacar = jg.getElementsByClass("numero-placar numero-placar-mandante");
				Elements jogoVisitantePlacar = jg.getElementsByClass("numero-placar numero-placar-visitante");
				Elements jogoVisitante = jg.getElementsByClass("sigla sigla-visitante");
				msg += jogoDia.text()+" - "+jogoCamp.text()+" - "+jogoHora.text()+"\n"
						+jogoMandante.attr("title")+" "+jogoMandantePlacar.text()+" X "
						+jogoVisitantePlacar.text()+" "+jogoVisitante.attr("title")+"\n";
			}
		}		*/
		
		return msg;
	}
	public String proximoJogo(Update update) throws IOException{
		String msg = "";
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
				System.out.println(url);
			}
		}
		msg = url;
		/*doc = Jsoup.connect(url).get();
		Elements table = doc.getElementsByClass("jogo-destaque cycle-slide cycle-slide-active");
		for(Element e:table) {
			System.out.println(1);
		}
		
		//if(jogosTable == null){ msg = "Não há jogos previstos";}
		/*else{
			for(Element jogos:jogosTable) {
				Elements jogo = jogos.getElementsByClass("post-lista-jogos__tbody");
			for(Element jg:jogo){							
				Elements jogoDia = jg.getElementsByClass("post-lista-jogos__campeonato");
				Elements jogoCamp;
				String strJogoCamp = jogoDia.toString();
				System.out.println(strJogoCamp);
				Elements jogoHora = jg.getElementsByClass("ge-game-info-hora");
				Elements jogoMandante = jg.getElementsByClass("post-lista-jogos__org-name post-lista-jogos__org-name--mandante");
				Elements jogoMandantePlacar = jg.getElementsByClass("numero-placar numero-placar-mandante");
				Elements jogoVisitantePlacar = jg.getElementsByClass("numero-placar numero-placar-visitante");
				Elements jogoVisitante = jg.getElementsByClass("post-lista-jogos__org-name post-lista-jogos__org-name--visitante");
				msg += strJogoCamp+" - "+jogoMandante.attr("title")+" "
						+" X "+jogoVisitante.attr("title")+"\n";
				System.out.println(strJogoCamp);
			}
		}
		}*/
		System.out.println(msg+"Encontrado");
		return msg;
	}
	
	public String retornaLinkTime(String time) throws IOException{
		String msg = "";
		url = "";
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(time.toLowerCase())){
				url = t.attr("href");
				System.out.println(url);
			}
		}
		return url;
	}
}

