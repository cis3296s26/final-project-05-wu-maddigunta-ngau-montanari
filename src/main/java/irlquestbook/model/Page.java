package irlquestbook.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.StringProperty;

public class Page {

	// list of questlines that are on this page
	private List<Questline> questlines;
	private StringProperty title;

	// constructor to initialize the page object
	public Page(List<Questline> questlines, StringProperty title) {
		this.questlines = questlines;
		this.title = title;
	}

	// method to return the title of the page
    public StringProperty pageNameProperty() {
        return this.title;
    }


	// method to set the title of the page
	public void setPageName(StringProperty title) {
		this.title = title;
	}

	// method quest getter 
	public List<Questline> getQuestlines() {
		return questlines;
	}

	// add questline to the page
	public void addQuestline(Questline questline) {
		this.questlines.add(questline);
	}

	// remove questline from the page
	public void removeQuestline(Questline questline) {
		this.questlines.remove(questline);
	}
}
