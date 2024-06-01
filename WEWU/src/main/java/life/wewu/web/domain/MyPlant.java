package life.wewu.web.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MyPlant {
	
	private int myPlantNo;
	private String myPlantState;
	private String myPlantName;
	private int myPlantExp;
	private Date plantStartDate;
	private Date plantEndDate;
	private String myPlantLevl;

}
