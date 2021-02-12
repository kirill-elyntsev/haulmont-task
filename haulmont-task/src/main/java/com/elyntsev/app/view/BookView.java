package com.elyntsev.app.view;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.elyntsev.app.entity.Author;
import com.elyntsev.app.entity.Book;
import com.elyntsev.app.entity.Book;
import com.elyntsev.app.repository.BookRepository;
import com.elyntsev.app.service.author.AuthorService;
import com.elyntsev.app.service.book.BookService;
import com.elyntsev.app.view.component.BookEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class BookView extends VerticalLayout {

	private BookService bookService;

	private BookEditor bookEditor;

	private Grid<Book> grid;

	private HorizontalLayout toolbar;

	private Button addButton;

	private TextField nameFilter = new TextField();
	private TextField publisherFilter = new TextField();
	private TextField authorFilter = new TextField();

	@Autowired
	public BookView(BookService bookService, BookEditor bookEditor) {
		this.bookService = bookService;
		this.bookEditor = bookEditor;

		nameFilter.setPlaceholder("Filter by name...");
		nameFilter.setClearButtonVisible(true);
		nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
		nameFilter.addValueChangeListener(e -> updateListByName(nameFilter.getValue()));

		authorFilter.setPlaceholder("Filter by author...");
		authorFilter.setClearButtonVisible(true);
		authorFilter.setValueChangeMode(ValueChangeMode.EAGER);
		authorFilter.addValueChangeListener(e -> updateListByAuthor(authorFilter.getValue()));

		publisherFilter.setPlaceholder("Filter by publisher...");
		publisherFilter.setClearButtonVisible(true);
		publisherFilter.setValueChangeMode(ValueChangeMode.EAGER);
		publisherFilter.addValueChangeListener(e -> updateListByPublisher(publisherFilter.getValue()));

		this.grid = new Grid<>(Book.class);
		this.addButton = new Button("Add new Book", VaadinIcon.PLUS.create());
		this.toolbar = new HorizontalLayout(addButton, nameFilter, authorFilter, publisherFilter);

		add(toolbar, grid, bookEditor);

		grid.setItems(bookService.findAll());
		grid.addComponentColumn(e -> new Button("Edit", VaadinIcon.EDIT.create(), evt -> {
			bookEditor.openEditorDialog(e);
		}));

		addButton.addClickListener(e -> bookEditor.opendAddDialog());

	}

	public void updateListByName(String value) {
		if (nameFilter.isEmpty()) {
			grid.setItems(bookService.findAll());
		} else {
			grid.setItems(bookService.findByName(value));
		}

	}

	public void updateListByPublisher(String value) {
		if (publisherFilter.isEmpty()) {
			grid.setItems(bookService.findAll());
		} else {
			grid.setItems(bookService.findByPublisher(value));
		}
	}

	public void updateListByAuthor(String value) {

		List<Book> listSortedByAuthor = bookService.findAll();

		if (authorFilter.isEmpty()) {
			grid.setItems(bookService.findAll());
		} else {
			for (Iterator<Book> iter = listSortedByAuthor.iterator(); iter.hasNext();) {
				Book b = iter.next();
				if (!value.equals(getAuthorName(b))) {
					iter.remove();
				}
			}
			grid.setItems(listSortedByAuthor);

		}

	}
	
	public String getAuthorName(Book book) {	
		Author author = book.getAuthor();
		return author.getName() + " "+ author.getSurname() + " " + author.getPatronymic();	
	}

}
