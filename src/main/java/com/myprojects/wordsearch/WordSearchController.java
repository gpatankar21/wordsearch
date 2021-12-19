package com.myprojects.wordsearch;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class WordSearchController {
	
	@Autowired
	WordSearchService service;
	

	@GetMapping("/wordsearch")	
	public String getGrid(@RequestParam int gridSize, @RequestParam String words)
	{
		List<String>list_of_words = Arrays.asList(words.split(","));
		char[][] grid = service.generateGrid(gridSize, list_of_words);
		String gridToString="";
		for(int i=0;i<gridSize;i++)
		{
			for(int j=0;j<gridSize;j++)
			{
				gridToString += grid[i][j] + " ";
			}
			gridToString += "\n";
		}
		
		return gridToString;
	}

}
