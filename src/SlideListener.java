import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SlideListener extends MouseAdapter
{

    private SlidingPuzzle puzzle;

    public SlideListener(SlidingPuzzle puzzle)
    {
        this.puzzle = puzzle;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Get the label that was clicked
        JLabel labelClicked = (JLabel)e.getSource();
        if(labelClicked.getIcon() != null) {
            //String number = labelClicked.getIcon().toString().replace(".png", "");
            int pathLength = labelClicked.getIcon().toString().length();
            String number = labelClicked.getIcon().toString().substring(pathLength-5, pathLength-4);
            puzzle.moveCell(Integer.valueOf(number));
        }
    }

}
