
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Artilheiros {
	public String artilharia() throws IOException{
		Document doc = Jsoup.connect("http://globoesporte.globo.com/sp/futebol/campeonato-paulista/").get();
		int cont = 0;
		String msg = "";
		Elements posicao = doc.getElementsByClass("artilheiros-linha-corpo");
		for(Element pos:posicao) {
			if(cont<10) {
				Elements nome = pos.getElementsByClass("artilheiro-foto");
				Elements time = pos.getElementsByClass("artilheiro-escudo");
				Elements gols = pos.getElementsByClass("artilheiro-gols");
				msg += (cont+1)+" - "+nome.attr("alt")+" - "+time.attr("alt")+" - "+gols.text()+" gols\n";	
				cont++;
			}
			
		}
		System.out.println("msg");
		return msg;
	}
}