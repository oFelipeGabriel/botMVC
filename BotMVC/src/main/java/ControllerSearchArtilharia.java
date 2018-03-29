import java.io.IOException;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchArtilharia implements ControllerSearch{
	
	private Model model;
	private View view;
	
	public ControllerSearchArtilharia(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void search(Update update){
		view.sendTypingMessage(update);
		try {
			model.searchArtilheiro(update);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}