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
public class Quest {
	
	private int questNo;
	private String questContents;
	private String questState;
	private Date questRegDate;
	private String questTarget;
	private int questTargetCnt;
	private int questReward;

}
