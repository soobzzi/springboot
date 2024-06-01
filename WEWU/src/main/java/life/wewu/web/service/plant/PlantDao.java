package life.wewu.web.service.plant;

import java.util.List;

import life.wewu.web.domain.MyPlant;
import life.wewu.web.domain.Plant;
import life.wewu.web.domain.Quest;

public interface PlantDao {
	
	public void addQuest(Quest quest) throws Exception;
	
	public void deleteQuest(int questNo) throws Exception;
	
	public void updateQuest(Quest quest) throws Exception;
	
	public List<Quest> getQuest(Quest quest) throws Exception;
	
	
	public void addPlant(Plant plant) throws Exception;
	
	public void deletePlant(int plantNo) throws Exception;
	
	public void updatePlant(Plant plant) throws Exception;
	
	public Plant getPlant(int PlantNo) throws Exception;
	
	public List<Plant> getPlantList(Plant plant) throws Exception;
	
	
	public MyPlant selectRandomPlant(MyPlant myPlant) throws Exception;
	
	public void updateMyPlant(MyPlant myPlant) throws Exception;
	
	public MyPlant getMyPlant(MyPlant myPlant) throws Exception;
	
	public List<MyPlant> getMyPlantList(MyPlant myPlant) throws Exception;
	
	public MyPlant deleteMyPlant(int myPlantNo) throws Exception;
	
	
	 void useItem(int itemNo) throws Exception;
	     
	 void fileUpload(String filePath) throws Exception;
	    
	 void completeQuest(int questNo) throws Exception; 
	    
	 void donatePlant(int plantNo ,String nickname) throws Exception;
	 
	 String getWeather(String location) throws Exception;
	
	
	//useItem
	//getWeather
	
	//fileUpload
	//CompltetQuest
	//DonatePlant
	
	

}
