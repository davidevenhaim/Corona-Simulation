/**
 * CovidMap Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Set;
//import java.awt.Point;
import Country.Settlement;
import Location.Point;

public class CovidMap extends JPanel{
    private ArrayList<Shape> shapeArray;

    CovidMap() {
        shapeArray = new ArrayList<>();
        addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for(Shape shape : shapeArray) {
                    java.awt.Point clickedPoint = e.getPoint();
                    if(shape.contains(clickedPoint)) {
                        System.out.println(e.getPoint());
                        Point point = new Point(clickedPoint.x, clickedPoint.y);
                        int k = -1;
                        Settlement[] sett = MainWindow.getInstance().simulate.getMap().getSettlements();
                        for (int i = 0; i < sett.length; i++) {
                            if (sett[i].getLocation().isOnLocation(point)) {
                                k = i;
                                break;
                            }
                        }
                        if (k >= 0) {
                            StatisticsWindow statistics = new StatisticsWindow();
                            System.out.println("K is: " + k);
                            statistics.setSelectedRow(k);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Settlement [] sett =  MainWindow.getInstance().simulate.getMap().getSettlements();
        for(int i = 0; i < sett.length; i++) {
            Point currCenter = sett[i].getLocation().getCenter();
            for (int j = 0; j < sett[i].getNearbySettlement().size(); j++) {
                Settlement nearbySett = sett[i].getNearbySettlement().get(j);
                Point center = nearbySett.getLocation().getCenter();
                NearbySettlementLineDecorator decorator = new NearbySettlementLineDecorator(sett[i], nearbySett);
                decorator.SetColor(g);
                g.drawLine(currCenter.getX(), currCenter.getY(), center.getX(), center.getY());
            }
        }

        for(int i = 0; i < sett.length; i++) {

            g.setColor(sett[i].getRamzorColor().color);
            int[] locationDetails = sett[i].getLocation().getDetails();
            Shape rect = new Rectangle(locationDetails[0], locationDetails[1], locationDetails[2], locationDetails[3]);
            shapeArray.add(rect);
            g.fillRoundRect(locationDetails[0], locationDetails[1], locationDetails[2], locationDetails[3], 5, 5 );
            g.setColor(Color.BLACK);
            g.drawString(sett[i].getName(), locationDetails[0], locationDetails[1] + 10);
        }
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }

}
