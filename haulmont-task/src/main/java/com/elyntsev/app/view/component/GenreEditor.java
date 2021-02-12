package com.elyntsev.app.view.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.elyntsev.app.entity.Book;
import com.elyntsev.app.entity.Genre;
import com.elyntsev.app.service.book.BookService;
import com.elyntsev.app.service.genre.GenreService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class GenreEditor extends VerticalLayout {

	private GenreService genreService;

	private BookService bookService;

	private Dialog addDialog;

	private Dialog editorDialog;

	private Dialog statisticDialog;

	private Genre genre;

	@Autowired
	public GenreEditor(GenreService genreService, BookService bookService) {

		this.genreService = genreService;
		this.bookService = bookService;

		this.addDialog = new Dialog();

		this.editorDialog = new Dialog();

		editorDialog.add(new Text("Edit"));

	}

	public void openAddDialog() {
		TextField name = new TextField("Genre name");
		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");

		cancelButton.addClickListener(g -> this.addDialog.close());

		saveButton.addClickListener(g -> {

			if (name.isEmpty()) {
				new Dialog(new Text("Empty field")).open();
			} else {
				save(name);
				this.addDialog.close();
				UI.getCurrent().getPage().reload();
			}
		});

		HorizontalLayout addDialogLayout = new HorizontalLayout(saveButton, cancelButton);
		addDialog.add(name);
		addDialog.add(addDialogLayout);

		addDialog.open();
	}

	public void save(TextField name) {
		genre = new Genre(name.getValue());
		genreService.save(genre);
	}

	public void update(TextField name, Long id) {
		Genre genre = genreService.findById(id);
		genre.setName(name.getValue());
		genreService.save(genre);
	}

	public void delete(Long id) {
		genreService.deleteById(id);
	}

	// Edit
	public void openEditorDialog(Genre genre) {

		editorDialog = new Dialog();
		FormLayout editorForm = new FormLayout();

		TextField name = new TextField();

		name.setLabel("Name");

		name.setValue(genre.getName());

		editorForm.add(name);
		editorDialog.add("Id: " + genre.getId().toString() + ", ");
		editorDialog.add("Name: " + genre.getName().toString());

		editorDialog.add(editorForm);

		Button updateButton = new Button("Update");
		updateButton.addClickListener(c -> {
			update(name, genre.getId());
			this.addDialog.close();
			UI.getCurrent().getPage().reload();
		});

		Button deleteButton = new Button("Delete");
		deleteButton.addClickListener(c -> {
			delete(genre.getId());
			this.addDialog.close();
			UI.getCurrent().getPage().reload();
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(c -> this.editorDialog.close());

		HorizontalLayout addEditLayout = new HorizontalLayout(updateButton, deleteButton, cancelButton);
		editorDialog.add(addEditLayout);

		editorDialog.open();

	}

	public void openStatisticDialog() {

		this.statisticDialog = new Dialog();

		VerticalLayout layout = new VerticalLayout();

		List<Book> booksList = bookService.findAll();

		List<String> genreList = new ArrayList<String>();

		for (int i = 0; i < booksList.size(); i++) {
			genreList.add(booksList.get(i).getGenre().getName());
		}

		Map<String, Integer> map = new HashMap<>();

		for (String book : genreList) {
			Integer i = map.get(book);
			map.put(book, (i == null ? 1 : i + 1));
		}

		statisticDialog.add("Statistics:");

		for (Map.Entry<String, Integer> val : map.entrySet()) {

			layout.add(new Label(val.getKey() + ": " + val.getValue() + " books"));

		}

		statisticDialog.add(layout);
		statisticDialog.open();
	}
}
