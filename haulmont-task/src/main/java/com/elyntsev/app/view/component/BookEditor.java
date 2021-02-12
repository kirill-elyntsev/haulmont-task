package com.elyntsev.app.view.component;

import org.springframework.beans.factory.annotation.Autowired;

import com.elyntsev.app.entity.Author;
import com.elyntsev.app.entity.Book;
import com.elyntsev.app.entity.Genre;
import com.elyntsev.app.service.author.AuthorService;
import com.elyntsev.app.service.book.BookService;
import com.elyntsev.app.service.genre.GenreService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class BookEditor extends VerticalLayout {

	private Dialog addDialog;

	private Dialog editorDialog;

	private Book book;

	private BookService bookService;

	private AuthorService authorService;

	private GenreService genreService;

	@Autowired
	public BookEditor(BookService bookService, AuthorService authorService, GenreService genreService) {
		this.bookService = bookService;
		this.authorService = authorService;
		this.genreService = genreService;
	}

	public void opendAddDialog() {

		addDialog = new Dialog();

		VerticalLayout vLaout = new VerticalLayout();
		TextField nameField = new TextField("Name");
		TextField authorField = new TextField("Author Id");
		TextField genreField = new TextField("Genre Id");
		TextField publisherField = new TextField("Publisher");
		TextField yearField = new TextField("Year");
		TextField cityField = new TextField("City");

		vLaout.add(nameField, authorField, genreField, publisherField, yearField, cityField);

		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");

		cancelButton.addClickListener(e -> this.addDialog.close());
		saveButton.addClickListener(e -> {
			save(nameField, authorField, genreField, publisherField, yearField, cityField);
			this.addDialog.close();
			UI.getCurrent().getPage().reload();
		});

		HorizontalLayout addDialogLayout = new HorizontalLayout(saveButton, cancelButton);
		addDialog.add(vLaout);
		addDialog.add(addDialogLayout);
		addDialog.open();
	}

	public void openEditorDialog(Book book) {

		editorDialog = new Dialog();

		VerticalLayout vLaout = new VerticalLayout();
		TextField nameField = new TextField("Name");
		TextField authorField = new TextField("Author Id");
		TextField genreField = new TextField("Genre Id");
		TextField publisherField = new TextField("Publisher");
		TextField yearField = new TextField("Year");
		TextField cityField = new TextField("City");

		FormLayout editorForm = new FormLayout();
		editorForm.add(nameField, authorField, genreField, publisherField, yearField, cityField);

		nameField.setValue(book.getName());
		authorField.setValue(book.getAuthor().getId().toString());
		genreField.setValue(book.getGenre().getId().toString());
		publisherField.setValue(book.getPublisher());
		yearField.setValue(book.getYear().toString());
		cityField.setValue(book.getCity());

		editorForm.add(nameField, authorField, genreField, publisherField, yearField, cityField);
		editorDialog.add("Id: " + book.getId() + ", ");
		editorDialog.add("Name: " + book.getName() + ", ");
		editorDialog.add("Author: " + book.getAuthor().getName() + " " + book.getAuthor().getSurname() + ", ");
		editorDialog.add("Genre: " + book.getGenre().getName() + ", ");
		editorDialog.add("Publisher: " + book.getPublisher() + ", ");
		editorDialog.add("Year: " + book.getYear() + ", ");
		editorDialog.add("City: " + book.getCity());

		editorDialog.add(editorForm);

		Button updateButton = new Button("Update");

		updateButton.addClickListener(c -> {
			update(nameField, authorField, genreField, publisherField, yearField, cityField, book.getId());
			this.editorDialog.close();
			UI.getCurrent().getPage().reload();
		});

		Button deleteButton = new Button("Delete");

		deleteButton.addClickListener(c -> {
			delete(book.getId());
			this.editorDialog.close();
			UI.getCurrent().getPage().reload();
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(c -> this.editorDialog.close());

		HorizontalLayout addEditLayout = new HorizontalLayout(updateButton, deleteButton, cancelButton);
		editorDialog.add(addEditLayout);

		editorDialog.open();
	}

	public void save(TextField name, TextField author, TextField genre, TextField publisher, TextField year,
			TextField city) {

		Author theAuther = authorService.findById(Long.parseLong(author.getValue()));
		Genre theGenre = genreService.findById(Long.parseLong(genre.getValue()));

		book = new Book(name.getValue(), theAuther, theGenre, publisher.getValue(), Integer.parseInt(year.getValue()),
				city.getValue());
		bookService.save(book);

	}

	public void update(TextField nameField, TextField authorField, TextField genreField, TextField publisherField,
			TextField yearField, TextField cityField, Long id) {

		Book book = bookService.findById(id);
		Author theAuther = authorService.findById(Long.parseLong(authorField.getValue()));
		Genre theGenre = genreService.findById(Long.parseLong(genreField.getValue()));

		book.setName(nameField.getValue());
		book.setAuthor(theAuther);
		book.setGenre(theGenre);
		book.setPublisher(publisherField.getValue());

		try {
			book.setYear(Integer.parseInt(yearField.getValue()));
		} catch (NumberFormatException e) {
			new Dialog(new Text("Incorrect year")).open();
		}
		book.setCity(cityField.getValue());

		bookService.save(book);
	}

	public void delete(Long id) {
		bookService.deleteById(id);
	}

}
