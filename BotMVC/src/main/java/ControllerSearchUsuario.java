import java.io.IOException;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchUsuario implements ControllerSearch{
	
	private Model model;
	private View view;
	
	public ControllerSearchUsuario(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void search(Update update) throws IOException{
		view.sendTypingMessage(update);
		Usuario user = new Usuario();
		model.addTimeUsuario(user, update);
	}
	public void cadastrar(Update update) throws IOException{
		Usuario user = new Usuario();
		model.addTimeUsuario(user, update);
	}
	public boolean verificaTime(Update update) {
		boolean b = false;
		b = model.temTimeCadastrado(update.message().chat().id());
		return b;		
	}
	public String retornarTime(Update update) {
		String time = "";
		time = model.retornaTime(update);
		return time;
	}
	public void retornaLink(Update update,String time) throws IOException {
		view.sendTypingMessage(update);
		model.searchLink(update, time);
	}
}