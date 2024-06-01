package life.wewu.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Plant {

	private int plantNo;
	private String plantName;
	private String plantLevl;
	private String plantImg;
	private int plantExp;
	
}
