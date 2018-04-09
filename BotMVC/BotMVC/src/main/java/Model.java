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
	
	private static Model uniqueInstance;
	String msg;
	Time time = new Time();
	Campeonato camp = new Campeonato();
	
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
		
	public void searchArtilheiro(Update update) throws IOException{
		msg = "";
		msg = camp.searchArtilheiros(update);
		
		if(msg != null){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "Não encontrado");
		}
		
	}
	
	public void searchUltimoJogo(Update update) throws IOException{	
		String msg = "";
		msg = time.searchUltimoJogo(update);
			
		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "Não encontrado");
		}
	}
		
		
	
	public void searchProximoJogo(Update update) throws IOException{
		msg = "";
		System.out.println("Encontrado time");
		msg = time.proximoJogo(update);
		
		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "Não encontrado");
		}
		System.out.println(msg);
	}
	
		

	public void searchClassificacao(Update update) throws IOException{
		msg = "";
		msg = camp.searchClassificacao(update);

		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "Não encontrado");
		}
		
}
	

}
