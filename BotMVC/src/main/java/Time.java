import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.pengrad.telegrambot.model.Update;

public class Time {
	String url_times = "http://globoesporte.globo.com/futebol/times/";
	String nome;
	String msgProximo;
	String msgUltimo;
		
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getMsgProximo() {
		return msgProximo;
	}
	public void setMsgProximo(String msgProximo) {
		this.msgProximo = msgProximo;
	}
	public String getMsgUltimo() {
		return msgUltimo;
	}
	public void setMsgUltimo(String msgUltimo) {
		this.msgUltimo = msgUltimo;
	}
	public String searchUltimoJogo(Update update) throws IOException{
		msgUltimo = "";
		String url = "";
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				nome = update.message().text().toLowerCase();
				url = t.attr("href");
			}
		}
		doc = Jsoup.connect(url).get();
		Elements jogos = doc.getElementsByClass("jogo anterior");
		if(jogos == null){ msgUltimo = "Não há jogos encontrados";}
		else{
			for(Element jg:jogos){							
				Elements jogoDia = jg.getElementsByClass("ge-game-info-dia");
				Elements jogoCamp = jg.getElementsByClass("ge-game-info-campeonato");
				Elements jogoHora = jg.getElementsByClass("ge-game-info-hora");
				Elements jogoMandante = jg.getElementsByClass("sigla sigla-mandante");
				Elements jogoMandantePlacar = jg.getElementsByClass("numero-placar numero-placar-mandante");
				Elements jogoVisitantePlacar = jg.getElementsByClass("numero-placar numero-placar-visitante");
				Elements jogoVisitante = jg.getElementsByClass("sigla sigla-visitante");
				msgUltimo += jogoDia.text()+" - "+jogoCamp.text()+" - "+jogoHora.text()+"\n"
						+jogoMandante.attr("title")+" "+jogoMandantePlacar.text()+" X "
						+jogoVisitantePlacar.text()+" "+jogoVisitante.attr("title")+"\n";
			}
		}		
		setMsgUltimo(msgUltimo);
		return msgUltimo;
	}
	public String proximoJogo(Update update) throws IOException{
		msgProximo = "";
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
				msgProximo += jogoDia.text()+" - "+jogoCamp.text()+" - "+jogoHora.text()+"\n"
						+jogoMandante.attr("title")+" "+jogoMandantePlacar.text()+" X "
						+jogoVisitantePlacar.text()+" "+jogoVisitante.attr("title")+"\n";		
			}
		jogos = doc.getElementsByClass("jogo proximo");
		if(jogos == null){ msgProximo = "Não há jogos previstos";}
		else{
			for(Element jg:jogos){							
				Elements jogoDia = jg.getElementsByClass("ge-game-info-dia");
				Elements jogoCamp = jg.getElementsByClass("ge-game-info-campeonato");
				Elements jogoHora = jg.getElementsByClass("ge-game-info-hora");
				Elements jogoMandante = jg.getElementsByClass("sigla sigla-mandante");
				Elements jogoMandantePlacar = jg.getElementsByClass("numero-placar numero-placar-mandante");
				Elements jogoVisitantePlacar = jg.getElementsByClass("numero-placar numero-placar-visitante");
				Elements jogoVisitante = jg.getElementsByClass("sigla sigla-visitante");
				msgProximo += jogoDia.text()+" - "+jogoCamp.text()+" - "+jogoHora.text()+"\n"
						+jogoMandante.attr("title")+" "+jogoMandantePlacar.text()+" X "
						+jogoVisitantePlacar.text()+" "+jogoVisitante.attr("title")+"\n";		
			}
		}
		setMsgProximo(msgProximo);
		return msgProximo;
	}
	
}

