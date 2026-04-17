package dev.emmie;
import java.util.List;

public class Page {

	// list of questlines that are on this page
	private List<Questline> questlines;
	private String title;

	// constructor to initialize the page object
	public void createPage(List<Questline> questlines, String title) {
		this.questlines = questlines;
		this.title = title;
	}

	// method to return the title of the page
	public String pageName(String title) {
		return title;
	}

	// method to set the title of the page
	public String setPageName(String title) {
		this.title = title;
		return title;
	}

	// method to delete the page
	public void deletePage() {
		this.questlines = null;
		this.title = null;
	}

	
}
