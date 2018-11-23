import javax.swing.*;

public class Cell
{

    private JLabel label;// = new JLabel();
    private int number;

    public Cell(ImageIcon icon, int number)
    {
        label = new JLabel(icon);
        this.number = number;
    }

    public JLabel getLabel()
    {
        return label;
    }

    public int getNumber()
    {
        return number;
    }

}
