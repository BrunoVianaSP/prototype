package application.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeamController {

	
	private static final Logger log = LoggerFactory.getLogger(TeamController.class);

	
    public static final String TEAM_REGISTRATION = "team/team-registration";
    
    public static final String TEAM_MATCH_PREFERENCES = "team/team-match-preferences";
    

    @RequestMapping("/team")
    public String registerTeam() {
    	log.info("registerTeam CALL");
        return TEAM_REGISTRATION;
    }
    
    @RequestMapping("/team/match-preferences")
    public String registerTeamMatchPreferences() {
    	log.info("registerTeamMatchPreferences CALL");
        return TEAM_MATCH_PREFERENCES;
    }
}
