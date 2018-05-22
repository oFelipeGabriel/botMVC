import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.pengrad.telegrambot.model.Update;


public class Model implements Subject{
	
	private List<Observer> observers = new LinkedList<Observer>();
	ObjectContainer times = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "times.db4o");
	

	private static Model uniqueInstance;
	String msg;
	Time time;
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
		
		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "Não encontrado");
		}
		
	}
	
	public void searchUltimoJogo(Update update) throws IOException{	
		msg = "";		
		time = new Time();
		time.setNome(update.message().text());
		if(addTimeUltimo(time, update)){		
			msg = time.getMsgUltimo();
			System.out.println("Encontrado time");		
			}
			else{
				times.close();
				Time t;
				times = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "times.db4o");
				Query query = times.query();
				query.constrain(Time.class);
			    ObjectSet<Time> allTimes = query.execute();
				for (int i=0;i<allTimes.size();i++) {				
					t = (Time)allTimes.get(i);
					System.out.println(t.nome);
					if(t.nome.equals(update.message().text())&&t.getMsgUltimo()!=null){
						msg = t.msgUltimo;
						System.out.println("Do banco: "+t.msgUltimo);
						break;
					}
					else{
						msg = "Falha no servidor de banco de dados";
					}
				}
			    System.out.println("Do banco de dados");
			    times.close();
		}
			
		if(msg != ""){
			this.notifyObservers(update.message().chat().id(), msg);
		} else {
			this.notifyObservers(update.message().chat().id(), "Não encontrado");
		}
	}
		
		
	
	public void searchProximoJogo(Update update) throws IOException{
		msg = "";
		time = new Time();
		time.setNome(update.message().text());
		if(addTimeProximo(time, update)){
		msg = time.getMsgProximo();
		System.out.println("Encontrado time");		
		}
		else{
			times.close();
			Time t;
			times = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "times.db4o");
			Query query = times.query();
			query.constrain(Time.class);
		    ObjectSet<Time> allTimes = query.execute();
			for (int i=0;i<allTimes.size();i++) {				
				t = (Time)allTimes.get(i);
				System.out.println(t.nome);
				if(t.nome.equals(update.message().text())&&t.getMsgProximo()!=null){
					msg = t.msgProximo;
					System.out.println("Do banco: "+t.msgProximo);
					break;
				}
				else{
					msg = "Falha no servidor de banco de dados";
				}
			}
		    System.out.println("Do banco de dados");
		    times.close();
		}
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
	public boolean addTimeUltimo(Time time, Update update) throws IOException{		
		if(isTimeAvailable(time.getNome())){
			time.setMsgUltimo(time.searchUltimoJogo(update));
			times.store(time);
			times.commit();
			times.close();
			return true;
		}
		return false;
	}
	public boolean addTimeProximo(Time time, Update update) throws IOException{		
		if(isTimeAvailable(time.getNome())){
			time.setMsgProximo(time.proximoJogo(update));
			System.out.println(time.getMsgProximo());
			times.store(time);
			times.commit();
			times.close();
			return true;
		}
		return false;
	}

	public boolean isTimeAvailable(String time){
		times.close();
		times = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "times.db4o");
		Query query = times.query();
		query.constrain(Time.class);
	    ObjectSet<Time> allTimes = query.execute();
	    
	    for(Time t:allTimes){
	    	if(t.getNome().equals(time)) return false;
	    }	    
	    return true;
	}
	

}
