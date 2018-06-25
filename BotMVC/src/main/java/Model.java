import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengrad.telegrambot.model.Update;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Model implements Subject{
	
	private List<Observer> observers = new LinkedList<Observer>();
	ObjectContainer usuarios = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "usuarios.db4o");		
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
	public void searchLink(Update update, String time) throws IOException{
		msg = "";
		msg = this.time.retornaLinkTime(time);
		
		if(msg != null){
			this.notifyObservers(update.message().chat().id(), "Veja o link abaixo:");
			this.notifyObservers(update.message().chat().id(), msg);
			this.notifyObservers(update.message().chat().id(), "Ou digite a opção desejada: \n classificação" + 
					"\n artilheiros \n próximo jogo");
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
	public boolean temTimeCadastrado(Long id) {
		usuarios.close();
		Usuario u;
		boolean r = false;
		usuarios = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "usuarios.db4o");
		Query query = usuarios.query();
		query.constrain(Usuario.class);
	    ObjectSet<Usuario> allUsers = query.execute();
		for (int i=0;i<allUsers.size();i++) {				
			u = (Usuario)allUsers.get(i);
			System.out.println(u.getId());
			if(u.getId().equals(id)){
				r = true;
				System.out.println("Tem time");
			}
		}
		usuarios.close();
		return r;
	}
	public void temTime(Update update) throws IOException {		
		System.out.println("Faz busca de time");
		Long id = update.message().chat().id();		
		Usuario u;
		boolean r = false;
		usuarios.close();
		r = temTimeCadastrado(id);
		System.out.println("Tem time: "+r);
		usuarios.close();
		usuarios = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "usuarios.db4o");
		Query query = usuarios.query();
		query.constrain(Usuario.class);
	    ObjectSet<Usuario> allUsers = query.execute();
		for (int i=0;i<allUsers.size();i++) {				
			u = (Usuario)allUsers.get(i);
			System.out.println(u.getId());
			this.notifyObservers(update.message().chat().id(), u.getTime());
			if(u.getId()==id){
				System.out.println("Retorna lista do ultimo jogo do time cadastrado");
			}
		}
		usuarios.close();
	}
	public String retornaTime(Update update) {
		String time = "";
		Usuario u;
		usuarios.close();
		usuarios = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "usuarios.db4o");
		Query query = usuarios.query();
		query.constrain(Usuario.class);
	    ObjectSet<Usuario> allUsers = query.execute();
		for (int i=0;i<allUsers.size();i++) {
			u = (Usuario)allUsers.get(i);
			if(u.getId().equals(update.message().chat().id())){
				time = u.getTime();				
			}
		}
		return time;
	}
	public void addTimeUsuario(Usuario user, Update update) throws IOException{
		msg = "Não cadastrado";
		if(isUserAvailable(user.getId())){
			user.setId(update.message().chat().id());
			user.setTemTime(true);
			user.setTime(update.message().text());
			usuarios.store(user);
			System.out.println("Cadastrado: "+user.getTime());
			usuarios.commit();
			usuarios.close();
			msg = "Time Cadastrado";
		}
		this.notifyObservers(update.message().chat().id(), msg);
	}
	
	public void searchProximoJogo(Update update) throws IOException{
		msg = "Encontrado";
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
	
	public boolean isUserAvailable(Long id){
		usuarios.close();
		boolean retorno = true;
		usuarios = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "usuarios.db4o");
		Query query = usuarios.query();
		query.constrain(Usuario.class);
	    ObjectSet<Usuario> allUsers = query.execute();
	    
	    for(Usuario u:allUsers){
	    	if(u.getId().equals(id)) retorno = false;
	    }	    
	    return retorno;
	}
}
