import java.io.IOException;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchProximoJogo implements ControllerSearch{
	
	private Model model;
	private View view;
	
	public ControllerSearchProximoJogo(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void search(Update update) throws IOException{
		view.sendTypingMessage(update);
		model.searchProximoJogo(update);
	}

}