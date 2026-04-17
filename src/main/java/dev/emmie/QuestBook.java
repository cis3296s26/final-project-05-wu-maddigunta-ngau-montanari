package dev.emmie;

import java.util.ArrayList;
import java.util.List;

public class QuestBook {

	// list of pages that are in this quest book
	private List<Page> pages;

	// constructor to initialize the quest book object
	public QuestBook() {
		this.pages = new ArrayList<>();
	}

	// overloaded constructor to initialize with an existing list
	public QuestBook(List<Page> pages) {
		this.pages = pages;
	}

	// method to return the list of pages
	public List<Page> getPages() {
		return this.pages;
	}

	// add a page to the book
	public void addPage(Page page) {
		this.pages.add(page);
	}

	// remove a page from the book
	public void removePage(Page page) {
		this.pages.remove(page);
	}

	// method to get the total number of pages
	public int getPageCount() {
		return this.pages.size();
	}
}