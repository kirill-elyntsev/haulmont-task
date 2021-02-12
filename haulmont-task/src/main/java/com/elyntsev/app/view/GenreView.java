package com.elyntsev.app.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.elyntsev.app.entity.Author;
import com.elyntsev.app.entity.Genre;
import com.elyntsev.app.service.genre.GenreService;
import com.elyntsev.app.view.component.GenreEditor;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class GenreView extends VerticalLayout {

	private GenreService genreService;

	private GenreEditor genreEditor;

	private Grid<Genre> grid;

	private HorizontalLayout toolbar;

	private Button addButton;

	private Button statisticButton;

	@Autowired
	public GenreView(GenreService genreService, GenreEditor genreEditor) {

		this.genreService = genreService;
		this.genreEditor = genreEditor;

		this.grid = new Grid<>(Genre.class);
		this.addButton = new Button("Add new genre", VaadinIcon.PLUS.create());
		this.statisticButton = new Button("Show statistic");
		this.toolbar = new HorizontalLayout(addButton, statisticButton);

		add(toolbar, grid, genreEditor);

		grid.setItems(genreService.findAll());
		grid.addComponentColumn(e -> new Button("Edit", VaadinIcon.EDIT.create(), evt -> {
			genreEditor.openEditorDialog(e);
		}));

		addButton.addClickListener(g -> {
			genreEditor.openAddDialog();
			grid.getDataProvider().refreshAll();
		});

		statisticButton.addClickListener(e -> genreEditor.openStatisticDialog());
	}

}
