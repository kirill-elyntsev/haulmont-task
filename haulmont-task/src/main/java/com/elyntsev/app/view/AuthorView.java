package com.elyntsev.app.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.elyntsev.app.entity.Author;
import com.elyntsev.app.service.author.AuthorService;
import com.elyntsev.app.view.component.AuthorEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class AuthorView extends VerticalLayout{
	
	private AuthorService authorService;
	
	private AuthorEditor authorEditor;
	
	private Grid<Author> grid;
	
	private HorizontalLayout toolbar;
	
	private Button addButton;
	

	@Autowired
	public AuthorView(AuthorService authorService, AuthorEditor authorEditor) {
		this.authorService = authorService;
		this.authorEditor = authorEditor;
		
		this.grid = new Grid<>(Author.class);
		this.addButton = new Button("Add new author", VaadinIcon.PLUS.create());
		this.toolbar = new HorizontalLayout(addButton);
		
		add(toolbar, grid, authorEditor);
		
		grid.setItems(authorService.findAll());
		grid.addComponentColumn(e ->
		new Button("Edit", VaadinIcon.EDIT.create(), evt -> {
			authorEditor.openEditorDialog(e);
		}));
		
		addButton.addClickListener(e -> authorEditor.opendAddDialog());
		
		
		
	}
	
	
	
	

}
