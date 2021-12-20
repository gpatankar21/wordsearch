package com.myprojects.wordsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service

public class WordSearchService {
		
	public class Coordinates
	{
		int x;
		int y;
		
		public Coordinates(int x, int y) {
			
			this.x=x;
			this.y=y;
		}
		
	}
	
	enum Direction 
	{
		HORIZONTAL,
		VERTICAL,
		DIAGONAL,
		HORIZONTAL_INVERSE,
		VERTICAL_INVERSE,
		DIAGONAL_INVERSE
	};
	
	
	public char[][] generateGrid(int gridSize, List<String>words)
	{
		List<Coordinates> coordinate = new ArrayList<>();
		char[][] grid = new char[gridSize][gridSize];
		
		for(int i=0;i<gridSize;i++)
		{
			for(int j=0;j<gridSize;j++)
			{
				coordinate.add(new Coordinates(i,j));
				grid[i][j]='_';
			}
		}
		
		Collections.shuffle(coordinate);
		for(String word: words)
		{
			
			for(Coordinates single_coordinate : coordinate)
			{
				int x = single_coordinate.x;
				int y = single_coordinate.y;
				
				Direction selectedDirection = getDirectionForFit(grid, word, single_coordinate);
				
				if(selectedDirection!=null)
				{
					switch(getDirectionForFit(grid, word, single_coordinate))
					{
					case HORIZONTAL:
						for(char c : word.toCharArray())
						{
							grid[x][y++]=c;
						}
						break;
					case VERTICAL:
						for(char c : word.toCharArray())
						{
							grid[x++][y]=c;
						}
						break;
					case DIAGONAL:
						for(char c : word.toCharArray())
						{
							grid[x++][y++]=c;
						}
						break;
					case HORIZONTAL_INVERSE:
						for(char c : word.toCharArray())
						{
							grid[x][y--]=c;
						}
						break;
					case VERTICAL_INVERSE:
						for(char c : word.toCharArray())
						{
							grid[x--][y]=c;
						}
						break;
					case DIAGONAL_INVERSE:
						for(char c : word.toCharArray())
						{
							grid[x--][y--]=c;
						}
						break;
					}
					break;
				}
			}
		}
		randomFillGrid(grid);
		return grid;
	}

	public Direction getDirectionForFit(char[][] grid, String word, Coordinates coordinate)
	{
		List<Direction>directions = Arrays.asList(Direction.values());
		Collections.shuffle(directions);
		for(Direction direction : directions)
		{
			if(checkFit(grid,word,coordinate,direction))
			{
				return direction;
			}
		}
		return null;
	}
	
	public boolean checkFit(char[][] grid,String word, Coordinates coordinate, Direction direction)
	{
		int gridSize = grid[0].length;
		switch(direction)
		{
			case HORIZONTAL:
				if(coordinate.y + word.length() > gridSize) return false;
				for(int i=0 ; i<word.length(); i++)
				{
					char letter = grid[coordinate.x][coordinate.y + i];
					if (letter!='_' && letter!=word.charAt(i)) return false;
				}
				break;
				
			case VERTICAL:
				if(coordinate.x + word.length() > gridSize) return false;
				for(int i=0 ; i<word.length(); i++)
				{
					char letter = grid[coordinate.x+i][coordinate.y];
					if (letter!='_' && letter!=word.charAt(i)) return false;
				}
				break;				
				
			case DIAGONAL:
				if(coordinate.x + word.length() > gridSize || coordinate.y + word.length() > gridSize) return false;
				for(int i=0 ; i<word.length(); i++)
				{
					char letter = grid[coordinate.x + i][coordinate.y + i];
					if (letter!='_' && letter!=word.charAt(i)) return false;
				}
				break;
				
			case HORIZONTAL_INVERSE:
				if(coordinate.y  < word.length()) return false;
				for(int i=0 ; i<word.length(); i++)
				{
					char letter = grid[coordinate.x][coordinate.y - i];
					if (letter!='_' && letter!=word.charAt(i)) return false;
				}
				break;
				
			case VERTICAL_INVERSE:
				if(coordinate.x < word.length()) return false;
				for(int i=0 ; i<word.length(); i++)
				{
					char letter = grid[coordinate.x - i][coordinate.y];
					if (letter!='_' && letter!=word.charAt(i)) return false;
				}
				break;
				
			case DIAGONAL_INVERSE:
				if(coordinate.x < word.length() || coordinate.y < word.length()) return false;
				for(int i=0 ; i<word.length(); i++)
				{
					char letter = grid[coordinate.x - i][coordinate.y - i];
					if (letter!='_' && letter!=word.charAt(i)) return false;
				}
				break;
		}
		
		return true;
	}
	
	
	
	
	private void randomFillGrid(char[][] grid)
	{
		int gridSize = grid[0].length;
		String allCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0;i<gridSize;i++)
		{
			for(int j=0;j<gridSize;j++)
			{
				if(grid[i][j]=='_')
				{
					int random = ThreadLocalRandom.current().nextInt(0,allCaps.length());
					grid[i][j]=allCaps.charAt(random);
				}
					
			}
		}
		
	}
	
	public void displayGrid(char grid[][])
	{
		int gridSize = grid[0].length;
		for(int i=0;i<gridSize;i++)
		{
			for(int j=0;j<gridSize;j++)
			{
				System.out.print(grid[i][j] + " ");
			}
			System.out.println("");
		}
	}

}
