import java.io.IOException;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchClassificacao implements ControllerSearch{
	
	private Model model;
	private View view;
	
	public ControllerSearchClassificacao(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void search(Update update){
		view.sendTypingMessage(update);
		try {
			model.searchClassificacao(update);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}