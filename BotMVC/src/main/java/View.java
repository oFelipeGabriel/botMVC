import java.io.IOException;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class View implements Observer{
	//FelipeESII: 507831327:AAFJxZPh-LNKQbrh9KI7yuk_1letMAz3I7k
	//BallsOfThrones: 437136343:AAGDevHTmnLcIWrMbMyx286bUZTb8jbyqTg	
	TelegramBot bot = TelegramBotAdapter.build("507831327:AAFJxZPh-LNKQbrh9KI7yuk_1letMAz3I7k");

	//Object that receives messages
	GetUpdatesResponse updatesResponse;
	//Object that send responses
	SendResponse sendResponse;
	//Object that manage chat actions like "typing action"
	BaseResponse baseResponse;
			
	
	int queuesIndex=0;
	
	ControllerSearch controllerSearch; //Strategy Pattern -- connection View -> Controller
	
	boolean searchBehaviour = false;
	boolean temTime = false;
	boolean verificou = false;
	String time = "";
	
	private Model model;
	
	public View(Model model){
		this.model = model; 
	}
	
	public void setControllerSearch(ControllerSearch controllerSearch){ //Strategy Pattern
		this.controllerSearch = controllerSearch;
	}
	
	public void receiveUsersMessages() throws IOException {

		
		
		//infinity loop
		while (true){
		
			//taking the Queue of Messages
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(queuesIndex));
			
			//Queue of messages
			List<Update> updates = updatesResponse.updates();
			
			//taking each message in the Queue
			for (Update update : updates) {
				ControllerSearchUsuario controllerUsuario = new ControllerSearchUsuario(model, this);
				temTime = controllerUsuario.verificaTime(update);
				//updating queue's index
				queuesIndex = update.updateId()+1;
				
				if(this.searchBehaviour==true){
					this.callController(update);
					this.verificou=false;
				}
				
				else if(this.temTime==true && this.searchBehaviour==false && this.verificou==false) {
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Olá"));
					time = controllerUsuario.retornarTime(update);
					controllerUsuario.retornaLink(update, time);
					this.verificou=true;
				}
				
				else if(update.message().text().toLowerCase().equals("classificação")){
					setControllerSearch(new ControllerSearchClassificacao(model, this));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual campeonato?"));
					this.searchBehaviour = true;
				}
				else if(update.message().text().toLowerCase().equals("artilheiros")){
					setControllerSearch(new ControllerSearchArtilharia(model, this));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual campeonato?"));
					this.searchBehaviour = true;
				}
				else if(update.message().text().toLowerCase().equals("cadastrar")){
					setControllerSearch(new ControllerSearchUsuario(model, this));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual time?"));
					this.searchBehaviour = true;
				}
				else if(update.message().text().toLowerCase().equals("próximo jogo")){
					setControllerSearch(new ControllerSearchProximoJogo(model, this));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual time?"));
					this.searchBehaviour = true;
				}
				else if(update.message().text().toLowerCase().equals("último jogo")){
					setControllerSearch(new ControllerSearchUltimoJogo(model, this));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Qual time?"));
					this.searchBehaviour = true;
				}
				else				
				{
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Digite: \nclassificação\n"
							+"artilheiros\npróximo jogo \n ou Digite cadastrar para escolher seu Time"));
				}
				
				
				
			}

		}
		
		
	}
	
	
	public void callController(Update update) throws IOException{
		this.controllerSearch.search(update);
	}
	
	public void update(long chatId, String studentsData){
		sendResponse = bot.execute(new SendMessage(chatId, studentsData));
		this.searchBehaviour = false;
	}
	
	public void sendTypingMessage(Update update){
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
	}
	

}