import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Puzzle game, where the user slides tiles around to complete an image
 */
public class SlidingPuzzle extends JFrame
{

    private Cell[] cells = new Cell[9];
    private  ArrayList<Integer> grid = new ArrayList<Integer>(9);
    private final int[] completeGrid = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private JPanel mainPanel;
    private int counter = 0;

    /**
     * Main - Instantiate a SlidingPuzzle object
     * @param args - Commandline parameters, currently unused
     */
    public static void main(String[] args)
    {
        SlidingPuzzle slidingPuzzle = new SlidingPuzzle();
    }

    /**
     * Constructor - Title the window, draw the GUI.
     */
    public SlidingPuzzle()
    {
        super("Sliding Puzzle Game");
        generateGrid();
        drawGUI();
    }

    /**
     * Draw the GUI
     */
    private void drawGUI()
    {
        //Let the program close if the window is closed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Main panel operations
        mainPanel = new JPanel();
        this.setContentPane(mainPanel);
        mainPanel.setPreferredSize(new Dimension(920, 920));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridLayout(3, 3));

        //Give each cell images an image
        for(int i = 0; i < 9; i++)
        {
            if(i != 8)
            {
                cells[i] = new Cell(new ImageIcon(getClass().getResource((i + 1) + ".png")), i);
                //cells[i] = new Cell(new ImageIcon((i + 1) + ".png"), i);
            }
            else
            {
                cells[i] = new Cell(null, i);
            }
            cells[i].getLabel().addMouseListener(new SlideListener(this));
        }

        addLabels();

        //Resize everything, and render to the screen
        this.pack();
        this.setVisible(true);
    }

    private void generateGrid()
    {
        do
        {
            grid.clear();
            while(grid.size() < 9)
            {
                Random rand = new Random();
                int number = rand.nextInt(9);
                if(!grid.contains(number))
                {
                    grid.add(number);
                }
            }
       }while(!checkSolvable());
        System.out.println(checkSolvable());
    }

    private boolean checkSolvable()
    {
        int numberOfInversions = 0;
        for(int current:grid)
        {
            for(int i = grid.indexOf(current); i < grid.size(); i++)
            {
                if(current > grid.get(i) && current != 8 && grid.get(i) != 8)
                {
                    numberOfInversions++;
                }
            }
        }

        //http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
        //( (grid width odd) && (#inversions even) )  ||  ( (grid width even) && ((blank on odd row from bottom) == (#inversions even)) )
        //return (((true) && (numberOfInversions%2 == 0)) || (false) && ((blankLocation)) == (numberOfInversions%2 == 0));
        return (numberOfInversions%2 == 0);
    }

    private void addLabels()
    {
        mainPanel.removeAll();
        for(int location:grid)
        {
            mainPanel.add(cells[location].getLabel());
        }
    }

    public void moveCell(int number)
    {
        int location = grid.indexOf(number-1);
        int[] surroundings;

        //Determine which locations the cell should be able to move to
        if(location == 2 || location == 5 || location == 8)
        {
            surroundings = new int[] {location - 3, location - 1, location + 3};
        }
        else if(location == 0 || location == 3 || location == 6)
        {
            surroundings = new int[] {location - 3, location + 1, location + 3};
        }
        else
        {
            surroundings = new int[] {location-3, location-1, location+1, location+3};
        }

        for(int i:surroundings)
        {
            if(!(i < 0 || i > 8) && grid.get(i) == 8)
            {
                grid.set(i, grid.get(location));
                grid.set(location, 8);
                System.out.println(i + "->" + location);
                counter++;
            }
        }
        drawGUI();
        if(checkWin())
        {
            System.out.println("Won");
            JOptionPane.showMessageDialog(null, "Congrats, you won in " + counter + " turns!");
            System.exit(0);
        }
    }

    private boolean checkWin()
    {
        boolean win = true;
        for(int i = 0; i < grid.size(); i++)
        {
            if(completeGrid[i] != grid.get(i))
                win = false;
        }
        return win;
    }

}
