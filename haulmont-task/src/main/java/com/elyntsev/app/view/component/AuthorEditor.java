package com.elyntsev.app.view.component;

import org.springframework.beans.factory.annotation.Autowired;

import com.elyntsev.app.entity.Author;
import com.elyntsev.app.service.author.AuthorService;
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
public class AuthorEditor extends VerticalLayout {

	private Dialog addDialog;

	private Dialog editorDialog;

	private Author author;

	private AuthorService authorService;

	@Autowired
	public AuthorEditor(AuthorService authorService) {
		this.authorService = authorService;

	}

	public void opendAddDialog() {

		addDialog = new Dialog();

		VerticalLayout vLaout = new VerticalLayout();
		TextField name = new TextField("Name");
		TextField surname = new TextField("Surname");
		TextField patronymic = new TextField("Patronymic");
		vLaout.add(name, surname, patronymic);

		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");

		cancelButton.addClickListener(g -> this.addDialog.close());
		saveButton.addClickListener(g -> {
			save(name, surname, patronymic);
			this.addDialog.close();
			UI.getCurrent().getPage().reload();
		});

		HorizontalLayout addDialogLayout = new HorizontalLayout(saveButton, cancelButton);
		addDialog.add(vLaout);
		addDialog.add(addDialogLayout);
		addDialog.open();
	}

	public void openEditorDialog(Author author) {

		editorDialog = new Dialog();

		FormLayout editorForm = new FormLayout();

		TextField name = new TextField("Name");
		TextField surname = new TextField("Surname");
		TextField patronymic = new TextField("Patronymic");

		name.setValue(author.getName());
		surname.setValue(author.getSurname());
		patronymic.setValue(author.getPatronymic());

		editorForm.add(name, surname, patronymic);
		editorDialog.add("Id: " + author.getId() + ", ");
		editorDialog.add("Name: " + author.getName() + ", ");
		editorDialog.add("Surname: " + author.getSurname() + ", ");
		editorDialog.add("Patronymic: " + author.getPatronymic());

		editorDialog.add(editorForm);

		Button updateButton = new Button("Update");
		updateButton.addClickListener(c -> {
			update(name, surname, patronymic, author.getId());
			this.editorDialog.close();
			UI.getCurrent().getPage().reload();
		});

		Button deleteButton = new Button("Delete");
		deleteButton.addClickListener(c -> {
			delete(author.getId());
			this.editorDialog.close();
			UI.getCurrent().getPage().reload();
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(c -> this.editorDialog.close());

		HorizontalLayout addEditLayout = new HorizontalLayout(updateButton, deleteButton, cancelButton);
		editorDialog.add(addEditLayout);

		editorDialog.open();
	}

	public void save(TextField name, TextField surname, TextField patronymic) {
		author = new Author(name.getValue(), surname.getValue(), patronymic.getValue());
		authorService.save(author);
	}

	public void update(TextField name, TextField surname, TextField patronymic, Long id) {
		Author author = authorService.findById(id);
		author.setName(name.getValue());
		author.setSurname(surname.getValue());
		author.setPatronymic(patronymic.getValue());
		authorService.save(author);
	}

	public void delete(Long id) {
		authorService.deleteById(id);
	}
}
