import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengrad.telegrambot.model.Update;

public class Model implements Subject{
	
	private List<Observer> observers = new LinkedList<Observer>();
	
	private List<Student> students = new LinkedList<Student>();
	private List<Teacher> teachers = new LinkedList<Teacher>();
	
	private static Model uniqueInstance;
	String url_camp = "http://globoesporte.globo.com/futebol/campeonato/";
	String url_times = "http://globoesporte.globo.com/futebol/times/";
	
	private Model(){}
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	
	public void notifyObservers(long chatId, String studentsData){
		for(Observer observer:observers){
			observer.update(chatId, studentsData);
		}
	}
	
	public void addStudent(Student student){
		this.students.add(student);
	}
	
	public void addTeacher(Teacher teacher){
		this.teachers.add(teacher);
	}
	
	public void searchArtilheiro(Update update) throws IOException{
		String url = "";
		Document doc = Jsoup.connect(url_camp).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
			}
		}
		String msg = "";
		doc = Jsoup.connect(url).get();
		int cont = 0;
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
		
		if(msg != null){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "N�o encontrado");
		}
		
	}
	
	public void searchUltimoJogo(Update update) throws IOException{
		String url = "";		
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
			}
		}
		String msg = "";
		doc = Jsoup.connect(url).get();
		int cont = 0;
		Elements jogos = doc.getElementsByClass("jogo anterior");
		if(jogos == null){ msg = "N�o h� jogos previstos";}
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
		
		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "N�o encontrado");
		}
		System.out.println(msg);
	}
	
	public void searchProximoJogo(Update update) throws IOException{
		String url = "";		
		Document doc = Jsoup.connect(url_times).get();
		Elements tagA = doc.getElementsByClass("theme-color");
		for(Element t:tagA){
			if(t.text().toLowerCase().equals(update.message().text().toLowerCase())){
				url = t.attr("href");
			}
		}
		String msg = "";
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
		if(jogos == null){ msg = "N�o h� jogos previstos";}
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
		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "N�o encontrado");
		}
		System.out.println(msg);
	}
	
		

	public void searchClassificacao(Update update) throws IOException{
		String url = "";
		String msg = "";
		int cont = 0;
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
		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "N�o encontrado");
		}
		}
		
}
	public void searchStudent(Update update){
		String studentsData = null;
		for(Student student: students){
			if(student.getName().equals(update.message().text())){
				studentsData = student.getAcademicNumber();
			}
		}
		
		if(studentsData != null){
			this.notifyObservers(update.message().chat().id(), studentsData);
		} else {
			this.notifyObservers(update.message().chat().id(), "Student not found");
		}
		
	}
	
	public void searchTeacher(Update update){
		String teachersData = null;
		for(Teacher teacher:teachers){
			if(teacher.getName().equals(update.message().text())) teachersData = teacher.getField();
		}
		
		if(teachersData != null){
			this.notifyObservers(update.message().chat().id(), teachersData);
		} else {
			this.notifyObservers(update.message().chat().id(), "Teacher not found");
		}
		
	}

}
