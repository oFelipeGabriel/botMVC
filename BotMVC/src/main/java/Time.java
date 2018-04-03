import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengrad.telegrambot.model.Update;

public class Time {
	String url_times = "http://globoesporte.globo.com/futebol/times/";
		
	
	public String searchUltimoJogo(Update update) throws IOException{
		String msg = "";
		String url = "";
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
			}
		}
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
		}		
		
		return msg;
	}
	public String proximoJogo(Update update) throws IOException{
		String msg = "";
		String url = "";
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
			}
		}
		doc = Jsoup.connect(url).get();
		Elements jogos = doc.getElementsByClass("jogo vigente");
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
		jogos = doc.getElementsByClass("jogo proximo");
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
		}		
		return msg;
	}
}

