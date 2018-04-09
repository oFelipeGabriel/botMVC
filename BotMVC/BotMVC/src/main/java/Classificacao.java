
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Classificacao {
	public String confrontos() throws IOException{
		Document doc = Jsoup.connect("http://globoesporte.globo.com/sp/futebol/campeonato-paulista/").get();
		String msg = "";
		Elements confr = doc.getElementsByClass("placar-jogo");
		for (Element el:confr) {
			Elements mandante = el.getElementsByClass("placar-jogo-equipes-escudo-mandante");
			Elements visitante = el.getElementsByClass("placar-jogo-equipes-escudo-visitante");
			msg += mandante.attr("title")+" x "+visitante.attr("title")+"\n";
		}
		return msg;
	}

}

